package com.wwg.config;

import com.wwg.entity.UserInfo;
import com.wwg.feignInterface.StoreClient;
import feign.Param;
import org.springframework.stereotype.Component;

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
    public UserInfo feignsFallBack(int id) {
        UserInfo userInfo=new UserInfo();
        userInfo.setName("feignsFallBack");
        userInfo.setId(2);
        return userInfo;
    }
}
