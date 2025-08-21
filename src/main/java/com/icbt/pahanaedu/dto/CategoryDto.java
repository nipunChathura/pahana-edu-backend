package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private String categoryStatus;
    private String categoryIconUrl;
}
