package com.w2g;

import com.w2g.filter.ZuulSelfFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * 构建微服务网关
 * 1单加一个@EnableZuulProxy，便可通过该类进行路由跳转
 * 2注入ZuulSelfFilter，请求在经过网关的时候，会进入相应的过滤器
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

	@Bean
	public ZuulSelfFilter zuulSelfFilter(){
		return new ZuulSelfFilter();
	}
}

