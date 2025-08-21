package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDetailsDto extends CommonDto {
    private Long userId;
    private String username;
    private String password;
    private String role;
    private String token;
    private String userStatus;
}
