package com.huawl.spring.test;

import com.huawl.spring.context.ApplicationContext;
import com.huawl.spring.context.ClassPathApplicationContext;
import com.huawl.spring.service.ISayService;
import com.huawl.spring.service.IUserInfoService;
import com.huawl.spring.service.StockService;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 17:37
 */
public class XmlApplicationContextTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathApplicationContext("ioc.xml");
        IUserInfoService userInfoService = (IUserInfoService) applicationContext.getBean("userInfoService");
        userInfoService.printUserA();
        ISayService sayService = (ISayService) applicationContext.getBean("sayService");
        sayService.printSayB();

        StockService stockService = (StockService)applicationContext.getBean("stockService");
        System.out.println(stockService.addStock(3,5));
    }
}
