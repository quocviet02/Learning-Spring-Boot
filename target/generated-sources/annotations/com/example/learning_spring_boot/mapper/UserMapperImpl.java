package com.example.learning_spring_boot.mapper;

import com.example.learning_spring_boot.dto.request.UserCreationRequest;
import com.example.learning_spring_boot.dto.request.UserUpdateRequest;
import com.example.learning_spring_boot.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-15T02:04:41+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( request.getId() );
        user.userName( request.getUserName() );
        user.passWord( request.getPassWord() );
        user.age( request.getAge() );
        user.dob( request.getDob() );
        user.email( request.getEmail() );

        return user.build();
    }

    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setUserName( request.getUserName() );
        user.setPassWord( request.getPassWord() );
        user.setAge( request.getAge() );
        user.setDob( request.getDob() );
        user.setEmail( request.getEmail() );
    }
}
