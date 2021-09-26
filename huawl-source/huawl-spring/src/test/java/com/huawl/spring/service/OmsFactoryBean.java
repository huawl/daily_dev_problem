package com.huawl.spring.service;

import com.huawl.spring.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/29 16:59
 */
public class OmsFactoryBean implements FactoryBean {

    private Class clazz;

    public OmsFactoryBean(Class clazz){
        this.clazz = clazz;
    }

    @Override
    public Object getObject() {
        return Proxy.newProxyInstance(OmsFactoryBean.class.getClassLoader(), new Class[]{this.clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (Object.class.equals(method.getDeclaringClass())) {
                    return method.invoke(this, args);
                } else {
                    return "curl:" + args[0];
                }
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }
}
