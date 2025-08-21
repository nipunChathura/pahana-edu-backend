package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.BookManageDto;
import com.icbt.pahanaedu.request.BookRequest;
import com.icbt.pahanaedu.response.BookResponse;
import com.icbt.pahanaedu.service.BookService;
import com.icbt.pahanaedu.service.impl.GcpStorageServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public BookResponse addBook(@RequestBody BookRequest request) {

        BookManageDto bookManageDto = new BookManageDto();
        BeanUtils.copyProperties(request, bookManageDto);

        BookManageDto result = bookService.addBook(bookManageDto);
        BookResponse response = new BookResponse();
        response.setBookDetail(result.getBookDetail());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @PutMapping("/update")
    public BookResponse updateBook(@RequestBody BookRequest request) {
        BookManageDto bookManageDto = new BookManageDto();
        BeanUtils.copyProperties(request, bookManageDto);

        BookManageDto result = bookService.updateBook(bookManageDto);
        BookResponse response = new BookResponse();
        response.setBookDetail(result.getBookDetail());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/get")
    public BookResponse getBookById(@RequestParam Long bookId, @RequestParam Long userId) {
        BookManageDto bookManageDto = new BookManageDto();
        bookManageDto.setUserId(userId);
        BookDetailsDto detailsDto = new BookDetailsDto();
        detailsDto.setBookId(bookId);
        bookManageDto.setBookDetail(detailsDto);

        BookManageDto result = bookService.getBookById(bookManageDto);
        BookResponse response = new BookResponse();
        response.setBookDetail(result.getBookDetail());
        System.out.println("response.getBookDetail() = " + response.getBookDetail());
        System.out.println("response.getBookDetail().getAwardList() = " + response.getBookDetail().getAwardList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/{userId}")
    public BookResponse getBookAll(@PathVariable  Long userId) {
        BookManageDto bookManageDto = new BookManageDto();
        bookManageDto.setUserId(userId);

        BookManageDto result = bookService.getBooks(bookManageDto);
        BookResponse response = new BookResponse();
        response.setBookDetailsList(result.getBookDetailsList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @DeleteMapping("/delete")
    public BookResponse deleteBook(@RequestBody  BookRequest request) {
        BookManageDto bookManageDto = new BookManageDto();
        BeanUtils.copyProperties(request, bookManageDto);

        BookManageDto result = bookService.deleteBooks(bookManageDto);
        BookResponse response = new BookResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }
}
