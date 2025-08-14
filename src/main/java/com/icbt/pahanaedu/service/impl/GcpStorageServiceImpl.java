package com.icbt.pahanaedu.service.impl;

import com.google.cloud.storage.*;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.GcpDto;
import com.icbt.pahanaedu.service.GcpStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class GcpStorageServiceImpl implements GcpStorageService {

    @Autowired
    private Storage storage;

    private final String bucketName = "pahana-edu-public-bucket";

    public void GcpStorageService() throws Exception {
        InputStream serviceAccountStream = getClass().getClassLoader()
                .getResourceAsStream("service-account-file.json");

        storage =  StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(serviceAccountStream))
                .build()
                .getService();
    }

    @Override
    public GcpDto uploadFile(GcpDto gcpDto) {
        MultipartFile file = gcpDto.getFile();
        String originalName = file.getOriginalFilename();
        String extension = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalName.substring(dotIndex);
        }

        String fileName = UUID.randomUUID().toString() + extension;

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        try {
            storage.create(blobInfo, file.getBytes());
            String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + fileName;
            gcpDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            gcpDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            gcpDto.setResponseMessage("Image uploading successfully");
            gcpDto.setImageUrl(imageUrl);

        } catch (IOException e) {
            gcpDto.setStatus(ResponseStatus.FAILURE.getStatus());
            gcpDto.setResponseCode(ResponseCodes.FAILED_CODE);
            gcpDto.setResponseMessage("Image uploading successfully" + "Exception : "+e.getMessage());
        }

        return gcpDto;
    }

    @Override
    public byte[] readFile(String fileName) {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        if (blob == null) {
            throw new RuntimeException("File not found: " + fileName);
        }
        return blob.getContent();
    }

    @Override
    public GcpDto deleteFile(GcpDto gcpDto) {
        String fileName = gcpDto.getFileName();
        try {
            storage.delete(BlobId.of(bucketName, fileName));
            gcpDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            gcpDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            gcpDto.setResponseMessage("Image deleting successfully");
            gcpDto.setDeleted(true);
        } catch (Exception e) {
            gcpDto.setStatus(ResponseStatus.FAILURE.getStatus());
            gcpDto.setResponseCode(ResponseCodes.FAILED_CODE);
            gcpDto.setResponseMessage("Image deleting unsuccessfully");
            gcpDto.setDeleted(false);
        }
        return gcpDto;
    }
}
