package com.icbt.pahanaedu.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icbt.pahanaedu.dto.BookDetailsDto;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse extends Response {
    private BookDetailsDto bookDetail;
    private List<BookDetailsDto> bookDetailsList;
}
