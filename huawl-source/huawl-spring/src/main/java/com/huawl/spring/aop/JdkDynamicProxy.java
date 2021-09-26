package com.huawl.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/21 11:10
 */
public class JdkDynamicProxy implements AopProxy {

    @Override
    public Object getProxy(AdviceSupport adviceSupport) {
        TargetSource targetSource = adviceSupport.getTargetSource();
        MethodInterceptor interceptor = adviceSupport.getInterceptor();
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), targetSource.getInterClazz(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("=====start=====");
                Object result = interceptor.invoke(new RefMethodInvocation(targetSource.getTarget(),args,method));
                System.out.println("=====end=====");
                return result;
            }
        });
    }
}
