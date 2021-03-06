package com.wwg.configuration;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by W2G on 2018/12/25.
 * 自定义feign契约，使用该契约可以feign自带的注解
 */
@Configuration
public class FooConfiguration {
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }
}
