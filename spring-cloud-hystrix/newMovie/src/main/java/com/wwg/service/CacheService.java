package com.wwg.service;

import com.wwg.entity.UserInfo;

/**
 * Created by W2G on 2018/11/28.
 */
public interface CacheService {


    Integer getRandomInteger(int i);

    UserInfo findOne(Integer id);
}
