package com.quicklybly.exchangerestapi.controllers;

import com.quicklybly.exchangerestapi.dto.ErrorDTO;
import com.quicklybly.exchangerestapi.dto.MailDTO;
import com.quicklybly.exchangerestapi.services.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Mailing")
@RequestMapping("mail")
public class MailController {
    private final MailService mailService;

    @PostMapping(value = "send", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Mailing endpoint", description = "Used within the application's infrastructure to send emails. Not available for external requests")
    @ApiResponse(responseCode = "200", description = "Email send")
    @ApiResponse(responseCode = "404", description = "Something went wrong",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<?> sendActivationEmail(@Parameter(description = "Mail data transfer object", required = true, schema = @Schema(implementation = MailDTO.class)) @RequestBody MailDTO mailDTO) {
        mailService.sendActivationEmail(mailDTO);
        return ResponseEntity.ok().build();
    }
}