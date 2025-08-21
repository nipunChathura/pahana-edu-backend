package com.icbt.pahanaedu.controller;

import static org.mockito.Mockito.when;

import com.icbt.pahanaedu.dto.DashboardDto;
import com.icbt.pahanaedu.service.DashboardService;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {DashboardController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DashboardControllerDiffblueTest {
    @Autowired
    private DashboardController dashboardController;

    @MockBean
    private DashboardService dashboardService;

    /**
     * Method under test: {@link DashboardController#getDashboardData(Long)}
     */
    @Test
    void testGetDashboardData() throws Exception {
        // Arrange
        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setIncomes(new HashMap<>());
        dashboardDto.setMembershipCount(new HashMap<>());
        dashboardDto.setOrderCount(new HashMap<>());
        dashboardDto.setPopularBookStock(new HashMap<>());
        dashboardDto.setUserId(1L);
        when(dashboardService.getDashboardData(Mockito.<DashboardDto>any())).thenReturn(dashboardDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/dashboard/{userId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(dashboardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"success\",\"responseCode\":\"00\",\"responseMessage\":\"Dashboard Data Getting successfully\""
                                        + ",\"dashboardDetails\":{\"userId\":1,\"incomes\":{},\"popularBookStock\":{},\"membershipCount\":{},\"orderCount"
                                        + "\":{}}}"));
    }
}
