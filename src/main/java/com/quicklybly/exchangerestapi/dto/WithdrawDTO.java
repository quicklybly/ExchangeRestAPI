package com.quicklybly.exchangerestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawDTO {
    @JsonProperty("currency")
    private String currencyTicker;
    @JsonProperty("count")
    private BigDecimal amount;

    @JsonProperty("credit_card")
    private String creditCard;

    @JsonProperty("wallet")
    private String walletAddress;
}
