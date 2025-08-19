package com.icbt.pahanaedu.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOpenRequest extends Request {
    private Long customerId;
    private String email;
    private String phoneNumber;

    private String requestType;
    private Long requestId;
}
