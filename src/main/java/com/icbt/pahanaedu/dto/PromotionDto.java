package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionDto {
    private Long promotionId;
//    private Long bookId;
    private String promotionName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date promotionStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date promotionEndDate;
    private String promotionType;
    private BigDecimal promotionPrice;
    private Integer priority;
    private String promotionStatus;
    private String promotionUrl;
    private List<Long> bookIds;
    private List<BookDetailsDto> bookDetailsDtoList;
}
