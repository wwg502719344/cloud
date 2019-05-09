package com.wwg.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.wwg.entity.UserInfo;
import com.wwg.feignInterface.StoreClient;
import com.wwg.hystrixUtils.UserCollapseCommand;
import com.wwg.service.CacheService;
import com.wwg.service.CacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class MovieController {
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private DiscoveryClient discoveryClient;
  @Autowired
  private LoadBalancerClient loadBalancerClient;
  @Autowired
  private StoreClient storeClient;
  @Autowired
  private CacheServiceImpl cacheService;



///////////////////////////////请求合并测试-BEGIN//////////////////////////////////////////

  /**
   * 注解方式的请求合并
   *
   * 这里真想不出怎么去测试 这个方法了，有什么好的并发测试框架请自测吧，如果找到这种神器
   */
    @GetMapping("/collapsebyannotation/{id}")
  public UserInfo collapseByAnnotation(@PathVariable Integer id) throws ExecutionException, InterruptedException {
    Future<UserInfo> one = cacheService.findOneByAnnotation(id);
    UserInfo user = one.get();
    return user;
  }




  /**
   * 模拟合并请求测试(非注解)
   * 这里通过
   */
  @GetMapping("/collapse")
  public void collapseTest(){
    System.out.println("==========>collapseTest方法执行了");
    HystrixRequestContext context = HystrixRequestContext.initializeContext();
    try {
      Future<UserInfo> f1 = new UserCollapseCommand(cacheService, 1).queue();
      Future<UserInfo> f2 = new UserCollapseCommand(cacheService, 2).queue();
      Future<UserInfo> f3 = new UserCollapseCommand(cacheService, 3).queue();

      Thread.sleep(3000);

      Future<UserInfo> f4 = new UserCollapseCommand(cacheService, 5).queue();
      Future<UserInfo> f5 = new UserCollapseCommand(cacheService, 6).queue();

      UserInfo u1 = f1.get();
      UserInfo u2 = f2.get();
      UserInfo u3 = f3.get();

      UserInfo u4 = f4.get();
      UserInfo u5 = f5.get();
      System.out.println(u1.getName());
      System.out.println(u2.getName());
      System.out.println(u3.getName());
      System.out.println(u4.getName());
      System.out.println(u5.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      context.close();
    }

  }

  /**
   * 单个请求处理接口
   * 调用该接口，该接口通过请求合并调用下面的多请求处理接口
   * @param id
   */
  @GetMapping("/users/{id}")
  public UserInfo findOne(@PathVariable Integer id){
    System.out.println("=============/hystrix/users/{"+id+"} 执行了");
    UserInfo user = cacheService.findOne(id);
    return user;
  }

  /**
   * 多个请求处理接口
   * @param ids id串，使用逗号分隔
   */
  @GetMapping("/users")
  public List<UserInfo> findAll(@RequestParam String ids){
    System.out.println("=============/hystrix/users?ids={"+ids+"} 执行了");
    List<UserInfo> u2=storeClient.findAll("1,2,3");
    return u2;
  }


///////////////////////////////请求合并测试-END//////////////////////////////////////////




///////////////////////////////请求缓存测试-BEGIN//////////////////////////////////////////
  /**
   * 请求缓存测试
   * 请求方式处设置缓存相关设置
   */
  @GetMapping("/getCache1")
  public String getCache1() {
    HystrixRequestContext.initializeContext();//初始化请求上下文
    Integer a=cacheService.getRandomInteger(1);
    System.out.println("第一次获取缓存值为"+a);
    Integer a1=cacheService.getRandomInteger(1);
    System.out.println("第二次获取缓存值为"+a1);
    Integer a2=cacheService.getRandomInteger(1);
    System.out.println("第三次获取缓存值为"+a2);
    String show="第一次请求值:"+a+",第二次请求值为:"+a1+",第三次请求值为:"+a2;
    return show;
  }
  ///////////////////////////////请求缓存测试-END//////////////////////////////////////////


  /**
   * 新增post类型请求方式
   */
  @PostMapping("/feignsPostFallBack")
  public UserInfo feignsPostFallBack(@RequestBody UserInfo userInfo) {
    UserInfo u=storeClient.feignsPostFallBack(userInfo);
    return u;
  }

  /**
   * 为feign添加回退，为给定的StoreClient添加回退类
   * 使用该方式，无法将该接口纳入hystrix的监控当中
   */
  @GetMapping("/feignsFallBack/{id}")
  public UserInfo feignsFallBack(@PathVariable int id) {
    UserInfo u=storeClient.feignsFallBack(id);
    //System.out.println("host="+uri.getHost()+",port="+uri.getPort()+"\n");
    return u;
  }

  /**
   * 控制器方法，方法异常自动降级，hystrix只包裹单个方法
   * @param id
   * @return
   */
  @HystrixCommand(fallbackMethod = "findByIdsBack")
  @GetMapping("/userBack/{id}")
  public UserInfo findByIds(@PathVariable int id) {
    return storeClient.findById(id);
    //return this.restTemplate.getForObject("http://localhost:8100/test/", UserInfo.class);
  }

  public UserInfo findByIdsBack( int id) {
    UserInfo userInfo=new UserInfo();
    userInfo.setName("asd1");
    userInfo.setId(2);
    return userInfo;
  }

  /**
   * 普通方法查询提供者的api服务
   * @param id
   * @return
   */
  @GetMapping("/user/{id}")
  public UserInfo findById(@PathVariable int id) {
    return this.restTemplate.getForObject("http://localhost:8100/test/", UserInfo.class);
  }

  @GetMapping("/userInstance")
  public List<ServiceInstance> userInstance() {
    return this.discoveryClient.getInstances("new_user");
  }


  @GetMapping("testRibbon")
  public void test(){
    ServiceInstance instance =loadBalancerClient.choose("new-user");
    URI uri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
    System.out.println("host="+uri.getHost()+",port="+uri.getPort()+"\n");
  }

}
