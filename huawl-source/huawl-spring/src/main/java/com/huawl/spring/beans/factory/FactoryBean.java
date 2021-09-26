package com.huawl.spring.beans.factory;

/**
 * 特殊生成bean的扩展点
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 16:00
 */
public interface FactoryBean<T> {
    T getObject();

    Class<?> getObjectType();
}
