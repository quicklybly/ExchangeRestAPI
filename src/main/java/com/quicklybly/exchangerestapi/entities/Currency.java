package com.quicklybly.exchangerestapi.entities;

import com.quicklybly.exchangerestapi.entities.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticker;

    @OneToMany(mappedBy = "firstCurrency")
    private Set<ExchangeRate> currenciesAvailableToExchange;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currencyType;

    public boolean isCrypto() {
        return CurrencyType.CRYPTO.equals(this.currencyType);
    }

    public boolean isFiat() {
        return CurrencyType.FIAT.equals(this.currencyType);
    }
}
