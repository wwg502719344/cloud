package com.w2g.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by W2G on 2019/1/7.
 * 自定义zuul网关过滤器，需要实现ZuulFilter
 * Q5,Q6
 */
public class ZuulSelfFilter  extends ZuulFilter{

    private static Logger log = LoggerFactory.getLogger(ZuulSelfFilter.class);

    /**
     * 该方法返回过滤器类型，有四种基本类型，对应接受请求前中后和错误拦截
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }


    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx=RequestContext.getCurrentContext();
        HttpServletRequest request=ctx.getRequest();

        if (request.getParameter("new-movie") != null) {
            // put the serviceId in `RequestContext`
            ZuulSelfFilter.log.info(String.format("方法是 %s,路径是 %s",request.getMethod(),request.getRequestURL().toString()));
        }else{
            ZuulSelfFilter.log.info(String.format("路径是 %s,方法是 %s",request.getRequestURL().toString(),request.getMethod()));
        }

        return null;
    }
}
