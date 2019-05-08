package com.wwg.config;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Created by W2G on 2018/12/26.
 * 获取降级原因
 */
@Component
public class FeignFactoryFallBack implements FallbackFactory{


    @Override
    public Object create(Throwable throwable) {
        return null;
    }
}
