package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.PromotionManageDto;
import com.icbt.pahanaedu.request.PromotionRequest;
import com.icbt.pahanaedu.response.PromotionResponse;
import com.icbt.pahanaedu.service.PromotionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PromotionControllerTest {
    @InjectMocks
    private PromotionController promotionController;

    @Mock
    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPromotion() {
        PromotionRequest request = new PromotionRequest();

        PromotionManageDto resultDto = new PromotionManageDto();
        resultDto.setStatus("SUCCESS");
        resultDto.setResponseCode("200");
        resultDto.setResponseMessage("Promotion added");

        when(promotionService.addPromotion(any(PromotionManageDto.class))).thenReturn(resultDto);

        PromotionResponse response = promotionController.addPromotion(request);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("Promotion added", response.getResponseMessage());
        verify(promotionService, times(1)).addPromotion(any(PromotionManageDto.class));
    }

    @Test
    void testUpdatePromotion() {
        PromotionRequest request = new PromotionRequest();

        PromotionManageDto resultDto = new PromotionManageDto();
        resultDto.setStatus("SUCCESS");
        resultDto.setResponseCode("200");
        resultDto.setResponseMessage("Promotion updated");

        when(promotionService.updatePromotion(any(PromotionManageDto.class))).thenReturn(resultDto);

        PromotionResponse response = promotionController.updatePromotion(request);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("Promotion updated", response.getResponseMessage());
        verify(promotionService, times(1)).updatePromotion(any(PromotionManageDto.class));
    }

    @Test
    void testGetByPromotionId() {
        Long userId = 1L;
        Long promotionId = 10L;
        boolean requestBookDetails = true;

        PromotionManageDto resultDto = new PromotionManageDto();
        resultDto.setStatus("SUCCESS");
        resultDto.setResponseCode("200");
        resultDto.setResponseMessage("Promotion fetched");

        when(promotionService.getByPromotionId(any(PromotionManageDto.class))).thenReturn(resultDto);

        PromotionResponse response = promotionController.getByPromotionId(userId, promotionId, requestBookDetails);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("Promotion fetched", response.getResponseMessage());
        verify(promotionService, times(1)).getByPromotionId(any(PromotionManageDto.class));
    }

    @Test
    void testDeletePromotion() {
        PromotionRequest request = new PromotionRequest();

        PromotionManageDto resultDto = new PromotionManageDto();
        resultDto.setStatus("SUCCESS");
        resultDto.setResponseCode("200");
        resultDto.setResponseMessage("Promotion deleted");

        when(promotionService.deletePromotion(any(PromotionManageDto.class))).thenReturn(resultDto);

        PromotionResponse response = promotionController.deletePromotion(request);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("Promotion deleted", response.getResponseMessage());
        verify(promotionService, times(1)).deletePromotion(any(PromotionManageDto.class));
    }

    @Test
    void testGetAllPromotion() {
        Long userId = 1L;
        boolean requestBookDetails = true;

        PromotionManageDto resultDto = new PromotionManageDto();
        resultDto.setStatus("SUCCESS");
        resultDto.setResponseCode("200");
        resultDto.setResponseMessage("All promotions fetched");
        resultDto.setPromotionDtoList(Collections.emptyList());

        when(promotionService.getAllPromotion(any(PromotionManageDto.class))).thenReturn(resultDto);

        PromotionResponse response = promotionController.getAllPromotion(userId, requestBookDetails);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("All promotions fetched", response.getResponseMessage());
        assertEquals(0, response.getPromotionDtoList().size());
        verify(promotionService, times(1)).getAllPromotion(any(PromotionManageDto.class));
    }

    @Test
    void testAddBookForPromotion() {
        Long userId = 1L;
        Long promotionId = 10L;
        Long bookId = 5L;

        PromotionManageDto resultDto = new PromotionManageDto();
        resultDto.setStatus("SUCCESS");
        resultDto.setResponseCode("200");
        resultDto.setResponseMessage("Book added to promotion");

        when(promotionService.addBookByExistingPromotion(any(PromotionManageDto.class))).thenReturn(resultDto);

        PromotionResponse response = promotionController.addBookForPromotion(userId, promotionId, bookId);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("Book added to promotion", response.getResponseMessage());
        verify(promotionService, times(1)).addBookByExistingPromotion(any(PromotionManageDto.class));
    }

    @Test
    void testRemoveBookForPromotion() {
        Long userId = 1L;
        Long promotionId = 10L;
        Long bookId = 5L;

        PromotionManageDto resultDto = new PromotionManageDto();
        resultDto.setStatus("SUCCESS");
        resultDto.setResponseCode("200");
        resultDto.setResponseMessage("Book removed from promotion");

        when(promotionService.removeBookByExistingPromotion(any(PromotionManageDto.class))).thenReturn(resultDto);

        PromotionResponse response = promotionController.removeBookForPromotion(userId, promotionId, bookId);

        assertEquals("SUCCESS", response.getStatus());
        assertEquals("200", response.getResponseCode());
        assertEquals("Book removed from promotion", response.getResponseMessage());
        verify(promotionService, times(1)).removeBookByExistingPromotion(any(PromotionManageDto.class));
    }
}
