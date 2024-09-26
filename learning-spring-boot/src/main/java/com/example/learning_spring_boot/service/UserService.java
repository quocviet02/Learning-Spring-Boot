package com.example.learning_spring_boot.service;

import com.example.learning_spring_boot.dto.request.UserCreationRequest;
import com.example.learning_spring_boot.dto.request.UserUpdateRequest;
import com.example.learning_spring_boot.entity.User;
import com.example.learning_spring_boot.exception.AppException;
import com.example.learning_spring_boot.exception.ErrorCode;
import com.example.learning_spring_boot.mapper.UserMapper;
import com.example.learning_spring_boot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)// mọi biến không khai báo dữ liệu sẽ tự động thêm final
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper; // sẽ không cần khởi tạo đối tượng như này UserMapper userMapper = new UserMapper()

    //create
    public User createRequest(UserCreationRequest request) {
        if (userRepository.existsByuserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);

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
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //get one
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

//        IsExistUser.setUserName(request.getUserName());
//        IsExistUser.setAge(request.getAge());
//        IsExistUser.setPassWord(request.getPassWord());
//        IsExistUser.setDob(request.getDob());
//        IsExistUser.setEmail(request.getEmail());
        return userRepository.save(IsExistUser);
    }
}
