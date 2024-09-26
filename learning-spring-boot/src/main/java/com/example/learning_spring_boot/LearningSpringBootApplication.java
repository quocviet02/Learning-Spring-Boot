package com.example.learning_spring_boot;

import org.hibernate.Version;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LearningSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningSpringBootApplication.class, args);
		System.out.println("Hibernate Vddddddddddddddddddddddddđsssssssssssssssssersion: " + Version.getVersionString());
	}

}
