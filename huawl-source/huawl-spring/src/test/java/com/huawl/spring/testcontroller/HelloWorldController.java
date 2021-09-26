package com.huawl.spring.testcontroller;

import com.huawl.spring.annocation.Autowire;
import com.huawl.spring.annocation.Controller;
import com.huawl.spring.annocation.RequestMapping;
import com.huawl.spring.service.IUserInfoService;
import com.huawl.tomcat.connector.HttpServletRequest;
import com.huawl.tomcat.connector.HttpServletResponse;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/17 17:26
 */
@RequestMapping("/hello")
@Controller
public class HelloWorldController {

    @Autowire
    private IUserInfoService userInfoService;

    @RequestMapping("/one")
    public String one(HttpServletRequest request, HttpServletResponse response){
        return "HelloWorldb One:" + request.getParams().toString() ;
    }

    @RequestMapping("/two")
    public String two(HttpServletRequest request, HttpServletResponse response){
        userInfoService.printUserA();;
        return "HelloWorld two:" + request.getParams().toString() ;
    }

    public String d(){
        return "";
    }
}
