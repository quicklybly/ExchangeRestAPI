package com.quicklybly.exchangerestapi.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretKeyDTO {
    @JsonProperty("secret_key")
    private String secretKey;
}
