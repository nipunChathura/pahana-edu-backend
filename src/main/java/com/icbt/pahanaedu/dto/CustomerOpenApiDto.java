package com.icbt.pahanaedu.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerOpenApiDto extends CommonDto {
    private Long customerId;
    private String email;
    private String phoneNumber;
    private String categoryId;
    private boolean requestBookDetails;
    private String requestType;
    private Long requestId;

//    response
    private CategoryDto categoryDetail;
    private List<CategoryDto> categoryDetailsList;

    private PromotionDto promotionDto;
    private List<PromotionDto> promotionDtoList;

    private BookDetailsDto bookDetail;
    private List<BookDetailsDto> bookDetailsList;
}
