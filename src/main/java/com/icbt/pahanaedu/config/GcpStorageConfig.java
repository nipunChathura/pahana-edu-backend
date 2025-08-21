package com.icbt.pahanaedu.config;

import com.google.cloud.storage.Storage;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class GcpStorageConfig {
    // TODO: 8/21/2025 Added GCP Json file  
//    @Bean
//    public Storage storage() throws Exception {
//
//        InputStream serviceAccountStream = getClass().getClassLoader()
//                .getResourceAsStream("*");
//
//        if (serviceAccountStream == null) {
//            throw new RuntimeException("Service account JSON file not found in resources folder");
//        }
//
//        return StorageOptions.newBuilder()
//                .setCredentials(ServiceAccountCredentials.fromStream(serviceAccountStream))
//                .build()
//                .getService();
//    }
}
