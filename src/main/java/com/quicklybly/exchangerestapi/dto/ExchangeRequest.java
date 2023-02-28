package com.quicklybly.exchangerestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRequest {
    @JsonProperty("currency_from")
    private String currencyFrom;

    @JsonProperty("currency_to")
    private String currencyTo;

    private BigDecimal amount;
}
