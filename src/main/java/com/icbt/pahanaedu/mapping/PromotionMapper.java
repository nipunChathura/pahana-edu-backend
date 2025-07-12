package com.icbt.pahanaedu.mapping;

import com.icbt.pahanaedu.dto.PromotionDto;
import com.icbt.pahanaedu.entity.Promotion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion toEntity(PromotionDto promotionDto);
    PromotionDto toDto(Promotion promotion);
}
