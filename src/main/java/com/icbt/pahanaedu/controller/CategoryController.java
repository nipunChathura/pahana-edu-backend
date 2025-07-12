package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.CategoryDto;
import com.icbt.pahanaedu.dto.CategoryManageDto;
import com.icbt.pahanaedu.request.CategoryRequest;
import com.icbt.pahanaedu.response.CategoryResponse;
import com.icbt.pahanaedu.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public CategoryResponse addCategory(@RequestBody CategoryRequest request) {

        CategoryManageDto categoryManageDto = new CategoryManageDto();
        BeanUtils.copyProperties(request, categoryManageDto);

        CategoryManageDto result = categoryService.addCategory(categoryManageDto);
        CategoryResponse response = new CategoryResponse();
        response.setCategoryDetail(result.getCategoryDetail());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @PutMapping("/update")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest request) {
        CategoryManageDto categoryManageDto = new CategoryManageDto();
        BeanUtils.copyProperties(request, categoryManageDto);

        CategoryManageDto result = categoryService.updateCategory(categoryManageDto);
        CategoryResponse response = new CategoryResponse();
        response.setCategoryDetail(result.getCategoryDetail());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/id")
    public CategoryResponse getCategoryById(@RequestParam Long categoryId, @RequestParam Long userId) {
        CategoryManageDto categoryManageDto = new CategoryManageDto();
        categoryManageDto.setUserId(userId);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(categoryId);
        categoryManageDto.setCategoryDetail(categoryDto);

        CategoryManageDto result = categoryService.getCategoryById(categoryManageDto);
        CategoryResponse response = new CategoryResponse();
        response.setCategoryDetail(result.getCategoryDetail());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping
    public CategoryResponse getCategories(@RequestParam Long userId) {
        CategoryManageDto categoryManageDto = new CategoryManageDto();
        categoryManageDto.setUserId(userId);

        CategoryManageDto result = categoryService.getCategories(categoryManageDto);
        CategoryResponse response = new CategoryResponse();
        response.setCategoryDetailsList(result.getCategoryDetailsList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }
}
