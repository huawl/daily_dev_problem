package com.huawl.spring.service;

import com.huawl.spring.annocation.Autowire;
import com.huawl.spring.annocation.Component;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/20 14:06
 */
@Component
public class SayService implements ISayService{

    @Autowire
    private IUserInfoService userInfoService;

    @Override
    public void printSayA() {
        System.out.println("====printSayA====");
    }

    @Override
    public void printSayB() {
        System.out.println("====printSayB====");
        userInfoService.printUserB();
    }

    @Override
    public int sum(int a, int b) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return a + b;
    }
}
