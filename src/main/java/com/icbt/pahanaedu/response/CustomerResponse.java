package com.icbt.pahanaedu.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icbt.pahanaedu.dto.CustomerDto;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse extends Response {
    private CustomerDto customerDto;
    private List<CustomerDto> customerDtoList;
}
