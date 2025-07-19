package com.icbt.pahanaedu.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icbt.pahanaedu.dto.CustomerDto;
import com.icbt.pahanaedu.dto.OrderDetailDto;
import com.icbt.pahanaedu.dto.OrderDto;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse extends Response {
    private Long orderId;
    private Long customerId;
    private CustomerDto customerDto;
    private OrderDto orderDto;
    private List<OrderDto> orderList;
    private List<OrderDetailDto> detailDetailList;
}
