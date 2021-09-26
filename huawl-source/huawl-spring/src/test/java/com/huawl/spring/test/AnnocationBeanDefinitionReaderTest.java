package com.huawl.spring.test;

import com.huawl.spring.beans.definition.AnnocationBeanDefinitionReader;
import com.huawl.spring.beans.definition.BeanDefinitionReader;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/30 13:48
 */
public class AnnocationBeanDefinitionReaderTest {

    public static void main(String[] args) throws Exception {
        BeanDefinitionReader reader = new AnnocationBeanDefinitionReader();
        reader.loadBeanDefinition("com.huawl.spring");
        System.out.println("===");
    }
}
