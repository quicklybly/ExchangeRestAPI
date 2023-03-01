package com.quicklybly.exchangerestapi.controllers;

import com.quicklybly.exchangerestapi.dto.*;
import com.quicklybly.exchangerestapi.entities.UserEntity;
import com.quicklybly.exchangerestapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Administration")
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "balance",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Returns a list of the user's wallets - the name of the currency and its amount")
    @ApiResponse(responseCode = "200",
            description = "Operation succeeded",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalletDTO.class))))
    public ResponseEntity<List<WalletDTO>> getBalance(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(userService.getBalance(user));
    }

    @PostMapping(value = "deposit",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Wallet deposit")
    @ApiResponse(responseCode = "200", description = "Operation succeeded",
            content = @Content(schema = @Schema(implementation = WalletDTO.class)))
    @ApiResponse(responseCode = "404", description = "Negative deposit",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<WalletDTO> deposit(@AuthenticationPrincipal UserEntity user,
                                             @RequestBody WalletDTO wallet) {
        return ResponseEntity.ok(userService.deposit(user, wallet));
    }

    @PostMapping(value = "withdraw",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Withdrawing money from the user's wallet")
    @ApiResponse(responseCode = "200", description = "Operation succeeded",
            content = @Content(schema = @Schema(implementation = WalletDTO.class)))
    @ApiResponse(responseCode = "404", description = "Unsupported currency or invalid withdraw type or negative amount or not enough money",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<WalletDTO> withdraw(@AuthenticationPrincipal UserEntity user,
                                              @RequestBody WithdrawDTO withdrawDTO) {
        return ResponseEntity.ok(userService.withdraw(user, withdrawDTO));
    }

    @PostMapping(value = "exchange",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Exchange of one currency for another")
    @ApiResponse(responseCode = "200", description = "Operation succeeded",
            content = @Content(schema = @Schema(implementation = ExchangeResponse.class)))
    @ApiResponse(responseCode = "404", description = "Unsupported currency or invalid exchange pair or not enough money",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<ExchangeResponse> exchange(@AuthenticationPrincipal UserEntity user,
                                                     @RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(userService.exchange(user, request));
    }


    @GetMapping(value = "exchange-rates",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "See what you can change the current currency for")
    @ApiResponse(responseCode = "200", description = "Operation succeeded",
            content = @Content(schema = @Schema(implementation = WalletDTO.class)))
    @ApiResponse(responseCode = "404", description = "Unsupported currency",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CurrencyAndRateDTO.class))))
    public ResponseEntity<List<CurrencyAndRateDTO>> getExchangeRate(@RequestBody TickerDTO targetCurrency) {
        return ResponseEntity.ok(userService.getExchangeRate(targetCurrency));
    }
}