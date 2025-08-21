package com.icbt.pahanaedu.common;

import lombok.Data;
import lombok.Getter;

public enum ResponseStatus {

    SUCCESS("success"),
    FAILURE("failure");

    private final String status;

    private ResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static ResponseStatus getByStatus(String status) {
        for (ResponseStatus requestStatus : values()) {
            if (requestStatus.getStatus().equals(status)) {
                return requestStatus;
            }
        }
        throw new AssertionError("Request status not found for given status [status: " + status + "]");
    }
}
