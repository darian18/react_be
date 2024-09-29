package com.dch.react_be.controller;

import com.dch.react_be.mapper.UserMapper;
import com.dch.react_be.model.UserEntity;
import com.dch.react_be.model.UserDto;
import com.dch.react_be.repository.UserRepository;
import com.dch.react_be.config.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUserSuccess() throws Exception {
        // given
        UserDto userDto = new UserDto("Jan", "jan@example.com");
        UserEntity user = UserMapper.toUser(userDto);

        when(userRepository.findByEmail(userDto.email())).thenReturn(Optional.empty());
        when(userRepository.findByName(userDto.name())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        final String userJson = objectMapper.writeValueAsString(userDto);

        // when then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }

    @Test
    public void testCreateUserAlreadyExists() throws Exception {
        UserDto user = new UserDto("Jan", "jan@example.com");

        when(userRepository.findByEmail(user.email())).thenReturn(Optional.of(UserMapper.toUser(user)));

        final String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(UserController.USER_WITH_THIS_EMAIL_ALREADY_EXISTS));
    }
}