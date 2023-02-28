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
public class WalletKey implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "currency_id", nullable = false)
    private Long currencyId;
}
