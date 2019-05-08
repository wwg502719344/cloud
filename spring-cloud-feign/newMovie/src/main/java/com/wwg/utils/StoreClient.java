package com.wwg.utils;

import com.wwg.config.FooConfiguration;
import com.wwg.entity.UserInfo;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by W2G on 2018/12/24.
 * 使用FeignClient的configuration属性，指定自定义配置文件
 */
@FeignClient(name="new-user",configuration = FooConfiguration.class)
public interface StoreClient {

    @RequestLine("GET /test")
    public UserInfo findById(@PathVariable("id") int id);

    @RequestLine("POST /post")
    public String post(@RequestBody UserInfo userInfo);
}
