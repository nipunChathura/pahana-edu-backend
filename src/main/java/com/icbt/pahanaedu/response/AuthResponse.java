package com.icbt.pahanaedu.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse extends Response {
    private String token;
    private Long userId;
    private String userRole;
    private String username;
}
