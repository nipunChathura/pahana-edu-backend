package com.icbt.pahanaedu.response;

import com.icbt.pahanaedu.dto.BookDetailsDto;
import com.icbt.pahanaedu.dto.CategoryDto;
import com.icbt.pahanaedu.dto.PromotionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerOpenResponse extends Response {
    private CategoryDto categoryDetail;
    private List<CategoryDto> categoryDetailsList;

    private PromotionDto promotionDto;
    private List<PromotionDto> promotionDtoList;

    private BookDetailsDto bookDetail;
    private List<BookDetailsDto> bookDetailsList;
}
