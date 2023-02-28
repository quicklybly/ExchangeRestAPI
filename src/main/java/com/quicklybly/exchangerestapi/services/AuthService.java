package com.quicklybly.exchangerestapi.services;

import com.quicklybly.exchangerestapi.dto.auth.AuthResponse;
import com.quicklybly.exchangerestapi.dto.auth.LoginDTO;
import com.quicklybly.exchangerestapi.dto.auth.SecretKeyDTO;
import com.quicklybly.exchangerestapi.dto.auth.SignUpDTO;
import com.quicklybly.exchangerestapi.entities.Role;
import com.quicklybly.exchangerestapi.entities.UserEntity;
import com.quicklybly.exchangerestapi.entities.enums.RoleEnum;
import com.quicklybly.exchangerestapi.exceptions.AppException;
import com.quicklybly.exchangerestapi.exceptions.auth.EmailIsTakenException;
import com.quicklybly.exchangerestapi.exceptions.auth.UserNotFoundException;
import com.quicklybly.exchangerestapi.exceptions.auth.UsernameIsTakenException;
import com.quicklybly.exchangerestapi.repositories.RoleRepository;
import com.quicklybly.exchangerestapi.repositories.UserRepository;
import com.quicklybly.exchangerestapi.security.JWTService;
import com.quicklybly.exchangerestapi.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Integer SECRET_KEY_LENGTH = 21;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public SecretKeyDTO signUp(SignUpDTO signUpDTO) {
        if (userRepo.existsByUsername(signUpDTO.getUsername())) {
            throw new UsernameIsTakenException();
        }
        if (userRepo.existsByEmail(signUpDTO.getEmail())) {
            throw new EmailIsTakenException();
        }
        //TODO exception logging
        Role roles = roleRepo.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new AppException("Server error", HttpStatus.INTERNAL_SERVER_ERROR));
        SecretKeyDTO secretKeyDTO = new SecretKeyDTO(generateSecretKey());
        UserEntity userEntity = UserEntity.builder()
                .username(signUpDTO.getUsername())
                .email(signUpDTO.getEmail())
                .password(passwordEncoder.encode(secretKeyDTO.getSecretKey()))
                .roles(Collections.singletonList(roles))
                .build();
        userRepo.save(userEntity);
        return secretKeyDTO;
    }

    public AuthResponse login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );
        UserEntity user = userRepo
                .findByUsername(loginDTO.getUsername())
                .orElseThrow(UserNotFoundException::new);
        return new AuthResponse(jwtService.generateToken(user));
    }

    private String generateSecretKey() {
        String secretKey = HashUtils.getRandomString(SECRET_KEY_LENGTH);
        while (userRepo.existsByPassword(secretKey)) {
            secretKey = HashUtils.getRandomString(SECRET_KEY_LENGTH);
        }
        return secretKey;
    }
}
