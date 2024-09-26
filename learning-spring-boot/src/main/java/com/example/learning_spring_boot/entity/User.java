package com.example.learning_spring_boot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity // đánh dấu là 1 table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)//tạo id ngẫu nhiên không trùng lặp
    private String id;
    private String userName;
    private String passWord;
    private int age;
    private LocalDate dob;
    private String email;


}
