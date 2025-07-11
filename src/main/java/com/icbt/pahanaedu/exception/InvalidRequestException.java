package com.icbt.pahanaedu.exception;

import com.icbt.pahanaedu.common.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidRequestException extends RuntimeException {
    private String status;
    private String code;
    private String message;

    public InvalidRequestException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.setStatus(ResponseStatus.FAILURE.getStatus());
        this.code = errorCode;
        this.message = errorMessage;
    }

}
