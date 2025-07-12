package com.icbt.pahanaedu.response;

import com.icbt.pahanaedu.dto.BookDetailsDto;
import lombok.Data;

import java.util.List;

@Data
public class BookResponse extends Response {
    private BookDetailsDto bookDetail;
    private List<BookDetailsDto> bookDetailsList;
}
