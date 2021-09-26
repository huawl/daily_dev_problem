package com.huawl.spring.beans.definition;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 17:34
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    private Map<String,BeanDefinition> registry = new LinkedHashMap<>();

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }
}
