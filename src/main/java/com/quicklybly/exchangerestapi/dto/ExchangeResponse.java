package com.quicklybly.exchangerestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"currency_from", "currency_to", "amount_from", "amount_to"})
public class ExchangeResponse {
    @JsonProperty("currency_from")
    private String currencyFrom;

    @JsonProperty("currency_to")
    private String currencyTo;

    @JsonProperty("amount_from")
    private BigDecimal amountFrom;
    @JsonProperty("amount_to")
    private BigDecimal amountTo;
}
