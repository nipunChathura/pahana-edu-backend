package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private Long customerId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String membershipType;
    private String status;
}
