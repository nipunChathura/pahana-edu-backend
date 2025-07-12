package com.icbt.pahanaedu.mapping;

import com.icbt.pahanaedu.dto.CustomerDto;
import com.icbt.pahanaedu.entity.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerDto customerDto);
    CustomerDto toDto(Customer customer);
    List<CustomerDto> toDtoList(List<Customer> customers);
}
