package com.wwg.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class UserController {


    @Value("${dev.profile}")
    public String profile;

    @RequestMapping("/profile")
    public String feignsFallBack(
    ) {
        return this.profile;
    }



}
