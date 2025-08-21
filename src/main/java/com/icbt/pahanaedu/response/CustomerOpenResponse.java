package com.icbt.pahanaedu.response;

import com.icbt.pahanaedu.dto.*;
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

    private Long orderId;
    private Long customerId;
    private CustomerDto customerDto;
    private OrderDto orderDto;
    private List<OrderDto> orderList;
    private List<OrderDetailDto> detailDetailList;
}
