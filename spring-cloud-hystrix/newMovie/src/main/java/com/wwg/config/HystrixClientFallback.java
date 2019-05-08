package com.wwg.config;

import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.wwg.entity.UserInfo;
import com.wwg.feignInterface.StoreClient;
import feign.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by W2G on 2018/12/26.
 */
@Component
public class HystrixClientFallback implements StoreClient{


    @Override
    public UserInfo findById(@Param("id") int id) {
        UserInfo userInfo=new UserInfo();
        userInfo.setName("findById");
        userInfo.setId(2);
        return userInfo;
    }

    @Override
    public UserInfo feignsPostFallBack(UserInfo userInfo) {
        userInfo.setName("feignsPostFallBack");
        userInfo.setId(3);
        return userInfo;
    }

    @Override
    public Integer getRandomInteger() {
        System.out.println("缓存失败");
        return null;
    }

    @Override
    public void cleanCache() {
        System.out.println("缓存清除失败");
    }

    @Override
    public UserInfo findOne(Integer id) {
        return null;
    }

    @Override
    public List<UserInfo> findAll(String ids) {
        return null;
    }

    @Override
    public UserInfo feignsFallBack(int id) {
        UserInfo userInfo=new UserInfo();
        userInfo.setName("feignsFallBack");
        userInfo.setId(2);
        return userInfo;
    }
}
