package com.example.learning_spring_boot.service;

import com.example.learning_spring_boot.dto.request.AuthencationRequest;
import com.example.learning_spring_boot.exception.AppException;
import com.example.learning_spring_boot.exception.ErrorCode;
import com.example.learning_spring_boot.repository.AuthencationResponse;
import com.example.learning_spring_boot.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthencationService {

    UserRepository userRepository;

    @NonFinal
    protected static final String SIGN_KEY =
            "069qDcdSwj82Ylfoq9BUji7t07654z5TGIBJbsLe6u5OHxM/UNguHU8S4Mr5n1ZT";


    public AuthencationResponse authenticate(AuthencationRequest request) {
        var user = userRepository.findByUserName(request.getUserName()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOTEXISTED)
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);
        boolean authenticated = passwordEncoder.matches(request.getPassWord(), user.getPassWord());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
            // đăng nhập không thành công
        }
        var token = generateToken(request.getUserName());
        return AuthencationResponse.builder()
                .token(token)
                .IsAuth(true)
                .build();
    }

    private String generateToken(String userName) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .issuer("")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {

            throw new RuntimeException(e);
        }
    }
}
