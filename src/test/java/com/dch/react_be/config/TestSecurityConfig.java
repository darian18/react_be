package com.dch.react_be.config;

import org.springframework.boot.test.context.TestConfiguration; // Import adnotacji @TestConfiguration
import org.springframework.context.annotation.Bean; // Import klasy @Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Import klasy HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain; // Import klasy SecurityFilterChain

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}