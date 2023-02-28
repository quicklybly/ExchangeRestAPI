package com.quicklybly.exchangerestapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ExchangeRateKey implements Serializable {
    @Column(name = "first_currency_id", nullable = false)
    private Long firstCurrencyId;

    @Column(name = "second_currency_id", nullable = false)
    private Long secondCurrencyId;
}
