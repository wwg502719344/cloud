package com.wwg.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * Created by W2G on 2019/1/23.
 *
 */
@Configuration
@RibbonClient(value = "new-user",configuration = RibbonConfiguration.class)
public class RibbonTestConfiguration {
}
