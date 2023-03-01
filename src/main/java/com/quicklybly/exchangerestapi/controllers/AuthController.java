package com.quicklybly.exchangerestapi.controllers;

import com.quicklybly.exchangerestapi.dto.ErrorDTO;
import com.quicklybly.exchangerestapi.dto.auth.AuthResponse;
import com.quicklybly.exchangerestapi.dto.auth.LoginDTO;
import com.quicklybly.exchangerestapi.dto.auth.SignUpDTO;
import com.quicklybly.exchangerestapi.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Authentication")
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/sign-up",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Sing up endpoint", description = "Returns a string with information about the status of success of the first stage of registration",
            parameters = @Parameter(schema = @Schema(implementation = SignUpDTO.class)))
    @ApiResponse(responseCode = "200",
            description = "An email has been sent to a user",
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "404", description = "Something went wrong",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<String> signUp(@Parameter(required = true, schema = @Schema(implementation = SignUpDTO.class)) @RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.ok(authService.signUp(signUpDTO));
    }

    @PostMapping(value = "/login",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Confirms the user's login by sending a JWT token for subsequent work with the rest of the exchange API",
            parameters = @Parameter(schema = @Schema(implementation = LoginDTO.class)))
    @ApiResponse(responseCode = "200",
            description = "User successfully logged in",
            content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "401", description = "Incorrect username or password",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @GetMapping(value = "activation",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "This endpoint must be available to the user when clicking on a link from an email previously sent by the application. Endpoint will return the password for the current user")
    @ApiResponse(responseCode = "200",
            description = "User activation succeeded",
            content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "404", description = "User already activated or user not found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<String> activeAccount(@RequestParam("id") String id) {
        return ResponseEntity.ok(authService.activateAccount(id));
    }
}
