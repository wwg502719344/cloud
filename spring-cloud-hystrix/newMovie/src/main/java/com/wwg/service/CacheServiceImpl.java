package com.wwg.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.wwg.entity.UserInfo;
import com.wwg.feignInterface.StoreClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by W2G on 2018/11/28.
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StoreClient storeClient;


    //////////////////////////////////注解方式实现请求合并-BEGIN//////////////////////////////////////
    /**
     * 被合并请求的方法
     * 注意是timerDelayInMilliseconds，注意拼写
     */
    @HystrixCollapser(batchMethod = "findAllByAnnotation",collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds",value = "100")})
    public Future<UserInfo> findOneByAnnotation(Integer id){
        //你会发现根本不会进入这个方法体
        System.out.println("findOne方法执行了，ids= "+id);
        return null;
    }

    /**
     * 真正执行的方法
     */
    @HystrixCommand
    public List<UserInfo> findAllByAnnotation(List<Integer> ids){
        System.out.println("findAll方法执行了，ids= "+ids);
        UserInfo[] users = restTemplate.getForObject("http://localhost:8100/users?ids={1}", UserInfo[].class, StringUtils.join(ids, ","));
        return Arrays.asList(users);
    }
    //////////////////////////////////注解方式实现请求合并-END//////////////////////////////////////












    //////////////////////////////////非注解方式实现请求合并-BEGIN//////////////////////////////////////
    /**
     * 请求合并测试(单个请求)
     * @param id
     * @return
     */
    @Override
    public UserInfo findOne(Integer id) {
        return storeClient.findOne(id);
    }

    /**
     * 请求合并测试(合并请求)
     * @param ids
     * @return
     */
    public List<UserInfo> findAll(List<Integer> ids) {
        UserInfo[] users = restTemplate.getForObject("http://localhost:8100/users?ids={1}", UserInfo[].class, StringUtils.join(ids, ","));
        //System.out.println(StringUtils.join(ids, ","));
        List<UserInfo> u2= Arrays.asList(users);
        List<UserInfo> u1=storeClient.findAll(StringUtils.join(ids, ","));
        return u2;
    }

    //////////////////////////////////非注解方式实现请求合并-END//////////////////////////////////////


    /**
     * 请求缓存测试
     * @param id
     * @return
     */
    @Override
    @CacheResult
    @HystrixCommand(commandKey = "commandKey2")
    public Integer getRandomInteger(@CacheKey int id) {
        Integer a1=storeClient.getRandomInteger();
        return a1;
    }


}
