package com.yassine.notini.mapper;

import com.yassine.notini.dto.request.user.CreateUserRequest;
import com.yassine.notini.dto.request.user.UpdateUserRequest;
import com.yassine.notini.dto.response.user.UserDto;
import com.yassine.notini.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public User toEntity(UpdateUserRequest request) {
        return User.builder()
                .id(request.getId())
                .username(request.getUsername())
                .email(request.getEmail())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
