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
@JsonPropertyOrder({"wallet", "amount"})
public class WalletDTO {
    @JsonProperty("wallet")
    private String currencyTicker;
    private BigDecimal amount;
}
