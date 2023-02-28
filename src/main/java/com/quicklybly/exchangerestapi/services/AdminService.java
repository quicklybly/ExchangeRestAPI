package com.quicklybly.exchangerestapi.services;

import com.quicklybly.exchangerestapi.dto.*;
import com.quicklybly.exchangerestapi.entities.Currency;
import com.quicklybly.exchangerestapi.entities.ExchangeRate;
import com.quicklybly.exchangerestapi.exceptions.UnsupportedCurrencyException;
import com.quicklybly.exchangerestapi.repositories.CurrencyRepository;
import com.quicklybly.exchangerestapi.repositories.ExchangeRateRepository;
import com.quicklybly.exchangerestapi.repositories.OperationRepository;
import com.quicklybly.exchangerestapi.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final WalletRepository walletRepo;
    private final CurrencyRepository currencyRepo;
    private final OperationRepository operationRepo;
    private final ExchangeRateRepository exchangeRateRepo;

    public WalletDTO getTotalAmount(TickerDTO tickerDTO) {
        tickerDTO.setCurrencyTicker(tickerDTO.getCurrencyTicker().toUpperCase());
        Currency currency = currencyRepo
                .findByTicker(tickerDTO.getCurrencyTicker())
                .orElseThrow(UnsupportedCurrencyException::new);

        return new WalletDTO(tickerDTO.getCurrencyTicker(),
                walletRepo.getTotalAmount(currency));
    }

    public OperationCountDTO countOperationBetweenDates(
            OperationBetweenDatesDTO operationBetweenDatesDTO) {
        return new OperationCountDTO(operationRepo.countAllByDateBetween(
                operationBetweenDatesDTO.getDateFrom(),
                operationBetweenDatesDTO.getDateTo()));
    }

    public ChangeRateResponse changeRate(ChangeRateRequest changeRateRequest) {
        Currency baseCurrency = currencyRepo
                .findByTicker(changeRateRequest.getBaseCurrency().toUpperCase())
                .orElseThrow(UnsupportedCurrencyException::new);
        ChangeRateResponse response = new ChangeRateResponse();
        for (CurrencyAndRateDTO currencyToRate : changeRateRequest.getTargetCurrencies()) {
            Optional<Currency> targetCurrency = currencyRepo
                    .findByTicker(currencyToRate.getCurrencyTicker().toUpperCase());
            if (targetCurrency.isPresent()) {
                ExchangeRate rate = exchangeRateRepo
                        .findByFirstCurrencyAndSecondCurrency(baseCurrency, targetCurrency.get())
                        .orElse(new ExchangeRate(baseCurrency, targetCurrency.get()));
                rate.setRate(currencyToRate.getAmount());
                exchangeRateRepo.save(rate);
                response.getUpdatedRate().add(currencyToRate);
            } else {
                response.getUnsupportedCurrencies().add(currencyToRate);
            }
        }
        return response;
    }
}
