package com.wwg.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * Created by W2G on 2019/1/24.
 * 选择RibbonConfiguration作为负载均衡规则
 * 也可以使用配置文件的方式进行负载均衡的设定
 */
@Configuration
@RibbonClient(name = "new-user",configuration = RibbonConfiguration.class)
public class RibbonUserConfiguration {
}
