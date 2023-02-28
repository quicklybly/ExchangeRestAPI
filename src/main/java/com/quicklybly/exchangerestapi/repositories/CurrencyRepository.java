package com.quicklybly.exchangerestapi.repositories;

import com.quicklybly.exchangerestapi.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    boolean existsByTicker(String ticker);

    Optional<Currency> findByTicker(String ticker);
}
