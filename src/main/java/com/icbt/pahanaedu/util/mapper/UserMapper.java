package com.icbt.pahanaedu.util.mapper;

import com.icbt.pahanaedu.dto.UserDto;
import com.icbt.pahanaedu.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    UserDto toDto(User user);
    List<UserDto> toDtoList(List<User> users);
}
