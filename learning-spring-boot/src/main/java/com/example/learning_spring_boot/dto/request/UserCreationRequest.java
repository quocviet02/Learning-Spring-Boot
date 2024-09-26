package com.example.learning_spring_boot.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {


    String id;
    @Size(min = 3, message = "USERNAME_INVALID")// phải truyền vào như này
    String userName;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String passWord;
    int age;
    LocalDate dob;

    @NotEmpty(message = "IVALID_EMAIL")
    @Email(message = "IVALID_EMAIL")
    String email;


}
