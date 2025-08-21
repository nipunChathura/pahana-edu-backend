package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.OrderManageDto;
import com.icbt.pahanaedu.request.OrderRequest;
import com.icbt.pahanaedu.response.OrderResponse;
import com.icbt.pahanaedu.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrder() {
        // Arrange
        OrderRequest request = new OrderRequest();
        request.setUserId(1L);
        request.setOrderDto(null); // set other fields if needed

        OrderManageDto dtoFromService = new OrderManageDto();
        dtoFromService.setOrderId(100L);
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("Order added successfully");

        when(orderService.addOrder(any(OrderManageDto.class))).thenReturn(dtoFromService);

        // Act
        OrderResponse response = orderController.addOrder(request);

        // Assert
        assertEquals(100L, response.getOrderId());
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("Order added successfully", response.getResponseMessage());

        verify(orderService, times(1)).addOrder(any(OrderManageDto.class));
    }

    @Test
    void testGetOrderById() {
        // Arrange
        Long orderId = 100L;
        Long userId = 1L;
        boolean detailsRequested = true;

        OrderManageDto dtoFromService = new OrderManageDto();
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("Order fetched successfully");
        dtoFromService.setOrderDto(null); // set a mock orderDto if needed

        when(orderService.getOrderById(any(OrderManageDto.class))).thenReturn(dtoFromService);

        // Act
        OrderResponse response = orderController.getOrderById(orderId, userId, detailsRequested);

        // Assert
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("Order fetched successfully", response.getResponseMessage());

        verify(orderService, times(1)).getOrderById(any(OrderManageDto.class));
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        Long userId = 1L;
        boolean detailsRequested = true;

        OrderManageDto dtoFromService = new OrderManageDto();
        dtoFromService.setStatus("SUCCESS");
        dtoFromService.setResponseCode("200");
        dtoFromService.setResponseMessage("All orders fetched");
        dtoFromService.setOrderList(Collections.emptyList());

        when(orderService.getAllOrders(any(OrderManageDto.class))).thenReturn(dtoFromService);

        // Act
        OrderResponse response = orderController.getAllOrders(userId, detailsRequested);

        // Assert
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("All orders fetched", response.getResponseMessage());
        assertEquals(0, response.getOrderList().size());

        verify(orderService, times(1)).getAllOrders(any(OrderManageDto.class));
    }
}
