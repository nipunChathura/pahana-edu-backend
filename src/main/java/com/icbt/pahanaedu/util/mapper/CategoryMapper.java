package com.icbt.pahanaedu.util.mapper;

import com.icbt.pahanaedu.dto.CategoryDto;
import com.icbt.pahanaedu.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);
    CategoryDto toDto(Category category);
    List<CategoryDto> toDtoList(List<Category> categories);
}
