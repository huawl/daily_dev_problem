package com.huawl.spring.mvc;

import java.lang.reflect.Method;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/17 17:13
 */
public class HandlerMapping {

    private Object target;

    private Object[] args;

    private Method method;

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
