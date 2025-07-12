package com.icbt.pahanaedu.request;

import com.icbt.pahanaedu.dto.CustomerDto;
import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest extends Request {
    private Long customerId;
    private CustomerDto customerDto;
    private List<CustomerDto> customerDtoList;
}
