package com.yassine.notini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yassine.notini.dto.request.user.CreateUserRequest;
import com.yassine.notini.dto.request.user.LoginRequest;
import com.yassine.notini.dto.request.user.UpdateUserRequest;
import com.yassine.notini.dto.response.user.LoginResponse;
import com.yassine.notini.dto.response.user.UserDto;
import com.yassine.notini.mapper.UserMapper;
import com.yassine.notini.repository.UserRepository;
import com.yassine.notini.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        return userRepo.findByEmail(request.getEmail())
            .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
            .map(user -> {
                String token = jwtUtil.generateToken(user.getId(), user.getUsername());
                return LoginResponse.builder()
                    .token(token)
                    .id(user.getId())
                    .username(user.getUsername())
                    .build();
            })
            .orElse(null);
    }
    public UserDto register(CreateUserRequest request) {
        var entity = userMapper.toEntity(request);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toDto(userRepo.save(entity));
    }
    public UserDto update(UpdateUserRequest request,String id) {
        if(userRepo.findById(id).isPresent()) {
            var user = userRepo.save(userMapper.toEntity(request));
            return userMapper.toDto(user);
        }
        return null;
    }
    public String deleteUser(String id) {
        userRepo.deleteById(id);
        return id;
    }
}