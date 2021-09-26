package com.huawl.spring.service;

import com.huawl.spring.annocation.Autowire;
import com.huawl.spring.annocation.Component;
import com.huawl.spring.beans.factory.BeanFactory;
import com.huawl.spring.beans.factory.BeanFactoryAware;
import com.huawl.spring.beans.factory.InitializingBean;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/16 17:39
 */
@Component
public class UserInfoService implements IUserInfoService, BeanFactoryAware, InitializingBean {


    @Autowire
    private ISayService sayService;

    @Override
    public void printUserA() {
        System.out.println("====printUserA==== username:");
        sayService.printSayA();
    }

    @Override
    public void printUserB() {
        System.out.println("====printUserB==== username:");
    }

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("=====afterPropertiesSet====");
    }
}
