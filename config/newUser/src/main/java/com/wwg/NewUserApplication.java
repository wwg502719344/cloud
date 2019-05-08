package com.wwg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.wwg.mapper")
public class NewUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewUserApplication.class, args);
	}

}

