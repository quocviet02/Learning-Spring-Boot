package com.example.learning_spring_boot.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    String userName;
    String passWord;
    int age;
    LocalDate dob;

    @NotEmpty(message = "IVALID_EMAIL")
    @Email(message = "IVALID_EMAIL")
    String email;
}
