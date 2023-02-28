package com.quicklybly.exchangerestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRateRequest {
    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("target_currencies")
    private List<CurrencyAndRateDTO> targetCurrencies;
}
