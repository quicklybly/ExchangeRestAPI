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
@Table(name = "wallet")
public class Wallet {
    @EmbeddedId
    private WalletKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @MapsId("currencyId")
    @JoinColumn(name = "currency_id")
    private Currency currency;

    private BigDecimal amount;
}
