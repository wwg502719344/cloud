package com.wwg.utils;

import com.wwg.controller.UserController;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * Created by W2G on 2018/12/21.
 * 自定义健康指示器,检查相关服务是否启动
 * 如果为true，当前服务的状态就设为UP，否则设为DOWN
 */
@Component
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if(UserController.isDB){
            return new Health.Builder(Status.UP).build();
        } else{
            return new Health.Builder(Status.DOWN).build();
        }
    }
}
