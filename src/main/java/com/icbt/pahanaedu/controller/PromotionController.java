package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.PromotionManageDto;
import com.icbt.pahanaedu.request.PromotionRequest;
import com.icbt.pahanaedu.response.PromotionResponse;
import com.icbt.pahanaedu.service.PromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/add")
    public PromotionResponse addPromotion(@RequestBody PromotionRequest request) {

        PromotionManageDto promotionManageDto = new PromotionManageDto();
        BeanUtils.copyProperties(request, promotionManageDto);

        PromotionManageDto result = promotionService.addPromotion(promotionManageDto);
        PromotionResponse response = new PromotionResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @PutMapping("/update")
    public PromotionResponse updatePromotion(@RequestBody PromotionRequest request) {

        PromotionManageDto promotionManageDto = new PromotionManageDto();
        BeanUtils.copyProperties(request, promotionManageDto);

        PromotionManageDto result = promotionService.updatePromotion(promotionManageDto);
        PromotionResponse response = new PromotionResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/id/{userId}")
    public PromotionResponse getByPromotionId(@PathVariable Long userId, @RequestParam Long promotionId,
                                             @RequestParam boolean requestBookDetails) {

        PromotionManageDto promotionManageDto = new PromotionManageDto();
        promotionManageDto.setRequestBookDetails(requestBookDetails);
        promotionManageDto.setUserId(userId);
        promotionManageDto.setPromotionId(promotionId);

        PromotionManageDto result = promotionService.getByPromotionId(promotionManageDto);
        PromotionResponse response = new PromotionResponse();
        response.setPromotionDto(result.getPromotionDto());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @GetMapping("/all/{userId}")
    public PromotionResponse getAllPromotion(@PathVariable Long userId,
                                             @RequestParam boolean requestBookDetails) {

        PromotionManageDto promotionManageDto = new PromotionManageDto();
        promotionManageDto.setRequestBookDetails(requestBookDetails);
        promotionManageDto.setUserId(userId);

        PromotionManageDto result = promotionService.getAllPromotion(promotionManageDto);
        PromotionResponse response = new PromotionResponse();
        response.setPromotionDtoList(result.getPromotionDtoList());
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @PatchMapping("/add/book/{userId}")
    public PromotionResponse addBookForPromotion(@PathVariable Long userId, @RequestParam Long promotionId,
                                             @RequestParam Long bookId) {

        PromotionManageDto promotionManageDto = new PromotionManageDto();
        promotionManageDto.setPromotionId(promotionId);
        promotionManageDto.setBookId(bookId);
        promotionManageDto.setUserId(userId);

        PromotionManageDto result = promotionService.addBookByExistingPromotion(promotionManageDto);
        PromotionResponse response = new PromotionResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }

    @PatchMapping("/remove/book/{userId}")
    public PromotionResponse removeBookForPromotion(@PathVariable Long userId, @RequestParam Long promotionId,
                                                 @RequestParam Long bookId) {

        PromotionManageDto promotionManageDto = new PromotionManageDto();
        promotionManageDto.setPromotionId(promotionId);
        promotionManageDto.setBookId(bookId);
        promotionManageDto.setUserId(userId);

        PromotionManageDto result = promotionService.removeBookByExistingPromotion(promotionManageDto);
        PromotionResponse response = new PromotionResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());

        return response;
    }
}
