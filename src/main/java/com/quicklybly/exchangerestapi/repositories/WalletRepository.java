package com.quicklybly.exchangerestapi.repositories;

import com.quicklybly.exchangerestapi.entities.Currency;
import com.quicklybly.exchangerestapi.entities.UserEntity;
import com.quicklybly.exchangerestapi.entities.Wallet;
import com.quicklybly.exchangerestapi.entities.WalletKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, WalletKey> {
    Collection<Wallet> findAllByUserEntity(UserEntity user);

    Optional<Wallet> findByUserEntityAndCurrencyTicker(UserEntity user, String ticker);

    @Query(value = "SELECT sum(w.amount) from Wallet w where w.currency = :currency")
    BigDecimal getTotalAmount(@Param("currency") Currency currency);
}
