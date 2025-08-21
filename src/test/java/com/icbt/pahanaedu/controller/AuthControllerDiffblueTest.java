package com.icbt.pahanaedu.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbt.pahanaedu.dto.AuthDetailsDto;
import com.icbt.pahanaedu.request.AuthRequest;
import com.icbt.pahanaedu.request.RegisterRequest;
import com.icbt.pahanaedu.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthControllerDiffblueTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    /**
     * Method under test: {@link AuthController#register(RegisterRequest)}
     */
    @Test
    void testRegister() throws Exception {
        // Arrange
        AuthDetailsDto authDetailsDto = new AuthDetailsDto();
        authDetailsDto.setPassword("iloveyou");
        authDetailsDto.setResponseCode("Response Code");
        authDetailsDto.setResponseMessage("Response Message");
        authDetailsDto.setRole("Role");
        authDetailsDto.setStatus("Status");
        authDetailsDto.setToken("ABC123");
        authDetailsDto.setUserId(1L);
        authDetailsDto.setUserStatus("User Status");
        authDetailsDto.setUsername("janedoe");
        when(authService.register(Mockito.<AuthDetailsDto>any())).thenReturn(authDetailsDto);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword("iloveyou");
        registerRequest.setRole("Role");
        registerRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(registerRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"userId\":1}"));
    }

    /**
     * Method under test: {@link AuthController#login(AuthRequest)}
     */
    @Test
    void testLogin() throws Exception {
        // Arrange
        AuthDetailsDto authDetailsDto = new AuthDetailsDto();
        authDetailsDto.setPassword("iloveyou");
        authDetailsDto.setResponseCode("Response Code");
        authDetailsDto.setResponseMessage("Response Message");
        authDetailsDto.setRole("Role");
        authDetailsDto.setStatus("Status");
        authDetailsDto.setToken("ABC123");
        authDetailsDto.setUserId(1L);
        authDetailsDto.setUserStatus("User Status");
        authDetailsDto.setUsername("janedoe");
        when(authService.login(Mockito.<AuthDetailsDto>any())).thenReturn(authDetailsDto);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setPassword("iloveyou");
        authRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(authRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"token\":\"ABC123"
                                        + "\",\"userId\":1,\"userRole\":\"Role\",\"username\":\"janedoe\"}"));
    }
}
