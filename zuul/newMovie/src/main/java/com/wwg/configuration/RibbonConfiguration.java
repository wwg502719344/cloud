package com.wwg.configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Configuration;

/**
 * Created by W2G on 2019/1/24.
 * 创建ribbon基础配置类
 */
@Configuration
public class RibbonConfiguration {

    public IRule ribbonRule(){
        return new RandomRule();
    }

}
