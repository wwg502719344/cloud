package com.wwg.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Configuration;

/**
 * Created by W2G on 2019/1/22.
 * 该类为ribbon自定义负载均衡的配置类，该类可以选择负载策略
 */
@Configuration
public class RibbonConfiguration {

    public IRule ribbonRule(){
        return new RandomRule();
    }
}
