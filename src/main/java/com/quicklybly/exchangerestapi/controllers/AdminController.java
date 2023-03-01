package com.quicklybly.exchangerestapi.controllers;

import com.quicklybly.exchangerestapi.dto.*;
import com.quicklybly.exchangerestapi.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Administration")
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping(value = "total-amount",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Accepts currencies and sees how much of this currency is on all user accounts")
    @ApiResponse(responseCode = "200",
            description = "Operation succeeded",
            content = @Content(schema = @Schema(implementation = WalletDTO.class)))
    @ApiResponse(responseCode = "404", description = "Unsupported currencies",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<WalletDTO> getTotalAmount(@RequestBody TickerDTO ticker) {
        return ResponseEntity.ok(adminService.getTotalAmount(ticker));
    }

    @GetMapping(value = "operation-count",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Returns the number of operations for the specified time period. Expected date format is: dd.MM.yyyy")
    @ApiResponse(responseCode = "200",
            description = "Operation succeeded",
            content = @Content(schema = @Schema(implementation = OperationCountDTO.class)))
    @ApiResponse(responseCode = "404", description = "Wrong date format",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<OperationCountDTO> countOperationBetweenDates(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Currency ratio",
                    required = true,
                    content = @Content(schema = @Schema(implementation = OperationBetweenDatesDTO.class)))
            @RequestBody OperationBetweenDatesDTO operationBetweenDatesDTO) {
        System.out.println(operationBetweenDatesDTO.getDateTo().toString());
        return ResponseEntity.ok(adminService.countOperationBetweenDates(operationBetweenDatesDTO));
    }

    @PostMapping(value = "change-rate",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Changes the exchange rate")
    @ApiResponse(responseCode = "200",
            description = "Operation succeeded",
            content = @Content(schema = @Schema(implementation = ChangeRateResponse.class)))
    @ApiResponse(responseCode = "404", description = "Unsupported currency",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<ChangeRateResponse> changeRate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Currency ratio",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ChangeRateRequest.class)))
            @RequestBody ChangeRateRequest changeRateRequest) {
        return ResponseEntity.ok(adminService.changeRate(changeRateRequest));
    }
}