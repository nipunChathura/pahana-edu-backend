package com.icbt.pahanaedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.icbt.pahanaedu.entity")
@EnableJpaRepositories(basePackages = "com.icbt.pahanaedu.repository")
public class PahanaeduApplication {
	public static void main(String[] args) {
		SpringApplication.run(PahanaeduApplication.class, args);
	}

}
