package com.icbt.pahanaedu.request;

import com.icbt.pahanaedu.dto.PromotionDto;
import lombok.Data;

import java.util.List;

@Data
public class PromotionRequest extends Request {
    private Long userId;
    private Long promotionId;
    private Long bookId;
    private boolean requestBookDetails;
    private PromotionDto promotionDto;
}
