package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.AuthDetailsDto;
import com.icbt.pahanaedu.entity.User;
import com.icbt.pahanaedu.repository.UserRepository;
import com.icbt.pahanaedu.request.AuthRequest;
import com.icbt.pahanaedu.request.RegisterRequest;
import com.icbt.pahanaedu.response.AuthResponse;
import com.icbt.pahanaedu.service.AuthService;
import com.icbt.pahanaedu.service.impl.CustomUserDetailsService;
import com.icbt.pahanaedu.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        AuthDetailsDto authDetailsDto = new AuthDetailsDto();
        BeanUtils.copyProperties(request, authDetailsDto);

        AuthDetailsDto result = authService.register(authDetailsDto);
        AuthResponse response = new AuthResponse();
        response.setUserId(result.getUserId());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;

    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        AuthDetailsDto authDetailsDto = new AuthDetailsDto();
        BeanUtils.copyProperties(request, authDetailsDto);

        AuthDetailsDto result = authService.login(authDetailsDto);
        AuthResponse response = new AuthResponse();
        response.setToken(result.getToken());
        response.setUserId(result.getUserId());
        response.setUserRole(result.getRole());
        response.setUsername(result.getUsername());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }
}
