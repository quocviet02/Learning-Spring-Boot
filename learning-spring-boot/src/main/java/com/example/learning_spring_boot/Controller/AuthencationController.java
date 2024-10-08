package com.example.learning_spring_boot.Controller;


import com.example.learning_spring_boot.dto.request.ApiResponse;
import com.example.learning_spring_boot.dto.request.AuthencationRequest;
import com.example.learning_spring_boot.dto.request.IntrospectRequest;
import com.example.learning_spring_boot.repository.AuthencationResponse;
import com.example.learning_spring_boot.repository.IntrospectResponse;
import com.example.learning_spring_boot.service.AuthencationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthencationController {

    AuthencationService authencationService;
    @PostMapping("/token")
    ApiResponse<AuthencationResponse> authenticate(@RequestBody AuthencationRequest request) {
        var result = authencationService.authenticate(request);
        return ApiResponse.<AuthencationResponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authencationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
