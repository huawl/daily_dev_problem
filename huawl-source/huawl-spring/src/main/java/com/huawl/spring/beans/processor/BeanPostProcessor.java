package com.huawl.spring.beans.processor;

/**
 * 扩展bean对象初始化之后
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 15:57
 */
public interface BeanPostProcessor {
    /**
     * bean初始化后NO.2
     * @param bean
     * @param beanName
     * @return
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * bean初始化后NO.3
     * @param bean
     * @param beanName
     * @return
     */
    Object postProcessAfterInitialization(Object bean, String beanName);
}
