package com.icbt.pahanaedu.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderManageDto extends CommonDto {
    private Long userId;
    private Long orderId;
    private Long customerId;
    private boolean detailsRequested;
    private CustomerDto customerDto;
    private OrderDto orderDto;
    private List<OrderDto> orderList;
    private List<OrderDetailDto> detailDetailList;
}
