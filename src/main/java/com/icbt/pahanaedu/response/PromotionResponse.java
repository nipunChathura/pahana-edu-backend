package com.icbt.pahanaedu.response;

import com.icbt.pahanaedu.dto.PromotionDto;
import lombok.Data;

import java.util.List;

@Data
public class PromotionResponse extends Response {
    private PromotionDto promotionDto;
    private List<PromotionDto> promotionDtoList;
}
