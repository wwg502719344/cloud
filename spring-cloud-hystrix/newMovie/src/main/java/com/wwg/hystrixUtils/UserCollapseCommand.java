package com.wwg.hystrixUtils;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.wwg.entity.UserInfo;
import com.wwg.service.CacheServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by W2G on 2019/5/1.
 * 请求合并类(合并处理器)
 * 在一定的时间内(默认10ms)，将依赖于同一个服务的请求进行合并整合并以批量方式进行请求
 * 服务端也需要提供批量访问的程序
 */
public class UserCollapseCommand extends HystrixCollapser<List<UserInfo>,UserInfo,Integer> {

    private CacheServiceImpl service;
    private Integer userId;

    /**
     * 构造方法，主要是用来设置合并器的时间，多长时间合并一次请求
     * @param cacheService 调用的服务
     * @param userId 单次需要传递的业务id
     */
    public UserCollapseCommand(CacheServiceImpl cacheService, Integer userId){
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapseCommand")).
                andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.service = cacheService;
        this.userId = userId;
    }



    @Override
    public Integer getRequestArgument() {
        return userId;
    }

    /**
     * 获取传入的业务参数集合，调用执行方法执行批量请求
     * @param collapsedRequests
     * @return
     */
    @Override
    protected HystrixCommand<List<UserInfo>> createCommand(Collection<CollapsedRequest<UserInfo, Integer>> collapsedRequests) {
        System.out.println("HystrixCommand========>");
        //按请求数声名UserId的集合
        List<Integer> userIds = new ArrayList<>(collapsedRequests.size());
        //通过请求将100毫秒中的请求参数取出来装进集合中
        userIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        //返回UserBatchCommand对象，自动执行UserBatchCommand的run方法
        HystrixCommand<List<UserInfo>> a=new UserBatchCommand(service, userIds);
        return a;
    }

    /**
     * 返回请求的执行结果，并根据对应的request将结果返回到对应的request相应体中
     * 通过循环所有被合并的请求依次从批处理的结果集中获取对应的结果
     * @param userInfos 这里是批处理后返回的结果集
     * @param collection 所有被合并的请求
     */
    @Override
    protected void mapResponseToRequests(List<UserInfo> userInfos, Collection<CollapsedRequest<UserInfo, Integer>> collection) {
        int count = 0 ;
        for(CollapsedRequest<UserInfo,Integer> collapsedRequest : collection){
            //从批响应集合中按顺序取出结果
            UserInfo user = userInfos.get(count++);
            //将结果放回原Request的响应体内
            collapsedRequest.setResponse(user);
        }
    }
}
