package com.example.userrestservice.mapper;

import com.example.userrestservice.dto.UserSavingDto;
import com.example.userrestservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserSavingMapper {
    User toEntity(UserSavingDto userSavingDto);

    UserSavingDto toDto(User user);
}
