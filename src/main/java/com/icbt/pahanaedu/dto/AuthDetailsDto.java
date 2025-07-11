package com.icbt.pahanaedu.dto;

import lombok.Data;

@Data
public class AuthDetailsDto extends CommonDto {
    private Long userId;
    private String username;
    private String password;
    private String role;
    private String token;
}
