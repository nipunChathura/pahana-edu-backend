package com.icbt.pahanaedu.request;

import com.icbt.pahanaedu.dto.UserDto;
import lombok.Data;

@Data
public class UserRequest extends Request {
    private Long userId;
    private String userStatus;
    private String searchValue;
    private UserDto userDto;
}
