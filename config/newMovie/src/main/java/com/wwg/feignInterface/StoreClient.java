package com.wwg.feignInterface;

import com.wwg.config.HystrixClientFallback;
import com.wwg.configuration.FooConfiguration;
import com.wwg.entity.UserInfo;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by W2G on 2018/12/25.
 *
 * @FeignClient 注解的fallback可以实现Hystrix的降级功能
 * Q:feignClient通过fallback实现的降级，和通过HystrixCommon包裹下的降级有什么区别
 * Q:@FeignClient如何实现@HystrixCommand中自定义属性配置
 */
@FeignClient(name = "new-user",configuration = FooConfiguration.class,fallback = HystrixClientFallback.class)
public interface StoreClient {

    /**
     * 实现feign的回退机制
     * @param id
     * @return
     */
    @RequestLine("GET /feignsFallBack/{id}")
    public UserInfo feignsFallBack(@Param("id") int id);

    /**
     * @RequestLine:指向
     * @param id
     * @return
     */
    @RequestLine("GET /findById/{id}")
    public UserInfo findById(@Param("id") int id);


}
