package com.quicklybly.exchangerestapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallet")
public class Wallet {
    @EmbeddedId
    private WalletKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @MapsId("currencyId")
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(columnDefinition = "NUMERIC(20, 10)", nullable = false)
    private BigDecimal amount;

    public Wallet(UserEntity userEntity, Currency currency) {
        this.id = new WalletKey(userEntity.getId(), currency.getId());
        this.userEntity = userEntity;
        this.currency = currency;
        this.amount = new BigDecimal(0);
    }
}
