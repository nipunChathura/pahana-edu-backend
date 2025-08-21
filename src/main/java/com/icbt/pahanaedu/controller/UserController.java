package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.UserManageDto;
import com.icbt.pahanaedu.request.UserRequest;
import com.icbt.pahanaedu.response.UserResponse;
import com.icbt.pahanaedu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public UserResponse saveUser(@RequestBody UserRequest request) {
        UserManageDto userManageDto = new UserManageDto();
        BeanUtils.copyProperties(request, userManageDto);

        UserManageDto result = userService.addUser(userManageDto);
        UserResponse response = new UserResponse();
        response.setUserDto(result.getUserDto());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());
        return response;
    }

    @PutMapping("/update")
    public UserResponse updateUser(@RequestBody UserRequest request) {
        UserManageDto userManageDto = new UserManageDto();
        BeanUtils.copyProperties(request, userManageDto);

        UserManageDto result = userService.updateUser(userManageDto);
        UserResponse response = new UserResponse();
        response.setUserDto(result.getUserDto());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());
        return response;
    }

    @GetMapping("/get")
    public UserResponse updateUser(@RequestParam Long userId) {
        UserManageDto userManageDto = new UserManageDto();
        userManageDto.setUserId(userId);

        UserManageDto result = userService.getUserById(userManageDto);
        UserResponse response = new UserResponse();
        response.setUserDto(result.getUserDto());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());
        return response;
    }

    @DeleteMapping("/delete")
    public UserResponse deleteUser(@RequestParam Long userId) {
        UserManageDto userManageDto = new UserManageDto();
        userManageDto.setUserId(userId);

        UserManageDto result = userService.deleteUser(userManageDto);
        UserResponse response = new UserResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());
        return response;
    }

    @GetMapping("/all/{userId}")
    public UserResponse getAllUser(@PathVariable Long userId) {
        UserManageDto userManageDto = new UserManageDto();
        userManageDto.setUserId(userId);

        UserManageDto result = userService.getUserAllUser(userManageDto);
        UserResponse response = new UserResponse();
        response.setUserDtos(result.getUserDtos());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());
        return response;
    }

    @GetMapping("/search/{userId}")
    public UserResponse searchUser(@PathVariable Long userId, @RequestParam String searchValue, @RequestParam String status) {
        UserManageDto userManageDto = new UserManageDto();
        userManageDto.setUserId(userId);
        userManageDto.setUserStatus(status);
        userManageDto.setSearchValue(searchValue);

        UserManageDto result = userService.searchUser(userManageDto);
        UserResponse response = new UserResponse();
        response.setUserDtos(result.getUserDtos());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());
        return response;
    }
}
