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

    private Long orderId;

    private CustomerDto customerDto;
    private OrderDto orderDto;
    private List<OrderDto> orderList;
    private List<OrderDetailDto> detailDetailList;

//    response
    private CategoryDto categoryDetail;
    private List<CategoryDto> categoryDetailsList;

    private PromotionDto promotionDto;
    private List<PromotionDto> promotionDtoList;

    private BookDetailsDto bookDetail;
    private List<BookDetailsDto> bookDetailsList;
}
