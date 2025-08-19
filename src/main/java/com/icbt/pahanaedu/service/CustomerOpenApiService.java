package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.CustomerOpenApiDto;

public interface CustomerOpenApiService {
    CustomerOpenApiDto getCustomerInfo(CustomerOpenApiDto customerOpenApiDto);
    CustomerOpenApiDto getCategories(CustomerOpenApiDto customerOpenApiDto);
    CustomerOpenApiDto getPromotions(CustomerOpenApiDto customerOpenApiDto);
    CustomerOpenApiDto getBooks(CustomerOpenApiDto customerOpenApiDto);
}
