package com.huawl.spring.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/17 17:33
 */
public class AnnotationUtil {

    /**
     * 判断类是否具备某个注解
     * @param targetClass
     * @param isAnd
     * @param annoClasses
     * @return
     */
    public static boolean isClass(Class targetClass,boolean isAnd,Class... annoClasses){
        boolean isMatch = false;
        for (int i = 0; i < annoClasses.length; i++) {
            if(targetClass.isAnnotationPresent(annoClasses[i])){
                isMatch = true;
            }
            if(!isAnd && isMatch){
                return true;
            }
            if(isAnd && !isMatch){
                return false;
            }
        }
        return isMatch;
    }

    /**
     * 判断方法是否具备某个注解
     * @param targetMthod
     * @param isAnd
     * @param annoClasses
     * @return
     */
    public static boolean isMethod(Method targetMthod,boolean isAnd,Class... annoClasses){
        boolean isMatch = false;
        for (int i = 0; i < annoClasses.length; i++) {
            if(targetMthod.isAnnotationPresent(annoClasses[i])){
                isMatch = true;
            }
            if(!isAnd && isMatch){
                return true;
            }
            if(isAnd && !isMatch){
                return false;
            }
        }
        return isMatch;
    }

    /**
     * 判断方法是否具备某个注解
     * @param targetMthod
     * @param isAnd
     * @param annoClasses
     * @return
     */
    public static boolean isField(Field field, boolean isAnd, Class... annoClasses){
        boolean isMatch = false;
        for (int i = 0; i < annoClasses.length; i++) {
            if(field.isAnnotationPresent(annoClasses[i])){
                isMatch = true;
            }
            if(!isAnd && isMatch){
                return true;
            }
            if(isAnd && !isMatch){
                return false;
            }
        }
        return isMatch;
    }
}
