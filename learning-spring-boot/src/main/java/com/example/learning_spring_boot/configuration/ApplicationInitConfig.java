package com.example.learning_spring_boot.configuration;

import com.example.learning_spring_boot.entity.User;
import com.example.learning_spring_boot.enums.Role;
import com.example.learning_spring_boot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
// tạo 1 user admin khi start ứng dụng
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)// mọi biến không khai báo dữ liệu sẽ tự động thêm final
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            //kiểm tra xem đã có user là admin hay chưa, chưa co
            if (userRepository.findByUserName("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .userName("admin")
                        .passWord(passwordEncoder.encode("admin"))
                        .role(roles)
                        .build();
                userRepository.save(user);
                log.warn("user create with default role is admin, and password:admin, pls change it");
            }
        };
    }
}
