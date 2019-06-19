package com.wwg.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wwg.entity.UserInfo;
import com.wwg.feignInterface.StoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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


  /**
   * 为feign添加回退，为给定的StoreClient添加回退类
   * 使用该方式，无法将该接口纳入hystrix的监控当中
   */
  @GetMapping("/feignsFallBack/{id}")
  public UserInfo feignsFallBack(@PathVariable int id) {
    System.out.println(id);
    return storeClient.feignsFallBack(id);
    //return this.restTemplate.getForObject("http://localhost:8100/test/", UserInfo.class);
  }

  /**
   * 控制器方法，方法异常自动降级，hystrix只包裹单个方法
   * @param id
   * @return
   */
  @HystrixCommand(fallbackMethod = "findByIdsBack")
  @GetMapping("/userBack/{id}")
  public UserInfo findByIds(@PathVariable int id) {
    UserInfo userInfo=new UserInfo();
    userInfo.setName("asd222222");
    userInfo.setId(2);
    //return storeClient.findById(id);
    return this.restTemplate.getForObject("http://localhost:8100/test/", UserInfo.class);
  }

  public UserInfo findByIdsBack( int id) {
    UserInfo userInfo=new UserInfo();
    userInfo.setName("asd222222");
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
    return this.discoveryClient.getInstances("new-user");
  }

}
