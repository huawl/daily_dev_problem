package com.huawl.tomcat.processor;

import com.huawl.tomcat.connector.HttpServletRequest;
import com.huawl.tomcat.connector.HttpServletResponse;

import java.io.IOException;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/13 17:47
 */
public class HelloWorldServlet implements HttpServlet{


    @Override
    public void init() {

    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setBody("Hello World!!!!" + request.getParamValue("username"));
    }
}
