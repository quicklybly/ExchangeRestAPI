package com.quicklybly.exchangerestapi.repositories;

import com.quicklybly.exchangerestapi.entities.Currency;
import com.quicklybly.exchangerestapi.entities.ExchangeRate;
import com.quicklybly.exchangerestapi.entities.ExchangeRateKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, ExchangeRateKey> {
    Collection<ExchangeRate> findAllByFirstCurrency_Ticker(String ticker);

    Optional<ExchangeRate> findByFirstCurrencyTickerAndSecondCurrencyTicker(String firstTicker, String secondTicker);

    Optional<ExchangeRate> findByFirstCurrencyAndSecondCurrency(Currency firstCurrency, Currency secondCurrency);
}
