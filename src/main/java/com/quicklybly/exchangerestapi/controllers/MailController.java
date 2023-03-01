package com.quicklybly.exchangerestapi.controllers;

import com.quicklybly.exchangerestapi.dto.MailDTO;
import com.quicklybly.exchangerestapi.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("mail")
public class MailController {
    private final MailService mailService;

    @PostMapping(value = "send",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> sendActivationEmail(@RequestBody MailDTO mailDTO) {
        mailService.sendActivationEmail(mailDTO);
        return ResponseEntity.ok().build();
    }
}
