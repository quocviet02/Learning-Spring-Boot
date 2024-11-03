package com.example.learning_spring_boot.service;

import com.example.learning_spring_boot.dto.request.AuthencationRequest;
import com.example.learning_spring_boot.dto.request.IntrospectRequest;
import com.example.learning_spring_boot.entity.User;
import com.example.learning_spring_boot.exception.AppException;
import com.example.learning_spring_boot.exception.ErrorCode;
import com.example.learning_spring_boot.repository.AuthencationResponse;
import com.example.learning_spring_boot.repository.IntrospectResponse;
import com.example.learning_spring_boot.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.StringJoiner;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthencationService {

    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGN_KEY;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verifed = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .IsValid(verifed && expityTime.after(new Date()))
                .build();
    }

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
        var token = generateToken(user);
        return AuthencationResponse.builder()
                .token(token)
                .IsAuth(true)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("heheh")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", bulidScope(user))
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

    private String bulidScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRole()))//check is user is empty
            user.getRole().forEach(stringJoiner::add);
            return stringJoiner.toString();
    }
}
