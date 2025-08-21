package com.icbt.pahanaedu.util.mapper;

import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookDetailsDto bookDetailsDto);
    BookDetailsDto toDto(Book book);
    List<BookDetailsDto> toDtoList(List<Book> books);
}
