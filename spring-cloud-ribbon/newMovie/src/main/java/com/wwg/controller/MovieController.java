package com.wwg.controller;

import com.wwg.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
public class MovieController {
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private DiscoveryClient discoveryClient;
  @Autowired
  private LoadBalancerClient loadBalancerClient;


  /**
   * 通过loadBalancerClient来调用不同的服务提供者，实现请求的负载均衡
   * @return
   */
  @GetMapping("/showLog2")
  public void showLog1() {
    ServiceInstance instance = loadBalancerClient.choose("new-user");
    URI uri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
    System.out.println("host="+uri.getHost()+",port="+uri.getPort()+"\n");
  }

  /**
   * 通过loadBalancerClient来调用不同的服务提供者，实现请求的负载均衡
   * @return
   */
  @GetMapping("/showLog")
  public void showLog() {
    ServiceInstance instance = loadBalancerClient.choose("new-user");
    URI uri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
    System.out.println("host="+uri.getHost()+",port="+uri.getPort()+"\n");
  }


  @GetMapping("/user/{id}")
  public UserInfo findById(@PathVariable int id) {
    return this.restTemplate.getForObject("http://localhost:8100/test/", UserInfo.class);
  }

  @GetMapping("/userInstance")
  public List<ServiceInstance> userInstance() {
    return this.discoveryClient.getInstances("new-user");
  }
}
