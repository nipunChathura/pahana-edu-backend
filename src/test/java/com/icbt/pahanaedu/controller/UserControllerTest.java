package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.UserManageDto;
import com.icbt.pahanaedu.request.UserRequest;
import com.icbt.pahanaedu.response.UserResponse;
import com.icbt.pahanaedu.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        UserRequest request = new UserRequest();

        UserManageDto dtoFromService = new UserManageDto();
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("User saved");

        when(userService.addUser(any(UserManageDto.class))).thenReturn(dtoFromService);

        UserResponse response = userController.saveUser(request);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("User saved", response.getResponseMessage());
        verify(userService, times(1)).addUser(any(UserManageDto.class));
    }

    @Test
    void testUpdateUser() {
        UserRequest request = new UserRequest();

        UserManageDto dtoFromService = new UserManageDto();
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("User updated");

        when(userService.updateUser(any(UserManageDto.class))).thenReturn(dtoFromService);

        UserResponse response = userController.updateUser(request);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("User updated", response.getResponseMessage());
        verify(userService, times(1)).updateUser(any(UserManageDto.class));
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;

        UserManageDto dtoFromService = new UserManageDto();
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("User fetched");

        when(userService.getUserById(any(UserManageDto.class))).thenReturn(dtoFromService);

        UserResponse response = userController.updateUser(userId);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("User fetched", response.getResponseMessage());
        verify(userService, times(1)).getUserById(any(UserManageDto.class));
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        UserManageDto dtoFromService = new UserManageDto();
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("User deleted");

        when(userService.deleteUser(any(UserManageDto.class))).thenReturn(dtoFromService);

        UserResponse response = userController.deleteUser(userId);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("User deleted", response.getResponseMessage());
        verify(userService, times(1)).deleteUser(any(UserManageDto.class));
    }

    @Test
    void testGetAllUser() {
        Long userId = 1L;

        UserManageDto dtoFromService = new UserManageDto();
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("All users fetched");
        dtoFromService.setUserDtos(Collections.emptyList());

        when(userService.getUserAllUser(any(UserManageDto.class))).thenReturn(dtoFromService);

        UserResponse response = userController.getAllUser(userId);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("All users fetched", response.getResponseMessage());
        assertEquals(0, response.getUserDtos().size());
        verify(userService, times(1)).getUserAllUser(any(UserManageDto.class));
    }

    @Test
    void testSearchUser() {
        Long userId = 1L;
        String searchValue = "John";
        String status = "ACTIVE";

        UserManageDto dtoFromService = new UserManageDto();
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("User search completed");
        dtoFromService.setUserDtos(Collections.emptyList());

        when(userService.searchUser(any(UserManageDto.class))).thenReturn(dtoFromService);

        UserResponse response = userController.searchUser(userId, searchValue, status);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("User search completed", response.getResponseMessage());
        assertEquals(0, response.getUserDtos().size());
        verify(userService, times(1)).searchUser(any(UserManageDto.class));
    }
}
