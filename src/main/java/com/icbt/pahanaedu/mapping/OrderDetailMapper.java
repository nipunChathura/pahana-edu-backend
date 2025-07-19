package com.icbt.pahanaedu.mapping;

import com.icbt.pahanaedu.dto.OrderDetailDto;
import com.icbt.pahanaedu.entity.OrderDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetails toEntity(OrderDetailDto orderDetailDto);
    OrderDetailDto toDto(OrderDetails orderDetails);
}
