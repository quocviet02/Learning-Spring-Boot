package com.example.learning_spring_boot.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuthencationRequest {
    String userName;
    String passWord;
}
