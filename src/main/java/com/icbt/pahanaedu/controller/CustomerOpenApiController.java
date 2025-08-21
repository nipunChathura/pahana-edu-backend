package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.CustomerOpenApiDto;
import com.icbt.pahanaedu.dto.OrderManageDto;
import com.icbt.pahanaedu.request.CustomerOpenRequest;
import com.icbt.pahanaedu.response.CategoryResponse;
import com.icbt.pahanaedu.response.CustomerOpenResponse;
import com.icbt.pahanaedu.response.CustomerResponse;
import com.icbt.pahanaedu.service.BookService;
import com.icbt.pahanaedu.service.CustomerOpenApiService;
import com.icbt.pahanaedu.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open/api/customer")
@CrossOrigin(origins = "*")
public class CustomerOpenApiController {

    @Autowired
    private CustomerOpenApiService customerOpenApiService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    @GetMapping("/categories")
    public CustomerOpenResponse getCategories() {
        CustomerOpenApiDto customerOpenApiDto = new CustomerOpenApiDto();

        CustomerOpenApiDto result = customerOpenApiService.getCategories(customerOpenApiDto);
        CustomerOpenResponse response = new CustomerOpenResponse();
        response.setCategoryDetailsList(result.getCategoryDetailsList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/prmotions")
    public CustomerOpenResponse getPromotions() {
        CustomerOpenApiDto customerOpenApiDto = new CustomerOpenApiDto();

        CustomerOpenApiDto result = customerOpenApiService.getPromotions(customerOpenApiDto);
        CustomerOpenResponse response = new CustomerOpenResponse();
        response.setPromotionDtoList(result.getPromotionDtoList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @PostMapping("/books")
    public CustomerOpenResponse getBooks(@RequestBody CustomerOpenRequest request) {
        System.out.println("CustomerOpenApiController.getBooks");
        CustomerOpenApiDto customerOpenApiDto = new CustomerOpenApiDto();
        BeanUtils.copyProperties(request, customerOpenApiDto);

        CustomerOpenApiDto result = customerOpenApiService.getBooks(customerOpenApiDto);
        CustomerOpenResponse response = new CustomerOpenResponse();
        response.setBookDetailsList(result.getBookDetailsList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @PostMapping("/place/order")
    public CustomerOpenResponse placeOrder(@RequestBody CustomerOpenRequest request) {
        CustomerOpenApiDto customerOpenApiDto = new CustomerOpenApiDto();
        BeanUtils.copyProperties(request, customerOpenApiDto);

        CustomerOpenApiDto result = customerOpenApiService.addOrder(customerOpenApiDto);
        CustomerOpenResponse response = new CustomerOpenResponse();
        response.setOrderId(result.getOrderId());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/order/{orderId}")
    public CustomerOpenResponse getOrderDetails(@PathVariable Long orderId) {
        OrderManageDto orderManageDto = new OrderManageDto();
        orderManageDto.setOrderId(orderId);
        orderManageDto.setDetailsRequested(true);

        OrderManageDto result = orderService.getOrderById(orderManageDto);
        CustomerOpenResponse response = new CustomerOpenResponse();
        response.setOrderDto(result.getOrderDto());
        response.setOrderId(result.getOrderId());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }
}
