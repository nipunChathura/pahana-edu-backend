package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionDto {
    private Long awardId;
    private Long bookId;
    private String promotionName;
    private Date promotionStartDate;
    private Date promotionEndDate;
    private String promotionType;
    private BigDecimal promotionPrice;
}
