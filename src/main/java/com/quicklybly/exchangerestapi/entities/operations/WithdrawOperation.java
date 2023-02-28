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
@Entity(name = "withdraw-operation")
public class WithdrawOperation extends Operation {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(columnDefinition = "NUMERIC(20, 10)", nullable = false)
    private BigDecimal amount;

    public WithdrawOperation(UserEntity user, Currency currency, BigDecimal amount) {
        super(user);
        this.currency = currency;
        this.amount = amount;
    }
}
