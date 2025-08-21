package com.icbt.pahanaedu.service.impl;

import com.google.cloud.storage.*;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.icbt.pahanaedu.common.ResponseCodes;
import com.icbt.pahanaedu.common.ResponseStatus;
import com.icbt.pahanaedu.dto.GcpDto;
import com.icbt.pahanaedu.service.GcpStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class GcpStorageServiceImpl implements GcpStorageService {

    @Autowired
    private Storage storage;

    @Value("${public.buckert.name}")
    private String bucketName;

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
        if (file == null || file.isEmpty()) {
            gcpDto.setStatus(ResponseStatus.FAILURE.getStatus());
            gcpDto.setResponseCode(ResponseCodes.FAILED_CODE);
            gcpDto.setResponseMessage("File is empty or not provided");
            return gcpDto;
        }

        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null) {
            int dotIndex = originalName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = originalName.substring(dotIndex);
            }
        }

        // Generate unique file name
        String fileName = UUID.randomUUID().toString() + extension;

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        Storage storage;
        try {
            // Load service account key from resources folder
            InputStream serviceAccount = getClass().getResourceAsStream("/pahana-edu-468718-a1cd1775c486.json");
            if (serviceAccount == null) {
                throw new FileNotFoundException("Service account file not found in resources");
            }

            storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(serviceAccount))
                    .build()
                    .getService();
        } catch (IOException e) {
            gcpDto.setStatus(ResponseStatus.FAILURE.getStatus());
            gcpDto.setResponseCode(ResponseCodes.FAILED_CODE);
            gcpDto.setResponseMessage("Error initializing GCP Storage: " + e.getMessage());
            return gcpDto;
        }

        try {
            // Upload file
            storage.create(blobInfo, file.getBytes());

            // Create public URL (works if bucket/object is public)
            String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + fileName;

            gcpDto.setStatus(ResponseStatus.SUCCESS.getStatus());
            gcpDto.setResponseCode(ResponseCodes.SUCCESS_CODE);
            gcpDto.setResponseMessage("Image uploaded successfully");
            gcpDto.setImageUrl(imageUrl);

        } catch (IOException e) {
            gcpDto.setStatus(ResponseStatus.FAILURE.getStatus());
            gcpDto.setResponseCode(ResponseCodes.FAILED_CODE);
            gcpDto.setResponseMessage("Image upload failed. Exception: " + e.getMessage());
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
