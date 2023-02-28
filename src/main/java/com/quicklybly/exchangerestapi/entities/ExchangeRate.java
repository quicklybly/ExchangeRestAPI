package com.quicklybly.exchangerestapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
    @EmbeddedId
    private ExchangeRateKey id;

    @ManyToOne
    @MapsId("first_currency_id")
    @JoinColumn(name = "first_currency_id", nullable = false)
    private Currency firstCurrency;

    @ManyToOne
    @MapsId("second_currency_id")
    @JoinColumn(name = "second_currency_id", nullable = false)
    private Currency secondCurrency;
    @Column(nullable = false, columnDefinition = "NUMERIC(20, 10)")
    private BigDecimal rate;

    public ExchangeRate(Currency firstCurrency, Currency secondCurrency) {
        this.id = new ExchangeRateKey(firstCurrency.getId(), secondCurrency.getId());
        this.firstCurrency = firstCurrency;
        this.secondCurrency = secondCurrency;
    }
}
