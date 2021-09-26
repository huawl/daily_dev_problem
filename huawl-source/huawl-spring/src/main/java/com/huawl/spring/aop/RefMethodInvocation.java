package com.huawl.spring.aop;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/21 12:06
 */
public class RefMethodInvocation implements MethodInvocation {

    public RefMethodInvocation(Object target, Object[] args, Method method) {
        this.target = target;
        this.args = args;
        this.method = method;
    }

    private Object target;

    private Object[] args;

    private Method method;

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object proceed() throws Throwable {
        System.out.println("====准备执行真实方法===");
        return method.invoke(target,args);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return null;
    }
}
