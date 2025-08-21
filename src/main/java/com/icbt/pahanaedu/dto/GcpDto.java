package com.icbt.pahanaedu.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GcpDto extends CommonDto {
    private Long userId;
    private MultipartFile file;
    private String fileName;
    private boolean isDeleted;
    private String imageUrl;
}
