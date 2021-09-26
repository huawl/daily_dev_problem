package com.huawl.spring.beans.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * bean定义信息
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 16:03
 */
public class BeanDefinition {

    private String name;

    private Class clazz;

    private List<Class> parameterTypes = new ArrayList<>(5);

    private PropertyValues propertyValues = new PropertyValues();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public List<Class> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<Class> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void addParameterType(Class<?> parameterType){
        this.parameterTypes.add(parameterType);
    }
}
