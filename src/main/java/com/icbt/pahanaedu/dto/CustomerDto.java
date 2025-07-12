package com.icbt.pahanaedu.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long customerId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String membershipType;
    private String status;
}
