package com.wwg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * EnableDiscoveryClient,将该服务设置为Eureka客户端，
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NewMovieApplication {

	/**
	 * @LoadBalanced 让客户端restTemplate拥有负载均衡的能力
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(NewMovieApplication.class, args);
	}

}

