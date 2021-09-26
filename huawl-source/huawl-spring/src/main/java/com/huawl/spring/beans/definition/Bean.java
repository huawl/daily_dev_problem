package com.huawl.spring.beans.definition;

import java.util.List;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 18:21
 */
public class Bean {

    private String id;

    private String clazz;

    private List<BeanChild> childs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
