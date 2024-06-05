package com.software_system_development.arona_mysterious_shop_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.software_system_development.arona_mysterious_shop_backend.mapper")
@EnableScheduling
public class AronaMysteriousShopBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AronaMysteriousShopBackendApplication.class, args);
	}

}
