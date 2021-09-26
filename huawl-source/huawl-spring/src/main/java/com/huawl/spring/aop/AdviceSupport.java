package com.huawl.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 管理哪个对象需要执行哪个拦截器
 * @author Luther
 * @version 1.0
 * @date 2021/7/21 10:58
 */
public class AdviceSupport {

    private TargetSource targetSource;

    private MethodInterceptor interceptor;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }
}
