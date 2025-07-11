package com.icbt.pahanaedu.common;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    SUCCESS("success"),
    FAILURE("failure");

    private final String status;

    private ResponseStatus(String status) {
        this.status = status;
    }
}
