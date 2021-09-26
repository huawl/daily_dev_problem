package com.huawl.tomcat.connector;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/10 18:14
 */
public class HttpServletRequest {

    private Map<String,Object> params;

    private String uri;

    private InputStream in;

    public HttpServletRequest(InputStream in){
        this.in = in;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Object getParamValue(String key){
        return params.get(key);
    }
}
