package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private Long orderId;
    private Long customerId;
    private CustomerDto customer;
    private Date orderDate;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal paidAmount;
    private String paymentType;
    private List<OrderDetailDto> orderDetailDtos;
}
