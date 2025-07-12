package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.BookManageDto;

public interface BookService {
    public BookManageDto addBook(BookManageDto bookManageDto);
    public BookManageDto updateBook(BookManageDto bookManageDto);
    public BookManageDto getBookById(BookManageDto bookManageDto);
    public BookManageDto getBooks(BookManageDto bookManageDto);
    public BookManageDto searchBooks(BookManageDto bookManageDto);
}
