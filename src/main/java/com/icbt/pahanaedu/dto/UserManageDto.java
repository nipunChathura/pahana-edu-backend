package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserManageDto extends CommonDto {
    private Long userId;
    private UserDto userDto;
    private String userStatus;
    private String searchValue;
    private List<UserDto> userDtos;
}
