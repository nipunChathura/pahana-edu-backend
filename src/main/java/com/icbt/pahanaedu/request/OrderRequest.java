package com.icbt.pahanaedu.request;

import com.icbt.pahanaedu.dto.CustomerDto;
import com.icbt.pahanaedu.dto.OrderDetailDto;
import com.icbt.pahanaedu.dto.OrderDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest extends Request {
    private Long orderId;
    private Long customerId;
    private boolean detailsRequested;
    private CustomerDto customerDto;
    private OrderDto orderDto;
    private List<OrderDto> orderList;
    private List<OrderDetailDto> detailDetailList;
}
