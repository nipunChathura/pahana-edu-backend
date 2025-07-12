package com.icbt.pahanaedu.response;

import com.icbt.pahanaedu.dto.CategoryDto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse extends Response {
    private CategoryDto categoryDetail;
    private List<CategoryDto> categoryDetailsList;
}
