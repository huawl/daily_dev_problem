package com.huawl.spring;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 17:18
 */
public class BeanNameUtil {

    /**
     * 根据类名转换未beanName
     * @param clazz
     * @return
     */
    public static String getName(Class clazz){
        return clazz.getName().substring(0, 1).toUpperCase() + clazz.getName().substring(1);
    }
}
