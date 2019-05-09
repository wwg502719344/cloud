package com.wwg.hystrixUtils;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.wwg.entity.UserInfo;
import com.wwg.service.CacheServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

/**
 * Created by W2G on 2019/5/1.
 * 请求批处理类
 * 在该类中将请求的方法替换成统一处理的方法
 */
public class UserBatchCommand extends HystrixCommand<List<UserInfo>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBatchCommand.class);

    private CacheServiceImpl service;

    private List<Integer> ids;

    public UserBatchCommand(CacheServiceImpl cacheService, List<Integer> ids){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")).
                andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
        this.service =cacheService ;
        this.ids = ids;
    }

    /**
     * 调用批量处理的方法
     * @return
     */
    @Override
    protected List<UserInfo> run() {
        List<UserInfo> users = service.findAll(ids);
        return users;
    }

    /**
     * Fallback回调方法，如果没有会报错
     */
    @Override
    protected List<UserInfo> getFallback(){
        System.out.println("UserBatchCommand的run方法，调用失败");
        return null;
    }
}
