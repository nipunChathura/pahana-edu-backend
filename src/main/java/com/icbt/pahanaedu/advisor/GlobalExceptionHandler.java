package com.icbt.pahanaedu.advisor;

import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response handleGlobalException(Exception ex) {
        ex.printStackTrace();
        Response response = new Response();
        response.setStatus(ResponseStatus.FAILURE.getStatus());
        response.setResponseMessage(ex.getMessage());
        response.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return response;
    }

    @ExceptionHandler(InvalidRequestException.class)
    public Response handleInvalidRequestException(InvalidRequestException ex) {
        Response response = new Response();
        response.setStatus(ResponseStatus.FAILURE.getStatus());
        response.setResponseMessage(ex.getMessage());
        response.setResponseCode(ex.getCode());
        return response;
    }
}
