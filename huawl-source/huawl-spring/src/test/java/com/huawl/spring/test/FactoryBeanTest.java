package com.huawl.spring.test;

import com.huawl.spring.beans.factory.FactoryBean;
import com.huawl.spring.context.ApplicationContext;
import com.huawl.spring.context.ClassPathApplicationContext;
import com.huawl.spring.service.*;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 17:37
 */
public class FactoryBeanTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathApplicationContext("ioc.xml");
        IOmsClient omsClient = (IOmsClient) applicationContext.getBean("omsClient");
        System.out.println(omsClient.curl("www.baidu.com"));

    }
}
