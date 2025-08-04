package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.CustomerMangeDto;
import com.icbt.pahanaedu.request.CategoryRequest;
import com.icbt.pahanaedu.request.CustomerRequest;
import com.icbt.pahanaedu.response.CustomerResponse;
import com.icbt.pahanaedu.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public CustomerResponse addCustomer(@RequestBody CustomerRequest request) {

        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        BeanUtils.copyProperties(request, customerMangeDto);

        CustomerMangeDto result = customerService.addCustomer(customerMangeDto);

        CustomerResponse response = new CustomerResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @PutMapping("/update")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest request) {
        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        BeanUtils.copyProperties(request, customerMangeDto);

        CustomerMangeDto result = customerService.updateCustomer(customerMangeDto);

        CustomerResponse response = new CustomerResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/id/{userId}")
    public CustomerResponse getByCustomerId(@PathVariable Long userId, @RequestParam Long customerId) {
        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        customerMangeDto.setUserId(userId);
        customerMangeDto.setCustomerId(customerId);

        CustomerMangeDto result = customerService.getByCustomerId(customerMangeDto);

        CustomerResponse response = new CustomerResponse();
        response.setCustomerDto(result.getCustomerDto());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/all/{userId}")
    public CustomerResponse getByCustomerId(@PathVariable Long userId) {
        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        customerMangeDto.setUserId(userId);

        CustomerMangeDto result = customerService.getAllCustomer(customerMangeDto);

        CustomerResponse response = new CustomerResponse();
        response.setCustomerDtoList(result.getCustomerDtoList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @DeleteMapping("/delete")
    public CustomerResponse deleteCustomer(@RequestBody CustomerRequest request) {
        CustomerMangeDto customerMangeDto = new CustomerMangeDto();
        BeanUtils.copyProperties(request, customerMangeDto);

        CustomerMangeDto result = customerService.deleteCustomer(customerMangeDto);

        CustomerResponse response = new CustomerResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }
}
