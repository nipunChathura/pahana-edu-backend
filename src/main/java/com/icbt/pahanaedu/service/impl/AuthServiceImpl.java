package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.AuthDetailsDto;
import com.icbt.pahanaedu.entity.User;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.repository.UserRepository;
import com.icbt.pahanaedu.service.AuthService;
import com.icbt.pahanaedu.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthDetailsDto register(AuthDetailsDto authDetailsDto) {
        log.info(LogSupport.USER_REGISTER + "starting.", authDetailsDto.getUsername());

        if (authDetailsDto.getUsername() == null || authDetailsDto.getUsername().isEmpty()) {
            log.error(LogSupport.USER_REGISTER + "username is required.", authDetailsDto.getUsername());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "username is required");
        }

        if (authDetailsDto.getPassword() == null || authDetailsDto.getPassword().isEmpty()) {
            log.error(LogSupport.USER_REGISTER + "password is required.", authDetailsDto.getUsername());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "password is required");
        }

        if (authDetailsDto.getRole() == null || authDetailsDto.getRole().isEmpty()) {
            log.error(LogSupport.USER_REGISTER + "role is required.", authDetailsDto.getUsername());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "role is required");
        }

        if (userRepository.findByUsername(authDetailsDto.getUsername()).isPresent()) {
            log.error(LogSupport.USER_REGISTER + "username already exists.", authDetailsDto.getUsername());
            authDetailsDto.setStatus(ResponseStatus.FAILURE.getStatus());
            authDetailsDto.setResponseCode(ResponseCodes.FAILED_CODE);
            authDetailsDto.setResponseMessage("Username already exists");
            return authDetailsDto;
        }

        User user = new User();
        user.setUsername(authDetailsDto.getUsername());
        user.setPassword(passwordEncoder.encode(authDetailsDto.getPassword()));
        user.setRole(Constants.ROLE + authDetailsDto.getRole().toUpperCase());

        userRepository.save(user);

        authDetailsDto.setUserId(user.getUserId());
        authDetailsDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        authDetailsDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        authDetailsDto.setResponseMessage("User registered successfully");
        log.info(LogSupport.USER_REGISTER + "end.", authDetailsDto.getUsername());
        return authDetailsDto;
    }

    @Override
    public AuthDetailsDto login(AuthDetailsDto authDetailsDto) {
        log.info(LogSupport.USER_LOGIN + "starting.", authDetailsDto.getUsername());

        if (authDetailsDto.getUsername() == null || authDetailsDto.getUsername().isEmpty()) {
            log.error(LogSupport.USER_LOGIN + "username is required.", authDetailsDto.getUsername());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "username is required");
        }

        if (authDetailsDto.getPassword() == null || authDetailsDto.getPassword().isEmpty()) {
            log.error(LogSupport.USER_LOGIN + "password is required.", authDetailsDto.getUsername());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "password is required");
        }

        User user = userDetailsService.getUsername(authDetailsDto.getUsername());

        if (user == null) {
            log.error(LogSupport.USER_LOGIN + "invalid user credentials.", authDetailsDto.getUsername());
            authDetailsDto.setStatus(ResponseStatus.FAILURE.getStatus());
            authDetailsDto.setResponseCode(ResponseCodes.FAILED_CODE);
            authDetailsDto.setResponseMessage("Invalid user credentials.");
            return authDetailsDto;
        }

        String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        ));
        authDetailsDto.setUserId(user.getUserId());
        authDetailsDto.setToken(token);
        authDetailsDto.setRole(user.getRole());
        authDetailsDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        authDetailsDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        authDetailsDto.setResponseMessage("User login successfully");

        log.info(LogSupport.USER_LOGIN + "end.", authDetailsDto.getUsername());
        return authDetailsDto;
    }
}
