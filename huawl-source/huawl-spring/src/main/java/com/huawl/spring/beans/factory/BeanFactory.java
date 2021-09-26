package com.huawl.spring.beans.factory;

import com.huawl.spring.beans.definition.BeanDefinition;

import java.util.List;
import java.util.Map;

/**
 * 衍生类主要对bean生命周期的操作
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 15:55
 */
public interface BeanFactory {

    Object getBean(String name);

    List<Object> getBean(Class type);

    Map<String, BeanDefinition> getBeanDefinitions(String name);

}
