package com.huawl.spring.aop;

/**
 * 被代理的目标对象
 * @author Luther
 * @version 1.0
 * @date 2021/7/21 10:58
 */
public class TargetSource {

    private Object target;


    private Class<?>[] interClazz;

    public TargetSource(Object target, Class<?>[] interClazz) {
        this.target = target;
        this.interClazz = interClazz;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class<?>[] getInterClazz() {
        return interClazz;
    }

    public void setInterClazz(Class<?>[] interClazz) {
        this.interClazz = interClazz;
    }
}
