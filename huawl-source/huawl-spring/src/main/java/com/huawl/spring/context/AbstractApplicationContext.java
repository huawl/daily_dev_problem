package com.huawl.spring.context;

import com.huawl.spring.beans.definition.BeanDefinition;
import com.huawl.spring.beans.factory.AbstractBeanFactory;
import com.huawl.spring.beans.processor.BeanFactoryPostProcessor;
import com.huawl.spring.beans.processor.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 17:23
 */
public abstract class AbstractApplicationContext implements ApplicationContext{

    private AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    @Override
    public List<Object> getBean(Class type) {
        return beanFactory.getBean(type);
    }

    @Override
    public Map<String, BeanDefinition> getBeanDefinitions(String name) {
        return beanFactory.getBeanDefinitions(name);
    }

    protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception;

    protected abstract void onRefresh() throws Exception;

    public List<Object> getBeanByAnnotation(Class<?> clazz) throws Exception{
        List<Object> beans = new ArrayList<>();
        Map<String, BeanDefinition> beanDefinitionMaps = beanFactory.getBeanDefinitions(null);
        for (Map.Entry<String, BeanDefinition> entry:beanDefinitionMaps.entrySet()) {
            BeanDefinition bd = entry.getValue();
            if(bd.getClazz().isAnnotationPresent(clazz)){
                beans.add(getBean(entry.getKey()));
            }
        }
        return beans;
    }


    /**
     * 注册容器中所有的beanpostprocessor
     */
    private void invokeBeanFactoryPostProcessor(){
        List<Object> beanFactoryPostProcessors = beanFactory.getBean(BeanFactoryPostProcessor.class);
        for (int i = 0; i < beanFactoryPostProcessors.size(); i++) {
            BeanFactoryPostProcessor beanFactoryPostProcessor = (BeanFactoryPostProcessor) beanFactoryPostProcessors.get(i);
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }
    /**
     * 注册容器中所有的beanpostprocessor
     */
    private void registerBeanPostProcessor(){
        List<Object> beanPostProcessors = beanFactory.getBean(BeanPostProcessor.class);
        for (int i = 0; i < beanPostProcessors.size(); i++) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessors.get(i));
        }
    }

    public void refresh() throws Exception {
        loadBeanDefinitions(beanFactory);
        invokeBeanFactoryPostProcessor();
        registerBeanPostProcessor();
        beanFactory.preInstantiateSingletons();
        onRefresh();
    }

}
