package com.quicklybly.exchangerestapi.services;

import com.quicklybly.exchangerestapi.dto.*;
import com.quicklybly.exchangerestapi.entities.Currency;
import com.quicklybly.exchangerestapi.entities.ExchangeRate;
import com.quicklybly.exchangerestapi.entities.UserEntity;
import com.quicklybly.exchangerestapi.entities.Wallet;
import com.quicklybly.exchangerestapi.entities.operations.DepositOperation;
import com.quicklybly.exchangerestapi.entities.operations.ExchangeOperation;
import com.quicklybly.exchangerestapi.entities.operations.WithdrawOperation;
import com.quicklybly.exchangerestapi.exceptions.*;
import com.quicklybly.exchangerestapi.repositories.CurrencyRepository;
import com.quicklybly.exchangerestapi.repositories.ExchangeRateRepository;
import com.quicklybly.exchangerestapi.repositories.OperationRepository;
import com.quicklybly.exchangerestapi.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WalletRepository walletRepo;
    private final ExchangeRateRepository exchangeRateRepo;
    private final CurrencyRepository currencyRepo;
    private final OperationRepository operationRepo;

    private static final String walletMark = "_wallet";


    public List<WalletDTO> getBalance(UserEntity user) {
        return walletRepo.findAllByUserEntity(user)
                .stream()
                .map(wallet -> WalletDTO.builder()
                        .currencyTicker(addWalletMark(wallet.getCurrency().getTicker()))
                        .amount(wallet.getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    public List<CurrencyAndRateDTO> getExchangeRate(TickerDTO targetCurrency) {
        targetCurrency.setCurrencyTicker(targetCurrency.getCurrencyTicker().toUpperCase());
        if (!currencyRepo.existsByTicker(targetCurrency.getCurrencyTicker())) {
            throw new UnsupportedCurrencyException();
        }
        return exchangeRateRepo
                .findAllByFirstCurrency_Ticker(targetCurrency.getCurrencyTicker())
                .stream()
                .map(rate -> new CurrencyAndRateDTO(rate.getSecondCurrency().getTicker(), rate.getRate()))
                .collect(Collectors.toList());
    }

    @Transactional
    public WalletDTO deposit(UserEntity user, WalletDTO walletDTO) {
        validateWalletDTO(walletDTO);
        Currency currency = currencyRepo
                .findByTicker(walletDTO.getCurrencyTicker())
                .orElseThrow(UnsupportedCurrencyException::new);

        Wallet wallet = walletRepo
                .findByUserEntityAndCurrencyTicker(user, walletDTO.getCurrencyTicker())
                .orElse(new Wallet(user, currency));
        wallet.setAmount(wallet.getAmount().add(walletDTO.getAmount()));
        operationRepo.save(new DepositOperation(user, currency, wallet.getAmount()));
        walletDTO.setAmount(wallet.getAmount());
        walletDTO.setCurrencyTicker(addWalletMark(wallet.getCurrency().getTicker()));
        walletRepo.save(wallet);

        return walletDTO;
    }

    @Transactional
    public WalletDTO withdraw(UserEntity user, WithdrawDTO withdrawDTO) {
        validateWithdrawDTO(withdrawDTO);
        Currency currency = currencyRepo
                .findByTicker(withdrawDTO.getCurrencyTicker())
                .orElseThrow(UnsupportedCurrencyException::new);
        if (isWithdrawTypeInvalid(withdrawDTO, currency)) {
            throw new InvalidWithdrawTypeException();
        }
        Wallet wallet = getWalletWithEnoughMoney(user, currency.getTicker(), withdrawDTO.getAmount());

        wallet.setAmount(wallet.getAmount().subtract(withdrawDTO.getAmount()));
        operationRepo.save(new WithdrawOperation(user, currency, withdrawDTO.getAmount()));
        walletRepo.save(wallet);
        return new WalletDTO(addWalletMark(currency.getTicker()), wallet.getAmount());
    }

    @Transactional
    public ExchangeResponse exchange(UserEntity user, ExchangeRequest request) {
        request.setCurrencyFrom(request.getCurrencyFrom().toUpperCase());
        request.setCurrencyTo(request.getCurrencyTo().toUpperCase());
        Wallet walletFrom = getWalletWithEnoughMoney(user, request.getCurrencyFrom(), request.getAmount());
        Currency currencyFrom = currencyRepo
                .findByTicker(request.getCurrencyFrom())
                .orElseThrow(UnsupportedCurrencyException::new);
        Currency currencyTo = currencyRepo
                .findByTicker(request.getCurrencyTo())
                .orElseThrow(UnsupportedCurrencyException::new);

        ExchangeRate rate = exchangeRateRepo
                .findByFirstCurrencyTickerAndSecondCurrencyTicker(request.getCurrencyFrom(), request.getCurrencyTo())
                .orElseThrow(InvalidExchangePairException::new);

        Wallet walletTo = walletRepo
                .findByUserEntityAndCurrencyTicker(user, request.getCurrencyTo())
                .orElse(new Wallet(user, currencyTo));

        walletFrom.setAmount(walletFrom.getAmount().subtract(request.getAmount()));
        walletTo.setAmount(walletTo.getAmount().add(rate.getRate().multiply(request.getAmount())));

        walletRepo.save(walletFrom);
        walletRepo.save(walletTo);

        operationRepo.save(new ExchangeOperation(user, currencyFrom, currencyTo, request.getAmount()));

        return ExchangeResponse.builder()
                .currencyFrom(currencyFrom.getTicker())
                .currencyTo(currencyTo.getTicker())
                .amountFrom(walletFrom.getAmount())
                .amountTo(walletTo.getAmount())
                .build();
    }

    private String deleteWalletMark(String s) {
        return s.split(walletMark)[0];
    }

    private String addWalletMark(String s) {
        return s + walletMark;
    }


    private void validateWalletDTO(WalletDTO walletDTO) {
        if (walletDTO.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException();
        }
        walletDTO.setCurrencyTicker(deleteWalletMark(walletDTO.getCurrencyTicker()).toUpperCase());
    }

    private void validateWithdrawDTO(WithdrawDTO withdrawDTO) {
        if (withdrawDTO.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException();
        }
        withdrawDTO.setCurrencyTicker(deleteWalletMark(withdrawDTO.getCurrencyTicker()).toUpperCase());
    }

    private Wallet getWalletWithEnoughMoney(UserEntity user, String currencyTicker, BigDecimal amount) {
        Optional<Wallet> wallet = walletRepo
                .findByUserEntityAndCurrencyTicker(user, currencyTicker);

        if (wallet.isEmpty() || wallet.get().getAmount().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        return wallet.get();
    }

    private boolean isWithdrawTypeInvalid(WithdrawDTO withdrawDTO, Currency currency) {
        return ((Objects.isNull(withdrawDTO.getCreditCard()) && Objects.isNull(withdrawDTO.getWalletAddress()))
                || (Objects.nonNull(withdrawDTO.getCreditCard()) && !currency.isFiat()) ||
                (Objects.nonNull(withdrawDTO.getWalletAddress()) && !currency.isCrypto()));
    }
}
