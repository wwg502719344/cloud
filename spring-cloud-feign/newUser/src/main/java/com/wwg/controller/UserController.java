package com.wwg.controller;

import com.wwg.emun.FilterType;
import com.wwg.entity.UserInfo;
import com.wwg.mapper.UserInfoMapper;

import com.wwg.markerMapper.MapperFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by W2G on 2018/7/10.
 *
 * springcloud-eureka集群-健康检测
 * https://blog.csdn.net/wgxaszc8/article/details/79064867
 */
@RestController
public class UserController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public static boolean isDB = true;

    @RequestMapping("/test")
    public UserInfo test() {

        //List<UserInfo> u=userInfoMapper.searchAll();
        MapperFilter mapperFilter=MapperFilter.custom(UserInfo.class)
                .addFilter("id", FilterType.EQ,1)
                .build();

        List<UserInfo> u=userInfoMapper.selectByCondition(mapperFilter.getCondition());

        UserInfo us=u.get(0);

        //ResponseData responseData= ResponseData.getInstance();
        //responseData.setEntity(us);
        //直接转换陈json
        //String usersJson = JSON.toJSONString(u);

        return us;
    }


    @PostMapping("/post")
    public String test2(@RequestBody UserInfo userInfo) {

        //List<UserInfo> u=userInfoMapper.searchAll();


        //ResponseData responseData= ResponseData.getInstance();
        //responseData.setEntity(us);
        //直接转换陈json
        //String usersJson = JSON.toJSONString(u);

        return userInfo.getName();
    }


    @GetMapping(value = "/setIsDB/{isDB}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void setIsDB(@PathVariable boolean isDB) {
        this.isDB = isDB;
    }

}
