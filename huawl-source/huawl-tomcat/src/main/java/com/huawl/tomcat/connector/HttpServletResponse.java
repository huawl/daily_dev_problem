package com.huawl.tomcat.connector;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/10 18:14
 */
public class HttpServletResponse {

    private OutputStream out;

    private Object body;

    public HttpServletResponse(OutputStream out){
        this.out = out;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
