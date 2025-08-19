package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.BookManageDto;
import com.icbt.pahanaedu.dto.OrderManageDto;
import com.icbt.pahanaedu.request.BookRequest;
import com.icbt.pahanaedu.request.OrderRequest;
import com.icbt.pahanaedu.response.BookResponse;
import com.icbt.pahanaedu.response.OrderResponse;
import com.icbt.pahanaedu.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public OrderResponse addOrder(@RequestBody OrderRequest request) {

        OrderManageDto orderManageDto = new OrderManageDto();
        BeanUtils.copyProperties(request, orderManageDto);

        OrderManageDto result = orderService.addOrder(orderManageDto);
        OrderResponse response = new OrderResponse();
        response.setOrderId(result.getOrderId());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/get")
    public OrderResponse getOrderById(@RequestParam Long orderId, @RequestParam Long userId, @RequestParam boolean detailsRequested) {
        OrderManageDto orderManageDto = new OrderManageDto();
        orderManageDto.setUserId(userId);
        orderManageDto.setOrderId(orderId);
        orderManageDto.setDetailsRequested(detailsRequested);

        OrderManageDto result = orderService.getOrderById(orderManageDto);
        OrderResponse response = new OrderResponse();
        response.setOrderDto(result.getOrderDto());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/all")
    public OrderResponse getAllOrders(@RequestParam Long userId, @RequestParam boolean detailsRequested) {
        OrderManageDto orderManageDto = new OrderManageDto();
        orderManageDto.setUserId(userId);
        orderManageDto.setDetailsRequested(detailsRequested);

        OrderManageDto result = orderService.getAllOrders(orderManageDto);
        OrderResponse response = new OrderResponse();
        response.setOrderList(result.getOrderList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }
}
