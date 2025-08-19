package com.icbt.pahanaedu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;

//@Configuration
public class WebConfig  {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**")
//                        .allowedOrigins("http://localhost:4200")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
////
////                registry.addMapping("/open/api/customer/**")
////                        .allowedOrigins("http://localhost:4200")
////                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
////                        .allowedHeaders("*")
////                        .allowCredentials(true);
//            }
//        };
//    }
}