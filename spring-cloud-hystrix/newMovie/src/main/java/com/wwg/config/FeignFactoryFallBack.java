package com.wwg.config;

import com.wwg.entity.UserInfo;
import com.wwg.feignInterface.StoreClient;
import feign.Param;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Created by W2G on 2018/12/26.
 * 该类可以实现接口失败回退原因，打印回退日志
 */
@Component
public class FeignFactoryFallBack implements FallbackFactory<StoreClient>{


    @Override
    public StoreClient create(Throwable throwable) {
        return new HystrixClientFallback() {
            @Override
            public UserInfo findById(@Param("id") int id) {
                return new UserInfo();
            }
        };
    }
}
