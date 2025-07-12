package com.icbt.pahanaedu.service.impl;

import com.icbt.pahanaedu.common.Constants;
import com.icbt.pahanaedu.common.LogSupport;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.CategoryDto;
import com.icbt.pahanaedu.dto.CategoryManageDto;
import com.icbt.pahanaedu.entity.Category;
import com.icbt.pahanaedu.exception.InvalidRequestException;
import com.icbt.pahanaedu.mapping.CategoryMapper;
import com.icbt.pahanaedu.repository.CategoryRepository;
import com.icbt.pahanaedu.service.CategoryService;
import com.icbt.pahanaedu.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryManageDto addCategory(CategoryManageDto categoryManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "addCategory()", categoryManageDto.getUserId());
        if (categoryManageDto.getCategoryDetail() == null) {
            log.error(LogSupport.BOOK_LOG + "category data is required.", "addCategory()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "category data  is required");
        }
        CategoryDto categoryDetail = categoryManageDto.getCategoryDetail();

        if (categoryDetail.getCategoryName() == null || categoryDetail.getCategoryName().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "name is required.", "addCategory()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "name is required");
        }

        Category category = categoryMapper.toEntity(categoryDetail);
        category.setCategoryStatus(Constants.ACTIVE_STATUS);
        category.setCreatedBy(categoryManageDto.getUserId());
        category.setCreatedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        categoryRepository.save(category);

        categoryDetail.setCategoryId(category.getCategoryId());
        categoryDetail.setCategoryStatus(category.getCategoryStatus());
        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        categoryManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        categoryManageDto.setResponseMessage("Category saving successfully");
        log.info(LogSupport.BOOK_LOG + "end.", "addCategory()", categoryManageDto.getUserId());
        return categoryManageDto;
    }

    @Override
    public CategoryManageDto updateCategory(CategoryManageDto categoryManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "updateCategory()", categoryManageDto.getUserId());
        if (categoryManageDto.getCategoryDetail() == null) {
            log.error(LogSupport.BOOK_LOG + "category data is required.", "updateCategory()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "category data  is required");
        }
        CategoryDto categoryDetail = categoryManageDto.getCategoryDetail();

        if (categoryDetail.getCategoryId() == null) {
            log.error(LogSupport.BOOK_LOG + "categoryId is required.", "updateCategory()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "categoryId is required");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDetail.getCategoryId());
        if (optionalCategory.isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "invalid categoryId.", "updateCategory()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_CATEGORY_ID_CODE, "Invalid Category Id");
        }

        if (categoryDetail.getCategoryName() == null || categoryDetail.getCategoryName().isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "categoryName is required.", "updateCategory()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "categoryName is required");
        }

        Category category = categoryMapper.toEntity(categoryDetail);
        category.setModifiedBy(categoryManageDto.getUserId());
        category.setModifiedDatetime(Utils.getCurrentDateByTimeZone(Constants.TIME_ZONE));

        categoryRepository.save(category);

        categoryDetail.setCategoryId(category.getCategoryId());
        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        categoryManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        categoryManageDto.setResponseMessage("Category updating successfully");
        log.info(LogSupport.BOOK_LOG + "end.", "updateCategory()", categoryManageDto.getUserId());
        return categoryManageDto;
    }

    @Override
    public CategoryManageDto getCategoryById(CategoryManageDto categoryManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "getCategoryById()", categoryManageDto.getUserId());
        if (categoryManageDto.getCategoryDetail() == null) {
            log.error(LogSupport.BOOK_LOG + "category data is required.", "getCategoryById()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "category data  is required");
        }
        CategoryDto categoryDetail = categoryManageDto.getCategoryDetail();

        if (categoryDetail.getCategoryId() == null) {
            log.error(LogSupport.BOOK_LOG + "categoryId is required.", "getCategoryById()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.MISSING_PARAMETER_CODE, "categoryId is required");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDetail.getCategoryId());
        if (optionalCategory.isEmpty()) {
            log.error(LogSupport.BOOK_LOG + "invalid categoryId.", "getCategoryById()", categoryManageDto.getUserId());
            throw new InvalidRequestException(ResponseCodes.INVALID_CATEGORY_ID_CODE, "Invalid Category Id");
        }

        categoryDetail = categoryMapper.toDto(optionalCategory.get());

        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        categoryManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        categoryManageDto.setResponseMessage("Category finding successfully");
        log.info(LogSupport.BOOK_LOG + "end.", "getCategoryById()", categoryManageDto.getUserId());
        return categoryManageDto;
    }

    @Override
    public CategoryManageDto getCategories(CategoryManageDto categoryManageDto) {
        log.info(LogSupport.BOOK_LOG + "starting.", "getCategories()", categoryManageDto.getUserId());

        List<CategoryDto> categoryDtos = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            log.info(LogSupport.BOOK_LOG + "categories is empty.", "getCategories()", categoryManageDto.getUserId());
            categoryManageDto.setCategoryDetailsList(categoryDtos);
            categoryManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            categoryManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            categoryManageDto.setResponseMessage("Category getting successfully");
            return categoryManageDto;
        }

        categories.forEach(category -> {
            if (category.getCategoryStatus().equalsIgnoreCase(Constants.ACTIVE_STATUS)) {
                categoryDtos.add(categoryMapper.toDto(category));
            }
        });

        categoryManageDto.setCategoryDetailsList(categoryDtos);
        categoryManageDto.setStatus(ResponseStatus.SUCCESS.getStatus());
        categoryManageDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
        categoryManageDto.setResponseMessage("Category getting successfully");
        log.info(LogSupport.BOOK_LOG + "end.", "getCategories()", categoryManageDto.getUserId());
        return categoryManageDto;
    }
}
