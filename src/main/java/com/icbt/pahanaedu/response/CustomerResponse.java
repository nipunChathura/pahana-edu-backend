package com.icbt.pahanaedu.response;

import com.icbt.pahanaedu.dto.CustomerDto;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse extends Response {
    private CustomerDto customerDto;
    private List<CustomerDto> customerDtoList;
}
