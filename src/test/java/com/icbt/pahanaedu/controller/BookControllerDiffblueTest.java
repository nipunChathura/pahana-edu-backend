package com.icbt.pahanaedu.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.BookManageDto;
import com.icbt.pahanaedu.request.BookRequest;
import com.icbt.pahanaedu.service.BookService;

import java.math.BigDecimal;
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

@ContextConfiguration(classes = {BookController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BookControllerDiffblueTest {
    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    /**
     * Method under test: {@link BookController#addBook(BookRequest)}
     */
    @Test
    void testAddBook() throws Exception {
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

        BookManageDto bookManageDto = new BookManageDto();
        bookManageDto.setBookDetail(bookDetail);
        bookManageDto.setBookDetailsList(new ArrayList<>());
        bookManageDto.setResponseCode("Response Code");
        bookManageDto.setResponseMessage("Response Message");
        bookManageDto.setStatus("Status");
        bookManageDto.setUserId(1L);
        when(bookService.addBook(Mockito.<BookManageDto>any())).thenReturn(bookManageDto);

        BookDetailsDto bookDetail2 = new BookDetailsDto();
        bookDetail2.setAuthor("JaneDoe");
        bookDetail2.setAwardList(new ArrayList<>());
        bookDetail2.setBookId(1L);
        bookDetail2.setBookStatus("Book Status");
        bookDetail2.setCategoryId(1L);
        bookDetail2.setCategoryName("Category Name");
        bookDetail2.setDescription("The characteristics of someone or something");
        bookDetail2.setImageUrl("https://example.org/example");
        bookDetail2.setIsbn("Isbn");
        bookDetail2.setLanguage("en");
        bookDetail2.setName("Name");
        bookDetail2.setPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionBookPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionEnable(true);
        bookDetail2.setPromotionId(1L);
        bookDetail2.setPromotionPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionType("Promotion Type");
        bookDetail2.setPublishDate("2020-03-01");
        bookDetail2.setPublisher("Publisher");
        bookDetail2.setQuantity(1L);

        BookRequest bookRequest = new BookRequest();
        bookRequest.setBookDetail(bookDetail2);
        bookRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(bookRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"bookDetail\":"
                                        + "{\"bookId\":1,\"name\":\"Name\",\"categoryId\":1,\"categoryName\":\"Category Name\",\"description\":\"The characteristics"
                                        + " of someone or something\",\"language\":\"en\",\"author\":\"JaneDoe\",\"publisher\":\"Publisher\",\"publishDate\":"
                                        + "\"2020-03-01\",\"isbn\":\"Isbn\",\"price\":2.3,\"promotionId\":1,\"promotionType\":\"Promotion Type\",\"promotionPrice"
                                        + "\":2.3,\"promotionBookPrice\":2.3,\"quantity\":1,\"imageUrl\":\"https://example.org/example\",\"bookStatus\":\"Book"
                                        + " Status\",\"awardList\":[],\"promotionEnable\":true}}"));
    }

    /**
     * Method under test: {@link BookController#updateBook(BookRequest)}
     */
    @Test
    void testUpdateBook() throws Exception {
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

        BookManageDto bookManageDto = new BookManageDto();
        bookManageDto.setBookDetail(bookDetail);
        bookManageDto.setBookDetailsList(new ArrayList<>());
        bookManageDto.setResponseCode("Response Code");
        bookManageDto.setResponseMessage("Response Message");
        bookManageDto.setStatus("Status");
        bookManageDto.setUserId(1L);
        when(bookService.updateBook(Mockito.<BookManageDto>any())).thenReturn(bookManageDto);

        BookDetailsDto bookDetail2 = new BookDetailsDto();
        bookDetail2.setAuthor("JaneDoe");
        bookDetail2.setAwardList(new ArrayList<>());
        bookDetail2.setBookId(1L);
        bookDetail2.setBookStatus("Book Status");
        bookDetail2.setCategoryId(1L);
        bookDetail2.setCategoryName("Category Name");
        bookDetail2.setDescription("The characteristics of someone or something");
        bookDetail2.setImageUrl("https://example.org/example");
        bookDetail2.setIsbn("Isbn");
        bookDetail2.setLanguage("en");
        bookDetail2.setName("Name");
        bookDetail2.setPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionBookPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionEnable(true);
        bookDetail2.setPromotionId(1L);
        bookDetail2.setPromotionPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionType("Promotion Type");
        bookDetail2.setPublishDate("2020-03-01");
        bookDetail2.setPublisher("Publisher");
        bookDetail2.setQuantity(1L);

        BookRequest bookRequest = new BookRequest();
        bookRequest.setBookDetail(bookDetail2);
        bookRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(bookRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/books/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"bookDetail\":"
                                        + "{\"bookId\":1,\"name\":\"Name\",\"categoryId\":1,\"categoryName\":\"Category Name\",\"description\":\"The characteristics"
                                        + " of someone or something\",\"language\":\"en\",\"author\":\"JaneDoe\",\"publisher\":\"Publisher\",\"publishDate\":"
                                        + "\"2020-03-01\",\"isbn\":\"Isbn\",\"price\":2.3,\"promotionId\":1,\"promotionType\":\"Promotion Type\",\"promotionPrice"
                                        + "\":2.3,\"promotionBookPrice\":2.3,\"quantity\":1,\"imageUrl\":\"https://example.org/example\",\"bookStatus\":\"Book"
                                        + " Status\",\"awardList\":[],\"promotionEnable\":true}}"));
    }

    /**
     * Method under test: {@link BookController#getBookById(Long, Long)}
     */
    @Test
    void testGetBookById() throws Exception {
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

        BookManageDto bookManageDto = new BookManageDto();
        bookManageDto.setBookDetail(bookDetail);
        bookManageDto.setBookDetailsList(new ArrayList<>());
        bookManageDto.setResponseCode("Response Code");
        bookManageDto.setResponseMessage("Response Message");
        bookManageDto.setStatus("Status");
        bookManageDto.setUserId(1L);
        when(bookService.getBookById(Mockito.<BookManageDto>any())).thenReturn(bookManageDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/books/get");
        MockHttpServletRequestBuilder paramResult = getResult.param("bookId", String.valueOf(1L));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("userId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"bookDetail\":"
                                        + "{\"bookId\":1,\"name\":\"Name\",\"categoryId\":1,\"categoryName\":\"Category Name\",\"description\":\"The characteristics"
                                        + " of someone or something\",\"language\":\"en\",\"author\":\"JaneDoe\",\"publisher\":\"Publisher\",\"publishDate\":"
                                        + "\"2020-03-01\",\"isbn\":\"Isbn\",\"price\":2.3,\"promotionId\":1,\"promotionType\":\"Promotion Type\",\"promotionPrice"
                                        + "\":2.3,\"promotionBookPrice\":2.3,\"quantity\":1,\"imageUrl\":\"https://example.org/example\",\"bookStatus\":\"Book"
                                        + " Status\",\"awardList\":[],\"promotionEnable\":true}}"));
    }

    /**
     * Method under test: {@link BookController#deleteBook(BookRequest)}
     */
    @Test
    void testDeleteBook() throws Exception {
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

        BookManageDto bookManageDto = new BookManageDto();
        bookManageDto.setBookDetail(bookDetail);
        bookManageDto.setBookDetailsList(new ArrayList<>());
        bookManageDto.setResponseCode("Response Code");
        bookManageDto.setResponseMessage("Response Message");
        bookManageDto.setStatus("Status");
        bookManageDto.setUserId(1L);
        when(bookService.deleteBooks(Mockito.<BookManageDto>any())).thenReturn(bookManageDto);

        BookDetailsDto bookDetail2 = new BookDetailsDto();
        bookDetail2.setAuthor("JaneDoe");
        bookDetail2.setAwardList(new ArrayList<>());
        bookDetail2.setBookId(1L);
        bookDetail2.setBookStatus("Book Status");
        bookDetail2.setCategoryId(1L);
        bookDetail2.setCategoryName("Category Name");
        bookDetail2.setDescription("The characteristics of someone or something");
        bookDetail2.setImageUrl("https://example.org/example");
        bookDetail2.setIsbn("Isbn");
        bookDetail2.setLanguage("en");
        bookDetail2.setName("Name");
        bookDetail2.setPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionBookPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionEnable(true);
        bookDetail2.setPromotionId(1L);
        bookDetail2.setPromotionPrice(new BigDecimal("2.3"));
        bookDetail2.setPromotionType("Promotion Type");
        bookDetail2.setPublishDate("2020-03-01");
        bookDetail2.setPublisher("Publisher");
        bookDetail2.setQuantity(1L);

        BookRequest bookRequest = new BookRequest();
        bookRequest.setBookDetail(bookDetail2);
        bookRequest.setUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(bookRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/books/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\"}"));
    }

    /**
     * Method under test: {@link BookController#getBookAll(Long)}
     */
    @Test
    void testGetBookAll() throws Exception {
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

        BookManageDto bookManageDto = new BookManageDto();
        bookManageDto.setBookDetail(bookDetail);
        bookManageDto.setBookDetailsList(new ArrayList<>());
        bookManageDto.setResponseCode("Response Code");
        bookManageDto.setResponseMessage("Response Message");
        bookManageDto.setStatus("Status");
        bookManageDto.setUserId(1L);
        when(bookService.getBooks(Mockito.<BookManageDto>any())).thenReturn(bookManageDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/books/{userId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bookController)
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
