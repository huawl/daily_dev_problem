package com.huawl.spring.beans.definition;

/**
 * bean应用信息
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 16:59
 */
public class BeanReference {

    private String name;

    private Object value;

    public BeanReference(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
