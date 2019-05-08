package com.wwg.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * Created by W2G on 2019/1/22.
 * 使用@RibbonClient，为特定name自定义负载均衡配置
 * 指定特定name的负载策略为RibbonConfiguration
 */
@Configuration
@RibbonClient(name = "new-user",configuration = RibbonConfiguration.class)
public class TestConfiguration {
}
