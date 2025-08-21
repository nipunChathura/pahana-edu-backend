package com.icbt.pahanaedu.util.mapper;

import com.icbt.pahanaedu.dto.OrderDto;
import com.icbt.pahanaedu.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDto orderDto);
    OrderDto toDto(Order order);
}
