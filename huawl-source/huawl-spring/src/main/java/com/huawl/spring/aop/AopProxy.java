package com.huawl.spring.aop;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/7/21 11:10
 */
public interface AopProxy {

    Object getProxy(AdviceSupport adviceSupport);
}
