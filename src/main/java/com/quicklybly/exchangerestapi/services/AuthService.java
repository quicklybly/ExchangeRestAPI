package com.quicklybly.exchangerestapi.services;

import com.quicklybly.exchangerestapi.dto.MailDTO;
import com.quicklybly.exchangerestapi.dto.auth.AuthResponse;
import com.quicklybly.exchangerestapi.dto.auth.LoginDTO;
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
import com.quicklybly.exchangerestapi.utils.CryptoTools;
import com.quicklybly.exchangerestapi.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Integer SECRET_KEY_LENGTH = 21;
    @Value("${spring.mail.send-url}")
    private String mailServiceUrl;

    private final PasswordEncoder passwordEncoder;
    private final CryptoTools cryptoTools;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public String signUp(SignUpDTO signUpDTO) {
        if (userRepo.existsByUsername(signUpDTO.getUsername())) {
            throw new UsernameIsTakenException();
        }
        if (userRepo.existsByEmail(signUpDTO.getEmail())) {
            throw new EmailIsTakenException();
        }
        //TODO exception logging
        Role roles = roleRepo.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new AppException("Server error", HttpStatus.INTERNAL_SERVER_ERROR));

        UserEntity user = UserEntity.builder()
                .username(signUpDTO.getUsername())
                .email(signUpDTO.getEmail())
                .isActive(false)
                .roles(Collections.singletonList(roles))
                .build();
        user = userRepo.save(user);
        var response = sendRequestToMailService(cryptoTools.hashOf(user.getId()), user.getEmail());

        if (response.getStatusCode() != HttpStatus.OK) {
            userRepo.delete(user);
            //TODO logging
            return String.format("Sending email to %s failed, please try again", signUpDTO.getEmail());
        }
        return "An email has been sent to you.\n"
                + "Follow the link in the email to confirm your registration.";
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

    public String activateAccount(String encodedId) {
        Long userId = cryptoTools.idOf(encodedId);
        UserEntity user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);

        if (user.isActive()) {
            throw new AppException("User already activated", HttpStatus.BAD_REQUEST);
        }

        String secretKey = generateSecretKey();
        user.setPassword(passwordEncoder.encode(secretKey));
        user.setActive(true);
        userRepo.save(user);

        return String.format("Account activated.\nPassword: %s", secretKey);
    }

    private String generateSecretKey() {
        String secretKey = HashUtils.getRandomString(SECRET_KEY_LENGTH);
        while (userRepo.existsByPassword(secretKey)) {
            secretKey = HashUtils.getRandomString(SECRET_KEY_LENGTH);
        }
        return secretKey;
    }

    private ResponseEntity<String> sendRequestToMailService(String encodedUserId, String email) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MailDTO mailDTO = new MailDTO(encodedUserId, email);
        HttpEntity<MailDTO> request = new HttpEntity<>(mailDTO, headers);
        return restTemplate.exchange(mailServiceUrl,
                HttpMethod.POST,
                request,
                String.class);
    }
}
