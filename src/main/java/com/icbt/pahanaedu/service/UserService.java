package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.UserManageDto;

public interface UserService {

    UserManageDto addUser(UserManageDto userManageDto);
    UserManageDto updateUser(UserManageDto userManageDto);
    void validateUsername(String username, Long userId);
    void validatePassword(String username, Long userId);
    UserManageDto deleteUser(UserManageDto userManageDto);
    UserManageDto getUserById(UserManageDto userManageDto);
    UserManageDto getUserAllUser(UserManageDto userManageDto);
    UserManageDto searchUser(UserManageDto userManageDto);
}
