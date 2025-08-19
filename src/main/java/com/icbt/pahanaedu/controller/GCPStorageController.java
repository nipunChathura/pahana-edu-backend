package com.icbt.pahanaedu.controller;

import com.icbt.pahanaedu.dto.GcpDto;
import com.icbt.pahanaedu.response.GcpResponse;
import com.icbt.pahanaedu.service.GcpStorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/gcp")
@CrossOrigin(origins = "http://localhost:4200")
public class GCPStorageController {

    @Autowired
    private GcpStorageService gcpStorageService;

    @PostMapping("/upload")
    public GcpResponse uploadImage(@RequestParam("file") MultipartFile file) {
        GcpDto gcpDto = new GcpDto();
        gcpDto.setFile(file);
        GcpDto result = gcpStorageService.uploadFile(gcpDto);

        GcpResponse response = new GcpResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());
        response.setImageUrl(result.getImageUrl());

        return response;
    }

    @DeleteMapping("/delete")
    public GcpResponse deleteImage(@RequestParam("file") String file) {
        GcpDto gcpDto = new GcpDto();
        gcpDto.setFileName(file);

        GcpDto result = gcpStorageService.deleteFile(gcpDto);
        GcpResponse response = new GcpResponse();
        response.setStatus(result.getStatus());
        response.setResponseCode(result.getResponseCode());
        response.setResponseMessage(result.getResponseMessage());
        response.setImageUrl(result.getImageUrl());

        return response;
    }
}
