package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionManageDto extends CommonDto {
    private Long userId;
    private Long promotionId;
    private Long bookId;
    private boolean requestBookDetails;
    private PromotionDto promotionDto;
    private List<PromotionDto> promotionDtoList;
}
