package com.example.learning_spring_boot.mapper;

import com.example.learning_spring_boot.dto.request.UserCreationRequest;
import com.example.learning_spring_boot.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-24T23:55:27+0700",
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
}
