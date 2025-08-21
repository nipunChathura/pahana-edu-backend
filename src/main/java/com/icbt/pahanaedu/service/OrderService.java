package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.OrderManageDto;

public interface OrderService {
    OrderManageDto addOrder(OrderManageDto orderManageDto);

    OrderManageDto getOrderById(OrderManageDto orderManageDto);

    OrderManageDto getOrderDetailsByOrderId(OrderManageDto orderManageDto);

    OrderManageDto getAllOrders(OrderManageDto orderManageDto);
}
