package com.quicklybly.exchangerestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChangeRateResponse {
    @JsonProperty("updated_rate")
    private List<CurrencyAndRateDTO> updatedRate;
    @JsonProperty("unsupported_currencies")
    private List<CurrencyAndRateDTO> unsupportedCurrencies;

    public ChangeRateResponse() {
        this.updatedRate = new ArrayList<>();
        this.unsupportedCurrencies = new ArrayList<>();
    }
}
