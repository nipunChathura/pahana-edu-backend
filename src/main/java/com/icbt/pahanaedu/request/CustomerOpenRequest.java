package com.icbt.pahanaedu.request;

import com.icbt.pahanaedu.dto.CustomerDto;
import com.icbt.pahanaedu.dto.OrderDetailDto;
import com.icbt.pahanaedu.dto.OrderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerOpenRequest extends Request {
    private Long customerId;
    private String email;
    private String phoneNumber;

    private String requestType;
    private Long requestId;

    private Long orderId;
    private boolean detailsRequested;
    private CustomerDto customerDto;
    private OrderDto orderDto;
    private List<OrderDto> orderList;
    private List<OrderDetailDto> detailDetailList;
}
