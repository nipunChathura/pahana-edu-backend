package com.icbt.pahanaedu.response;

import com.icbt.pahanaedu.dto.DashboardDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardResponse extends Response {
    private DashboardDto dashboardDetails;
}
