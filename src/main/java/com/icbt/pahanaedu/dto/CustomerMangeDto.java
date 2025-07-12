package com.icbt.pahanaedu.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerMangeDto extends CommonDto {
    private Long userId;
    private Long customerId;
    private CustomerDto customerDto;
    private List<CustomerDto> customerDtoList;
}
