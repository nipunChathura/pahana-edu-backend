package com.icbt.pahanaedu.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GcpResponse extends Response {
    private String imageUrl;
    private String fileName;
    private boolean isDeleted;
}
