package com.huawl.spring.beans.factory;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 16:32
 */
public interface ObjectFactory<T> {

    T getObject();
}
