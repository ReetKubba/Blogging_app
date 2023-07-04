package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payloads.UserDto;
@RestController
@Service
public interface UserService {
    UserDto registerNewUser(UserDto user);
    
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
