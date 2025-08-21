package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.CategoryManageDto;

public interface CategoryService {
    public CategoryManageDto addCategory(CategoryManageDto categoryManageDto);
    public CategoryManageDto updateCategory(CategoryManageDto categoryManageDto);
    public CategoryManageDto deleteCategory(CategoryManageDto categoryManageDto);
    public CategoryManageDto getCategoryById(CategoryManageDto categoryManageDto);
    public CategoryManageDto getCategories(CategoryManageDto categoryManageDto);
    public CategoryManageDto getCategoriesByStatus(CategoryManageDto categoryManageDto);
}
