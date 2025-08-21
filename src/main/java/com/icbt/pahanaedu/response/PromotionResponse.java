package com.icbt.pahanaedu.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.icbt.pahanaedu.dto.PromotionDto;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionResponse extends Response {
    private PromotionDto promotionDto;
    private List<PromotionDto> promotionDtoList;
}
