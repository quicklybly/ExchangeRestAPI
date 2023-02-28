package com.quicklybly.exchangerestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyAndRateDTO {
    @JsonProperty("currency")
    private String currencyTicker;
    @JsonProperty("rate")
    private BigDecimal amount;
}
