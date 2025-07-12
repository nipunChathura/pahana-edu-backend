package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryManageDto extends CommonDto {
    private Long userId;
    private CategoryDto categoryDetail;
    private List<CategoryDto> categoryDetailsList;
}
