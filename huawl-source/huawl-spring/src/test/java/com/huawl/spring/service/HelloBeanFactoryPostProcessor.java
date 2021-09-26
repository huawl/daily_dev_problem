package com.huawl.spring.service;

import com.huawl.spring.annocation.Component;
import com.huawl.spring.beans.definition.BeanDefinition;
import com.huawl.spring.beans.factory.AbstractBeanFactory;
import com.huawl.spring.beans.processor.BeanFactoryPostProcessor;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/21 10:31
 */
@Component
public class HelloBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(AbstractBeanFactory beanFactory) {
        try {
            String name = "stockService";
            BeanDefinition bd = new BeanDefinition();
            bd.setName(name);
            bd.setClazz(Class.forName("com.huawl.spring.service.StockService"));
            beanFactory.addBeanDefinition(name,bd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String name = "omsClient";
            BeanDefinition bd = new BeanDefinition();
            bd.setName(name);
            bd.setClazz(OmsFactoryBean.class);
            bd.addParameterType(IOmsClient.class);
            beanFactory.addBeanDefinition(name,bd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
