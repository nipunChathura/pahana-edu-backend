package com.icbt.pahanaedu.response;

import com.icbt.pahanaedu.entity.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleLoginResponse extends Response {
    private Customer customerDto;
}
