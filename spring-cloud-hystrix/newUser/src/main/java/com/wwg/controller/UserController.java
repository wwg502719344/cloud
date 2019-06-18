package com.wwg.controller;

import com.wwg.emun.FilterType;
import com.wwg.entity.UserInfo;
import com.wwg.mapper.UserInfoMapper;

import com.wwg.markerMapper.MapperFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

///////////////////////////////请求合并测试//////////////////////////////////////////
    /**
     * 请求合并测试
     * 为hystrix请求合并提供的接口
     */
    @GetMapping("/getUserById/{id}")
    public UserInfo getUserById(
            @PathVariable Integer id
    ){
        System.out.println("=========getUserById方法：入参ids："+id);
        UserInfo u=new UserInfo();
        u.setId(id);
        u.setName("斯巴鲁");
        return u;
    }

    @GetMapping("/users")
    public List<UserInfo> getUsersByIds(@RequestParam("ids") String ids){
        System.out.println("ids===:" + ids);
        List<UserInfo> lists = new ArrayList<UserInfo>();
        lists.add(new UserInfo(1, "小明"));
        lists.add(new UserInfo(2, "小红"));
        lists.add(new UserInfo(3, "小张"));
        lists.add(new UserInfo(4, "小王"));
        lists.add(new UserInfo(5, "小李"));
        return lists;
    }

    @GetMapping("/users/{ids}")
    public List<UserInfo> getUsersByIds2(@PathVariable("ids") String ids){
        System.out.println("ids===:" + ids);
        List<UserInfo> lists = new ArrayList<UserInfo>();
        lists.add(new UserInfo(5, "小李"));
        lists.add(new UserInfo(1, "小明"));
        lists.add(new UserInfo(2, "小红"));
        lists.add(new UserInfo(3, "小张"));
        lists.add(new UserInfo(4, "小王"));

        return lists;
    }
///////////////////////////////请求合并测试//////////////////////////////////////////




    /**
     * hystrix请求缓存
     *
     * @param
     * @return
     */
    @RequestMapping("/hystrix/cache")
    public Integer getRandomInteger(){
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        return randomInt;
    }

    /**
     * hystrix请求缓存清除
     * @param
     * @return
     */
    @RequestMapping("/cleanCache")
    public void cleanCache(){
        System.out.println("清除缓存成功");
    }

    @RequestMapping("/feignsFallBack/{id}")
    public UserInfo feignsFallBack(
            @PathVariable int id
    ) {
        MapperFilter mapperFilter=MapperFilter.custom(UserInfo.class)
                .addFilter("id", FilterType.EQ,id)
                .build();
        List<UserInfo> u=userInfoMapper.selectByCondition(mapperFilter.getCondition());
        UserInfo us=u.get(0);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //测试基于feign的负载均衡
        System.out.println("当前端口号为==================="+request.getLocalPort()+"=======================");
        return us;
    }

    @RequestMapping("/findById/{id}")
    public UserInfo findById(
            @PathVariable int id
    ) {
        MapperFilter mapperFilter=MapperFilter.custom(UserInfo.class)
                .addFilter("id", FilterType.EQ,id)
                .build();
        List<UserInfo> u=userInfoMapper.selectByCondition(mapperFilter.getCondition());
        UserInfo us=u.get(0);
        System.out.println("服务启动");
        return us;
    }

    @RequestMapping("/feignsPostFallBack")
    public UserInfo feignsPostFallBack(
            @RequestBody UserInfo userInfo
    ) {
        MapperFilter mapperFilter=MapperFilter.custom(UserInfo.class)
                .addFilter("id", FilterType.EQ,userInfo.getId())
                .build();
        List<UserInfo> u=userInfoMapper.selectByCondition(mapperFilter.getCondition());
        UserInfo us=u.get(0);
        System.out.println("name="+us.getName());
        return us;
    }

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

    @GetMapping(value = "/setIsDB/{isDB}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void setIsDB(@PathVariable boolean isDB) {
        this.isDB = isDB;
    }

}
