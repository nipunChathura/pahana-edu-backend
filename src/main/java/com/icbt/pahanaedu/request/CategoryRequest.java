package com.icbt.pahanaedu.request;

import com.icbt.pahanaedu.dto.CategoryDto;
import lombok.Data;

@Data
public class CategoryRequest extends Request {
    private CategoryDto categoryDetail;
}
