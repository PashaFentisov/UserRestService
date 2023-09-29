package com.example.userrestservice.mapper;

import com.example.userrestservice.dto.UserDto;
import com.example.userrestservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapper {

  User toEntity(UserDto userDto);

  UserDto toDto(User user);
}
