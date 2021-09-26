package com.huawl.spring.beans.definition;

/**
 * 获取bean定义信息
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 16:04
 */
public interface BeanDefinitionReader {

    void loadBeanDefinition(String location)  throws Exception ;
}
