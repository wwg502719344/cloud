package com.wwg.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by W2G on 2018/12/24.
 * 将契约改为feign原生的默认契约，这样就可以使用feign自带的注解了
 */
@Configuration
public class FooConfiguration {

    @Bean
    public Contract feignContract(){
        return new feign.Contract.Default();
    }

}
