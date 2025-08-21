package com.icbt.pahanaedu.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbt.pahanaedu.dto.CategoryDto;
import com.icbt.pahanaedu.dto.CategoryManageDto;
import com.icbt.pahanaedu.request.CategoryRequest;
import com.icbt.pahanaedu.service.CategoryService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CategoryController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CategoryControllerDiffblueTest {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    /**
     * Method under test: {@link CategoryController#addCategory(CategoryRequest)}
     */
    @Test
    void testAddCategory() throws Exception {
        // Arrange
        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        CategoryManageDto categoryManageDto = new CategoryManageDto();
        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setCategoryDetailsList(new ArrayList<>());
        categoryManageDto.setCategoryStatus("Category Status");
        categoryManageDto.setResponseCode("Response Code");
        categoryManageDto.setResponseMessage("Response Message");
        categoryManageDto.setStatus("Status");
        categoryManageDto.setUserId(1L);
        when(categoryService.addCategory(Mockito.<CategoryManageDto>any())).thenReturn(categoryManageDto);

        CategoryDto categoryDetail2 = new CategoryDto();
        categoryDetail2.setCategoryIconUrl("https://example.org/example");
        categoryDetail2.setCategoryId(1L);
        categoryDetail2.setCategoryName("Category Name");
        categoryDetail2.setCategoryStatus("Category Status");

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryDetail(categoryDetail2);
        categoryRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(categoryRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/categories/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"categoryDetail"
                                        + "\":{\"categoryId\":1,\"categoryName\":\"Category Name\",\"categoryStatus\":\"Category Status\",\"categoryIconUrl"
                                        + "\":\"https://example.org/example\"}}"));
    }

    /**
     * Method under test: {@link CategoryController#updateCategory(CategoryRequest)}
     */
    @Test
    void testUpdateCategory() throws Exception {
        // Arrange
        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        CategoryManageDto categoryManageDto = new CategoryManageDto();
        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setCategoryDetailsList(new ArrayList<>());
        categoryManageDto.setCategoryStatus("Category Status");
        categoryManageDto.setResponseCode("Response Code");
        categoryManageDto.setResponseMessage("Response Message");
        categoryManageDto.setStatus("Status");
        categoryManageDto.setUserId(1L);
        when(categoryService.updateCategory(Mockito.<CategoryManageDto>any())).thenReturn(categoryManageDto);

        CategoryDto categoryDetail2 = new CategoryDto();
        categoryDetail2.setCategoryIconUrl("https://example.org/example");
        categoryDetail2.setCategoryId(1L);
        categoryDetail2.setCategoryName("Category Name");
        categoryDetail2.setCategoryStatus("Category Status");

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryDetail(categoryDetail2);
        categoryRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(categoryRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/categories/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"categoryDetail"
                                        + "\":{\"categoryId\":1,\"categoryName\":\"Category Name\",\"categoryStatus\":\"Category Status\",\"categoryIconUrl"
                                        + "\":\"https://example.org/example\"}}"));
    }

    /**
     * Method under test: {@link CategoryController#deleteCategory(CategoryRequest)}
     */
    @Test
    void testDeleteCategory() throws Exception {
        // Arrange
        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        CategoryManageDto categoryManageDto = new CategoryManageDto();
        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setCategoryDetailsList(new ArrayList<>());
        categoryManageDto.setCategoryStatus("Category Status");
        categoryManageDto.setResponseCode("Response Code");
        categoryManageDto.setResponseMessage("Response Message");
        categoryManageDto.setStatus("Status");
        categoryManageDto.setUserId(1L);
        when(categoryService.deleteCategory(Mockito.<CategoryManageDto>any())).thenReturn(categoryManageDto);

        CategoryDto categoryDetail2 = new CategoryDto();
        categoryDetail2.setCategoryIconUrl("https://example.org/example");
        categoryDetail2.setCategoryId(1L);
        categoryDetail2.setCategoryName("Category Name");
        categoryDetail2.setCategoryStatus("Category Status");

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryDetail(categoryDetail2);
        categoryRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(categoryRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/categories/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"categoryDetail"
                                        + "\":{\"categoryId\":1,\"categoryName\":\"Category Name\",\"categoryStatus\":\"Category Status\",\"categoryIconUrl"
                                        + "\":\"https://example.org/example\"}}"));
    }

    /**
     * Method under test: {@link CategoryController#getCategoryById(Long, Long)}
     */
    @Test
    void testGetCategoryById() throws Exception {
        // Arrange
        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        CategoryManageDto categoryManageDto = new CategoryManageDto();
        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setCategoryDetailsList(new ArrayList<>());
        categoryManageDto.setCategoryStatus("Category Status");
        categoryManageDto.setResponseCode("Response Code");
        categoryManageDto.setResponseMessage("Response Message");
        categoryManageDto.setStatus("Status");
        categoryManageDto.setUserId(1L);
        when(categoryService.getCategoryById(Mockito.<CategoryManageDto>any())).thenReturn(categoryManageDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/categories/id");
        MockHttpServletRequestBuilder paramResult = getResult.param("categoryId", String.valueOf(1L));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("userId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"categoryDetail"
                                        + "\":{\"categoryId\":1,\"categoryName\":\"Category Name\",\"categoryStatus\":\"Category Status\",\"categoryIconUrl"
                                        + "\":\"https://example.org/example\"}}"));
    }

    /**
     * Method under test: {@link CategoryController#getCategories(Long)}
     */
    @Test
    void testGetCategories() throws Exception {
        // Arrange
        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        CategoryManageDto categoryManageDto = new CategoryManageDto();
        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setCategoryDetailsList(new ArrayList<>());
        categoryManageDto.setCategoryStatus("Category Status");
        categoryManageDto.setResponseCode("Response Code");
        categoryManageDto.setResponseMessage("Response Message");
        categoryManageDto.setStatus("Status");
        categoryManageDto.setUserId(1L);
        when(categoryService.getCategories(Mockito.<CategoryManageDto>any())).thenReturn(categoryManageDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/categories");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("userId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"categoryDetailsList"
                                        + "\":[]}"));
    }

    /**
     * Method under test: {@link CategoryController#getCategories(Long, String)}
     */
    @Test
    void testGetCategories2() throws Exception {
        // Arrange
        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        CategoryManageDto categoryManageDto = new CategoryManageDto();
        categoryManageDto.setCategoryDetail(categoryDetail);
        categoryManageDto.setCategoryDetailsList(new ArrayList<>());
        categoryManageDto.setCategoryStatus("Category Status");
        categoryManageDto.setResponseCode("Response Code");
        categoryManageDto.setResponseMessage("Response Message");
        categoryManageDto.setStatus("Status");
        categoryManageDto.setUserId(1L);
        when(categoryService.getCategoriesByStatus(Mockito.<CategoryManageDto>any())).thenReturn(categoryManageDto);
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/api/categories/status")
                .param("status", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("userId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"categoryDetailsList"
                                        + "\":[]}"));
    }
}
