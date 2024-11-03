package com.example.learning_spring_boot.service;

import com.example.learning_spring_boot.dto.request.ApiResponse;
import com.example.learning_spring_boot.dto.request.UserCreationRequest;
import com.example.learning_spring_boot.dto.request.UserUpdateRequest;
import com.example.learning_spring_boot.entity.User;
import com.example.learning_spring_boot.enums.Role;
import com.example.learning_spring_boot.exception.AppException;
import com.example.learning_spring_boot.exception.ErrorCode;
import com.example.learning_spring_boot.mapper.UserMapper;
import com.example.learning_spring_boot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)// mọi biến không khai báo dữ liệu sẽ tự động thêm final
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    PasswordEncoder passwordEncoder;//securityConfig

    UserRepository userRepository;
    UserMapper userMapper; // sẽ không cần khởi tạo đối tượng như này UserMapper userMapper = new UserMapper()

    //create
    public User createRequest(UserCreationRequest request) {
        if (userRepository.existsByuserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));

        HashSet<String> roles= new HashSet<>();
        roles.add(Role.USER.name());
        user.setRole(roles);

//        User request1 =  User.builder()
//                .userName(request.getUserName())
//                .age(request.getAge())
//                .passWord(request.getPassWord())
//                .dob(request.getDob())
//                .email(request.getEmail())
//                .build(); dùng bulider

//        User user = new User();
//        user.setUserName(request.getUserName());
//        user.setAge(request.getAge());
//        user.setPassWord(request.getPassWord());
//        user.setDob(request.getDob());
//        user.setEmail(request.getEmail()); cách truyền thống, chỉ có thể dùng build trong case tạo mới


        return userRepository.save(user);

    }

    //get all
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();// chua thong tin ve user dang dang nhap hien tai
        log.info(authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return userRepository.findAll();
    }

    //get one
    @PostAuthorize("returnObject.userName == authentication.name")
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //delete
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    //update
    public User updateUser(String userId, UserUpdateRequest request) {
        User IsExistUser = userRepository.findById(userId).orElseThrow(() -> new AppException((ErrorCode.USER_EXISTED)));
        userMapper.updateUser(IsExistUser, request);
//        HashSet<String> roles= new HashSet<>();
//        roles.add(Role.ADMIN.name());
//        user.setRole(roles);

//        IsExistUser.setUserName(request.getUserName());
//        IsExistUser.setAge(request.getAge());
//        IsExistUser.setPassWord(request.getPassWord());
//        IsExistUser.setDob(request.getDob());
//        IsExistUser.setEmail(request.getEmail());
        return userRepository.save(IsExistUser);
    }

    //myinfo
    public User getMyInfo (){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUserName(name).orElseThrow(()->new AppException(ErrorCode.USER_NOTEXISTED));
        return userMapper.myInfo(user);
    }
}
