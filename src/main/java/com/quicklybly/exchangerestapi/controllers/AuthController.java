package com.quicklybly.exchangerestapi.controllers;

import com.quicklybly.exchangerestapi.dto.auth.AuthResponse;
import com.quicklybly.exchangerestapi.dto.auth.LoginDTO;
import com.quicklybly.exchangerestapi.dto.auth.SignUpDTO;
import com.quicklybly.exchangerestapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/sign-up",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.ok(authService.signUp(signUpDTO));
    }

    @PostMapping(value = "/login",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @GetMapping(value = "activation",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> activeAccount(@RequestParam("id") String id) {
        return ResponseEntity.ok(authService.activateAccount(id));
    }
}
