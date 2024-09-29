package com.dch.react_be.integration;

import com.dch.react_be.mapper.UserMapper;
import com.dch.react_be.model.UserEntity;
import com.dch.react_be.model.UserDto;
import com.dch.react_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Transactional
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @Rollback(false)
    public void testSaveUser() {
        UserDto user = new UserDto("Anna", "anna@example.com");
        UserEntity savedUser = userRepository.save(UserMapper.toUser(user));

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindByEmail() {
        UserDto user = new UserDto("Marek", "marek@example.com");
        userRepository.save(UserMapper.toUser(user));

        UserEntity foundUser = userRepository.findByEmail("marek@example.com").orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("marek@example.com");
    }

    @Test
    public void testDeleteUser() {
        UserDto user = new UserDto("Kasia", "kasia@example.com");
        UserEntity savedUser = userRepository.save(UserMapper.toUser(user));
        userRepository.delete(savedUser);

        UserEntity deletedUser = userRepository.findByEmail("kasia@example.com").orElse(null);
        assertThat(deletedUser).isNull();
    }
}