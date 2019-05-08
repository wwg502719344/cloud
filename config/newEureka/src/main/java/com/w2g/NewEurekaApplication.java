package com.w2g;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * 此处直接使用@EnableEurekaServer就可以让该应用变为Eureka服务器，
 * 因为springboot已经封装了Eureka Service
 */
@SpringBootApplication
@EnableEurekaServer
public class NewEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewEurekaApplication.class, args);
	}

}

