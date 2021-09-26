package com.huawl.spring.beans.processor;

import com.huawl.spring.beans.factory.AbstractBeanFactory;

/**
 * 用于可以扩展bean定义信息的
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 15:57
 */
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(AbstractBeanFactory beanFactory);
}
