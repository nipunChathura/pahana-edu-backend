package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.BookManageDto;
import com.icbt.pahanaedu.dto.DashboardDto;
import com.icbt.pahanaedu.response.BookResponse;
import com.icbt.pahanaedu.response.DashboardResponse;
import com.icbt.pahanaedu.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{userId}")
    public DashboardResponse getDashboardData(@PathVariable Long userId) {
        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setUserId(userId);

        DashboardDto result = dashboardService.getDashboardData(dashboardDto);
        DashboardResponse response = new DashboardResponse();
        response.setDashboardDetails(result);
        response.setStatus(ResponseStatus.SUCCESS.getStatus());
        response.setResponseCode(ResponseCodes.SUCCESS_CODE);
        response.setResponseMessage("Dashboard Data Getting successfully");

        return response;
    }
}
