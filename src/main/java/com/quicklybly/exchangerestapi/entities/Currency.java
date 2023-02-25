package com.quicklybly.exchangerestapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceCurrencyGenerator")
    @GenericGenerator(
            name = "sequenceCurrencyGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "currency_sequence"),
                    @Parameter(name = "initial_value", value = "20"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticker;

    @OneToMany(mappedBy = "firstCurrency")
    private Set<ExchangeRate> currenciesAvailableToExchange;
}
