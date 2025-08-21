package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.CustomerMangeDto;
import com.icbt.pahanaedu.entity.Customer;

public interface CustomerService {

    public CustomerMangeDto addCustomer(CustomerMangeDto customerMangeDto);
    public CustomerMangeDto updateCustomer(CustomerMangeDto customerMangeDto);
    public CustomerMangeDto deleteCustomer(CustomerMangeDto customerMangeDto);
    public CustomerMangeDto getByCustomerId(CustomerMangeDto customerMangeDto);
    public CustomerMangeDto getAllCustomer(CustomerMangeDto customerMangeDto);

    public Customer upsert(String email, String mobileNumber, String name, String picture);
}
