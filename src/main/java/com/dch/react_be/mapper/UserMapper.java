package com.dch.react_be.mapper;

import com.dch.react_be.model.UserEntity;
import com.dch.react_be.model.UserDto;

public class UserMapper {
    public static UserDto toUserDto(UserEntity user) {
        if(user == null){
            return null;
        }
        return new UserDto(user.getName(), user.getEmail());
    }

    public static UserEntity toUser(UserDto userDto) {
        if(userDto == null){
            return null;
        }
        return new UserEntity(userDto.name(), userDto.email());
    }

    private UserMapper() {
        throw new IllegalStateException("Mapper class");
    }
}
