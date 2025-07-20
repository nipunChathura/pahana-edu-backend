package com.icbt.pahanaedu.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icbt.pahanaedu.dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends Response {
    private Long userId;
    private UserDto userDto;
    private List<UserDto> userDtos;
}
