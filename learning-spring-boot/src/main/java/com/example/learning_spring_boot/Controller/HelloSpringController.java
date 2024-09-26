package com.example.learning_spring_boot.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloSpringController {
    @GetMapping("/apsd")
    public String sayHi(){
        return "Fisrt Demo";
    }
}
