package com.quicklybly.exchangerestapi.controllers;

import com.quicklybly.exchangerestapi.dto.*;
import com.quicklybly.exchangerestapi.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping(value = "total-amount",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<WalletDTO> getTotalAmount(@RequestBody TickerDTO ticker) {
        return ResponseEntity.ok(adminService.getTotalAmount(ticker));
    }

    @GetMapping(value = "operation-count",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<OperationCountDTO> countOperationBetweenDates(
            @RequestBody OperationBetweenDatesDTO operationBetweenDatesDTO) {
        System.out.println(operationBetweenDatesDTO.getDateTo().toString());
        return ResponseEntity.ok(adminService.countOperationBetweenDates(operationBetweenDatesDTO));
    }

    @PostMapping(value = "change-rate",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ChangeRateResponse> changeRate(@RequestBody ChangeRateRequest changeRateRequest) {
        return ResponseEntity.ok(adminService.changeRate(changeRateRequest));
    }
}
