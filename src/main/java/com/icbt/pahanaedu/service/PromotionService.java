package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.PromotionManageDto;

public interface PromotionService {
    PromotionManageDto addPromotion(PromotionManageDto promotionManageDto);
    PromotionManageDto updatePromotion(PromotionManageDto promotionManageDto);
    PromotionManageDto getByPromotionId(PromotionManageDto promotionManageDto);
    PromotionManageDto getAllPromotion(PromotionManageDto promotionManageDto);
    PromotionManageDto addBookByExistingPromotion(PromotionManageDto promotionManageDto);
    PromotionManageDto removeBookByExistingPromotion(PromotionManageDto promotionManageDto);
}
