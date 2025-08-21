package com.icbt.pahanaedu.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.CategoryDto;
import com.icbt.pahanaedu.dto.CustomerOpenApiDto;
import com.icbt.pahanaedu.dto.PromotionDto;
import com.icbt.pahanaedu.request.CustomerOpenRequest;
import com.icbt.pahanaedu.service.CustomerOpenApiService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

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

@ContextConfiguration(classes = {CustomerOpenApiController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomerOpenApiControllerDiffblueTest {
    @Autowired
    private CustomerOpenApiController customerOpenApiController;

    @MockBean
    private CustomerOpenApiService customerOpenApiService;

    /**
     * Method under test: {@link CustomerOpenApiController#getCategories()}
     */
    @Test
    void testGetCategories() throws Exception {
        // Arrange
        BookDetailsDto bookDetail = new BookDetailsDto();
        bookDetail.setAuthor("JaneDoe");
        bookDetail.setAwardList(new ArrayList<>());
        bookDetail.setBookId(1L);
        bookDetail.setBookStatus("Book Status");
        bookDetail.setCategoryId(1L);
        bookDetail.setCategoryName("Category Name");
        bookDetail.setDescription("The characteristics of someone or something");
        bookDetail.setImageUrl("https://example.org/example");
        bookDetail.setIsbn("Isbn");
        bookDetail.setLanguage("en");
        bookDetail.setName("Name");
        bookDetail.setPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionBookPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionEnable(true);
        bookDetail.setPromotionId(1L);
        bookDetail.setPromotionPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionType("Promotion Type");
        bookDetail.setPublishDate("2020-03-01");
        bookDetail.setPublisher("Publisher");
        bookDetail.setQuantity(1L);

        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        PromotionDto promotionDto = new PromotionDto();
        promotionDto.setBookIds(new ArrayList<>());
        promotionDto.setPriority(1);
        promotionDto
                .setPromotionEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        promotionDto.setPromotionId(1L);
        promotionDto.setPromotionName("Promotion Name");
        promotionDto.setPromotionPrice(new BigDecimal("2.3"));
        promotionDto
                .setPromotionStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        promotionDto.setPromotionStatus("Promotion Status");
        promotionDto.setPromotionType("Promotion Type");
        promotionDto.setPromotionUrl("https://example.org/example");

        CustomerOpenApiDto customerOpenApiDto = new CustomerOpenApiDto();
        customerOpenApiDto.setBookDetail(bookDetail);
        customerOpenApiDto.setBookDetailsList(new ArrayList<>());
        customerOpenApiDto.setCategoryDetail(categoryDetail);
        customerOpenApiDto.setCategoryDetailsList(new ArrayList<>());
        customerOpenApiDto.setCategoryId("42");
        customerOpenApiDto.setCustomerId(1L);
        customerOpenApiDto.setEmail("jane.doe@example.org");
        customerOpenApiDto.setPhoneNumber("6625550144");
        customerOpenApiDto.setPromotionDto(promotionDto);
        customerOpenApiDto.setRequestBookDetails(true);
        customerOpenApiDto.setRequestId(1L);
        customerOpenApiDto.setRequestType("Request Type");
        customerOpenApiDto.setResponseCode("Response Code");
        customerOpenApiDto.setResponseMessage("Response Message");
        customerOpenApiDto.setStatus("Status");
        when(customerOpenApiService.getCategories(Mockito.<CustomerOpenApiDto>any())).thenReturn(customerOpenApiDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/open/api/customer/categories");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerOpenApiController)
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
     * Method under test: {@link CustomerOpenApiController#getPromotions()}
     */
    @Test
    void testGetPromotions() throws Exception {
        // Arrange
        BookDetailsDto bookDetail = new BookDetailsDto();
        bookDetail.setAuthor("JaneDoe");
        bookDetail.setAwardList(new ArrayList<>());
        bookDetail.setBookId(1L);
        bookDetail.setBookStatus("Book Status");
        bookDetail.setCategoryId(1L);
        bookDetail.setCategoryName("Category Name");
        bookDetail.setDescription("The characteristics of someone or something");
        bookDetail.setImageUrl("https://example.org/example");
        bookDetail.setIsbn("Isbn");
        bookDetail.setLanguage("en");
        bookDetail.setName("Name");
        bookDetail.setPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionBookPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionEnable(true);
        bookDetail.setPromotionId(1L);
        bookDetail.setPromotionPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionType("Promotion Type");
        bookDetail.setPublishDate("2020-03-01");
        bookDetail.setPublisher("Publisher");
        bookDetail.setQuantity(1L);

        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        PromotionDto promotionDto = new PromotionDto();
        promotionDto.setBookIds(new ArrayList<>());
        promotionDto.setPriority(1);
        promotionDto
                .setPromotionEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        promotionDto.setPromotionId(1L);
        promotionDto.setPromotionName("Promotion Name");
        promotionDto.setPromotionPrice(new BigDecimal("2.3"));
        promotionDto
                .setPromotionStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        promotionDto.setPromotionStatus("Promotion Status");
        promotionDto.setPromotionType("Promotion Type");
        promotionDto.setPromotionUrl("https://example.org/example");

        CustomerOpenApiDto customerOpenApiDto = new CustomerOpenApiDto();
        customerOpenApiDto.setBookDetail(bookDetail);
        customerOpenApiDto.setBookDetailsList(new ArrayList<>());
        customerOpenApiDto.setCategoryDetail(categoryDetail);
        customerOpenApiDto.setCategoryDetailsList(new ArrayList<>());
        customerOpenApiDto.setCategoryId("42");
        customerOpenApiDto.setCustomerId(1L);
        customerOpenApiDto.setEmail("jane.doe@example.org");
        customerOpenApiDto.setPhoneNumber("6625550144");
        customerOpenApiDto.setPromotionDto(promotionDto);
        customerOpenApiDto.setRequestBookDetails(true);
        customerOpenApiDto.setRequestId(1L);
        customerOpenApiDto.setRequestType("Request Type");
        customerOpenApiDto.setResponseCode("Response Code");
        customerOpenApiDto.setResponseMessage("Response Message");
        customerOpenApiDto.setStatus("Status");
        when(customerOpenApiService.getPromotions(Mockito.<CustomerOpenApiDto>any())).thenReturn(customerOpenApiDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/open/api/customer/prmotions");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerOpenApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\"}"));
    }

    /**
     * Method under test:
     * {@link CustomerOpenApiController#getBooks(CustomerOpenRequest)}
     */
    @Test
    void testGetBooks() throws Exception {
        // Arrange
        BookDetailsDto bookDetail = new BookDetailsDto();
        bookDetail.setAuthor("JaneDoe");
        bookDetail.setAwardList(new ArrayList<>());
        bookDetail.setBookId(1L);
        bookDetail.setBookStatus("Book Status");
        bookDetail.setCategoryId(1L);
        bookDetail.setCategoryName("Category Name");
        bookDetail.setDescription("The characteristics of someone or something");
        bookDetail.setImageUrl("https://example.org/example");
        bookDetail.setIsbn("Isbn");
        bookDetail.setLanguage("en");
        bookDetail.setName("Name");
        bookDetail.setPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionBookPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionEnable(true);
        bookDetail.setPromotionId(1L);
        bookDetail.setPromotionPrice(new BigDecimal("2.3"));
        bookDetail.setPromotionType("Promotion Type");
        bookDetail.setPublishDate("2020-03-01");
        bookDetail.setPublisher("Publisher");
        bookDetail.setQuantity(1L);

        CategoryDto categoryDetail = new CategoryDto();
        categoryDetail.setCategoryIconUrl("https://example.org/example");
        categoryDetail.setCategoryId(1L);
        categoryDetail.setCategoryName("Category Name");
        categoryDetail.setCategoryStatus("Category Status");

        PromotionDto promotionDto = new PromotionDto();
        promotionDto.setBookIds(new ArrayList<>());
        promotionDto.setPriority(1);
        promotionDto
                .setPromotionEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        promotionDto.setPromotionId(1L);
        promotionDto.setPromotionName("Promotion Name");
        promotionDto.setPromotionPrice(new BigDecimal("2.3"));
        promotionDto
                .setPromotionStartDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        promotionDto.setPromotionStatus("Promotion Status");
        promotionDto.setPromotionType("Promotion Type");
        promotionDto.setPromotionUrl("https://example.org/example");

        CustomerOpenApiDto customerOpenApiDto = new CustomerOpenApiDto();
        customerOpenApiDto.setBookDetail(bookDetail);
        customerOpenApiDto.setBookDetailsList(new ArrayList<>());
        customerOpenApiDto.setCategoryDetail(categoryDetail);
        customerOpenApiDto.setCategoryDetailsList(new ArrayList<>());
        customerOpenApiDto.setCategoryId("42");
        customerOpenApiDto.setCustomerId(1L);
        customerOpenApiDto.setEmail("jane.doe@example.org");
        customerOpenApiDto.setPhoneNumber("6625550144");
        customerOpenApiDto.setPromotionDto(promotionDto);
        customerOpenApiDto.setRequestBookDetails(true);
        customerOpenApiDto.setRequestId(1L);
        customerOpenApiDto.setRequestType("Request Type");
        customerOpenApiDto.setResponseCode("Response Code");
        customerOpenApiDto.setResponseMessage("Response Message");
        customerOpenApiDto.setStatus("Status");
        when(customerOpenApiService.getBooks(Mockito.<CustomerOpenApiDto>any())).thenReturn(customerOpenApiDto);

        CustomerOpenRequest customerOpenRequest = new CustomerOpenRequest();
        customerOpenRequest.setCustomerId(1L);
        customerOpenRequest.setEmail("jane.doe@example.org");
        customerOpenRequest.setPhoneNumber("6625550144");
        customerOpenRequest.setRequestId(1L);
        customerOpenRequest.setRequestType("Request Type");
        customerOpenRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(customerOpenRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/open/api/customer/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(customerOpenApiController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"bookDetailsList"
                                        + "\":[]}"));
    }
}
