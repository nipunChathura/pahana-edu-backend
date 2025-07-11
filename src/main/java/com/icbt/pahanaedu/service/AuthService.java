package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.AuthDetailsDto;

public interface AuthService {
    public AuthDetailsDto register(AuthDetailsDto authDetailsDto);
    public AuthDetailsDto login(AuthDetailsDto authDetailsDto);
}
