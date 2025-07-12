package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDetailsDto {
    private Long bookId;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String description;
    private String language;
    private String author;
    private String publisher;
    private String publishDate;
    private String isbn;
    private BigDecimal price;
    private boolean isPromotionEnable;
    private String promotionType;
    private BigDecimal promotionPrice;
    private Long quantity;
    private String imageUrl;
    private String bookStatus;
    private List<AwardDto> awardList;
}
