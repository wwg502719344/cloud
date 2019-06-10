package com.w2g.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by W2G on 2019/4/22.
 * 刷新路由服务（当DB路由有变更时，应调用refreshRoute方法）
 * 事件监听发布demo(可以做一个事件监听的功能)
 * https://www.jianshu.com/p/4359dd4b36a6
 */

@RestController
public class RefreshRouteService {

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    RouteLocator routeLocator;

    @GetMapping("/refreshRoute")
    public void refreshRoute() {
        //zuulconfigure默认配置了事件监听，这里我们发送一个事件去触发监听
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }
}

