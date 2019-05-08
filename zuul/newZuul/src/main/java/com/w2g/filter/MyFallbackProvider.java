package com.w2g.filter;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by W2G on 2019/1/7.
 * 实现FallbackProvider的实现类是为zuul提供的熔断回退类，当api不可用时，提供熔断降级处理
 * zuul网关内部默认集成了Hystrix、Ribbon
 *
 * 在F版中需实现FallbackProvider类，F版以前不是FallbackProvider
 * @component 实例化该类到spring容器当中
 */
@Component
public class MyFallbackProvider implements FallbackProvider {

    /**
     * 为某个微服务提供回退操作, * 表示适用于所有回退类，否则指定serviceId
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String s, Throwable throwable) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                //fallback时候的状态码
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "ok";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("该微服务已经扑街了亲".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
