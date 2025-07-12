package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.CustomerMangeDto;

public interface CustomerService {

    public CustomerMangeDto addCustomer(CustomerMangeDto customerMangeDto);
    public CustomerMangeDto updateCustomer(CustomerMangeDto customerMangeDto);
    public CustomerMangeDto getByCustomerId(CustomerMangeDto customerMangeDto);
    public CustomerMangeDto getAllCustomer(CustomerMangeDto customerMangeDto);
}
