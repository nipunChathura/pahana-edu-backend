package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookManageDto extends CommonDto {
    private Long userId;
    private BookDetailsDto bookDetail;
    private List<BookDetailsDto> bookDetailsList;
}
