package com.huawl.tomcat.processor;

import com.huawl.tomcat.connector.HttpServletRequest;
import com.huawl.tomcat.connector.HttpServletResponse;

import java.io.IOException;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/16 17:06
 */
public interface HttpServlet {

    void init();

    void service(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
