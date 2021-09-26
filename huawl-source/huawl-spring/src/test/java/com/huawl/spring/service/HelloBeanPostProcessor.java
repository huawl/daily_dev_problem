package com.huawl.spring.service;

import com.huawl.spring.annocation.Component;
import com.huawl.spring.beans.processor.BeanPostProcessor;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/20 18:32
 */
@Component
public class HelloBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("===postProcessBeforeInitialization===" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("===postProcessAfterInitialization===" + beanName);
        return bean;
    }
}
