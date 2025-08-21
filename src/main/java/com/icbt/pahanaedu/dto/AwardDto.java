package com.icbt.pahanaedu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwardDto {
    private Long awardId;
    private Long bookId;
    private String awardName;
    private String awardDescription;
}
