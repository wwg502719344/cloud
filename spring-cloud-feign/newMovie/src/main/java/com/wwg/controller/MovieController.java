package com.wwg.controller;

import com.wwg.entity.UserInfo;
import com.wwg.utils.StoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MovieController {
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private DiscoveryClient discoveryClient;
  @Autowired
  private StoreClient userClient;

  @GetMapping("/user/{id}")
  public UserInfo findById(@PathVariable int id) {
    return userClient.findById(id);
  }

  @PostMapping("/post")
  public String post(@RequestBody UserInfo userInfo) {
    return userClient.post(userInfo);
  }

  @GetMapping("/userInstance")
  public List<ServiceInstance> userInstance() {
    return this.discoveryClient.getInstances("new-user");
  }
}
