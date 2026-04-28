package com.yassine.notini.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yassine.notini.dto.request.user.CreateUserRequest;
import com.yassine.notini.dto.request.user.LoginRequest;
import com.yassine.notini.dto.request.user.UpdateUserRequest;
import com.yassine.notini.dto.response.user.LoginResponse;
import com.yassine.notini.dto.response.user.UserDto;
import com.yassine.notini.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/authentication")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
    @PostMapping("/register")
    public UserDto register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.register(createUserRequest);
    }
    @PutMapping("/{id}")
    public UserDto update(@RequestBody UpdateUserRequest UpdateUserRequest,@PathVariable String id) {
        return userService.update(UpdateUserRequest, id);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
    
}
