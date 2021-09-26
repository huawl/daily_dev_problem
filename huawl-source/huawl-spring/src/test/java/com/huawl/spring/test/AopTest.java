package com.huawl.spring.test;

import com.huawl.spring.aop.AdviceSupport;
import com.huawl.spring.aop.AopProxy;
import com.huawl.spring.aop.JdkDynamicProxy;
import com.huawl.spring.aop.TargetSource;
import com.huawl.spring.interceptor.TimeInterceptor;
import com.huawl.spring.service.ISayService;
import com.huawl.spring.service.SayService;

import java.lang.reflect.Proxy;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/21 11:20
 */
public class AopTest {

    public static void main(String[] args) {
        ISayService sayService = new SayService();

        AdviceSupport adviceSupport = new AdviceSupport();

        TargetSource targetSource = new TargetSource(sayService,new Class[]{ISayService.class});
        adviceSupport.setTargetSource(targetSource);

        TimeInterceptor timeInterceptor = new TimeInterceptor();
        adviceSupport.setInterceptor(timeInterceptor);

        AopProxy proxy = new JdkDynamicProxy();
        ISayService sayServiceProxy = (ISayService) proxy.getProxy(adviceSupport);
        System.out.println(sayServiceProxy.sum(1,2));
    }
}
