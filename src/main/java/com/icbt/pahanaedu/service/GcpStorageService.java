package com.icbt.pahanaedu.service;

import com.icbt.pahanaedu.dto.GcpDto;
import org.springframework.web.multipart.MultipartFile;

public interface GcpStorageService {
    public GcpDto uploadFile(GcpDto gcpDto);
    public byte[] readFile(String fileName);
    public GcpDto deleteFile(GcpDto gcpDto);
}
