package com.icbt.pahanaedu.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icbt.pahanaedu.dto.CategoryDto;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse extends Response {
    private CategoryDto categoryDetail;
    private List<CategoryDto> categoryDetailsList;
}
