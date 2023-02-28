package com.quicklybly.exchangerestapi.entities.operations;

import com.quicklybly.exchangerestapi.entities.Currency;
import com.quicklybly.exchangerestapi.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "exchange-operation")
public class ExchangeOperation extends Operation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_from_id", nullable = false)
    private Currency currencyFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_to_id", nullable = false)
    private Currency currencyTo;

    @Column(columnDefinition = "NUMERIC(20, 10)", nullable = false)
    private BigDecimal amount;

    public ExchangeOperation(UserEntity user, Currency currencyFrom, Currency currencyTo, BigDecimal amount) {
        super(user);
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amount = amount;
    }
}
