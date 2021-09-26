package com.huawl.spring.beans.definition;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 16:46
 */
public class PropertyValue {

    public PropertyValue(String name,Object value){
        this.name = name;
        this.value = value;
    }

    private String name;

    private Object value;

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
