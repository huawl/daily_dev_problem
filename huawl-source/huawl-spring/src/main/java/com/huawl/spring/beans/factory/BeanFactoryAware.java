package com.huawl.spring.beans.factory;

/**
 * bean初始化后NO.1
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 16:17
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory);
}
