package com.wwg.feignInterface;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.wwg.config.HystrixClientFallback;
import com.wwg.configuration.FooConfiguration;
import com.wwg.entity.UserInfo;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * Created by W2G on 2018/12/25.
 *
 * @FeignClient 注解的fallback可以实现Hystrix的降级功能
 * 此处需要注意，在注解@FeignClient中有两个属性，fallback和fallbackFactory
 * 前者是feign包裹hystrix下的回退类，后者可以查看回退的原因等详细信息
 * Q:feignClient通过fallback实现的降级，和通过HystrixCommond包裹下的降级有什么区别
 * A:feign内部集成了Hystrix的功能，每一个请求同样是被hystrix包裹的
 */
@FeignClient(name = "new-user",configuration = FooConfiguration.class,fallback = HystrixClientFallback.class,fallbackFactory=HystrixClientFallback.class)
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

    @RequestLine("POST /feignsPostFallBack")
    UserInfo feignsPostFallBack(UserInfo userInfo);

    //@CacheResult
    //@HystrixCommand(commandKey = "commandKey2")
    @RequestLine("GET /hystrix/cache")
    Integer getRandomInteger();

    //@CacheRemove(commandKey = "commandKey2")
    //@HystrixCommand
    @RequestLine("GET /cleanCache")
    void cleanCache();

    @RequestLine("GET /getUserById/{id}")
    UserInfo findOne(Integer id);

    @RequestLine("GET /users/{ids}")
    List<UserInfo> findAll(@Param("ids") String ids);
}
