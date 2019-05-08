package com.wwg.controller;

import com.wwg.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

  @GetMapping("/user/{id}")
  public UserInfo findById(@PathVariable int id) {
    return this.restTemplate.getForObject("http://localhost:8100/test/", UserInfo.class);
  }

  @GetMapping("/userInstance")
  public List<ServiceInstance> userInstance() {
    return this.discoveryClient.getInstances("new-user");
  }

  @GetMapping("/test/{id}")
  public UserInfo test(@PathVariable int id) {
    UserInfo userInfo=new UserInfo();
    userInfo.setId(1);
    userInfo.setName("test");
    return userInfo;
  }
}
