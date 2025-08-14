package com.icbt.pahanaedu.config;

import com.google.cloud.storage.Storage;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class GcpStorageConfig {
    @Bean
    public Storage storage() throws Exception {
        InputStream serviceAccountStream = getClass().getClassLoader()
                .getResourceAsStream("pahana-edu-468718-6de200ad4abe.json");

        if (serviceAccountStream == null) {
            throw new RuntimeException("Service account JSON file not found in resources folder");
        }

        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(serviceAccountStream))
                .build()
                .getService();
    }
}
