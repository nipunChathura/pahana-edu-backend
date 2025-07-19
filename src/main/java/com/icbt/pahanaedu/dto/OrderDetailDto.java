package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailDto {
    private Long orderDetailId;
    private Long orderId;
    private Long bookId;
    private BookDetailsDto book;
    private BigDecimal itemPrice;
    private Integer itemQuantity;
    private BigDecimal discountPrice;
    private Long promotionId;
    private PromotionDto promotion;
}
