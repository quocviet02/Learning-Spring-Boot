package com.example.learning_spring_boot.repository;

import com.example.learning_spring_boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByuserName(String userName); // dùng để check xem db đã tồn tại tên chưa
}
