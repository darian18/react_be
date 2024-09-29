package com.dch.react_be.controller;

import com.dch.react_be.mapper.UserMapper;
import com.dch.react_be.model.UserDto;
import com.dch.react_be.repository.UserRepository;
import com.dch.react_be.exception.UserAlreadyExistsException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {
    public static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "UserEntity with this email already exists.";
    public static final String USER_WITH_THIS_NAME_ALREADY_EXISTS = "UserEntity with this name already exists.";
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto user) {
        if (userRepository.findByEmail(user.email()).isPresent()) {
            throw new UserAlreadyExistsException(USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.findByName(user.name()).isPresent()) {
            throw new UserAlreadyExistsException(USER_WITH_THIS_NAME_ALREADY_EXISTS);
        }
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(user)));
    }
}