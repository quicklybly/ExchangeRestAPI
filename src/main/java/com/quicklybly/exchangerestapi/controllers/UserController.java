package com.quicklybly.exchangerestapi.controllers;

import com.quicklybly.exchangerestapi.dto.*;
import com.quicklybly.exchangerestapi.entities.UserEntity;
import com.quicklybly.exchangerestapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "balance",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<WalletDTO>> getBalance(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(userService.getBalance(user));
    }

    @PostMapping(value = "deposit",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<WalletDTO> deposit(@AuthenticationPrincipal UserEntity user,
                                             @RequestBody WalletDTO wallet) {
        return ResponseEntity.ok(userService.deposit(user, wallet));
    }

    @PostMapping(value = "withdraw",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<WalletDTO> withdraw(@AuthenticationPrincipal UserEntity user,
                                              @RequestBody WithdrawDTO withdrawDTO) {
        return ResponseEntity.ok(userService.withdraw(user, withdrawDTO));
    }

    @PostMapping(value = "exchange",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ExchangeResponse> exchange(@AuthenticationPrincipal UserEntity user,
                                                     @RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(userService.exchange(user, request));
    }


    @GetMapping(value = "exchange-rates",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CurrencyAndRateDTO>> getExchangeRate(@RequestBody TickerDTO targetCurrency) {
        return ResponseEntity.ok(userService.getExchangeRate(targetCurrency));
    }
}
