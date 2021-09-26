package com.huawl.tomcat.processor;

import com.huawl.tomcat.connector.HttpServletRequest;
import com.huawl.tomcat.connector.HttpServletResponse;
import com.huawl.tomcat.constant.ReqMessageConstant;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/16 17:46
 */
public class CommonBioHandler implements HttpHandler{
    @Override
    public void handlerRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in  = request.getIn();
        byte[] bs = new byte[1046];
        in.read(bs,0,in.available());
        String req = new String(bs,"utf-8");
        System.out.println("req:\r\n" + req);
        if(req == null || req == ""){
            return;
        }
        int start = req.indexOf("GET") + 4;
        int end = req.indexOf("HTTP/1.1") - 1;
        if(start < 0 || end < 0){
            return;
        }
        String urlAndReqs = req.substring(start,end);
        int startUri = 0;
        int endUri = urlAndReqs.indexOf("?");
        String uri = urlAndReqs.substring(startUri,endUri);
        request.setUri(uri);
        int startParams = urlAndReqs.indexOf("?") + 1;
        String params = urlAndReqs.substring(startParams);
        if(params == null || params == ""){
            return;
        }
        Map<String,Object> paramMap = new HashMap<>();
        String[] paramArr  = params.split("&");
        for (String param:paramArr) {
            String[] keyValues = param.split("=");
            paramMap.put(keyValues[0],keyValues[1]);
        }
        request.setParams(paramMap);
//        System.out.println("request:" + request);
    }

    @Override
    public void handlerResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(ReqMessageConstant.DEFAULTVERSION);
        sb.append(ReqMessageConstant.BLANK);
        sb.append(200);
        sb.append(ReqMessageConstant.BLANK);
        sb.append("OK");
        sb.append(ReqMessageConstant.CRLF);
        sb.append("Content-Type: application/json;charset=UTF-8");
        sb.append(ReqMessageConstant.CRLF);
        sb.append("Access-Control-Allow-Methods: GET,POST,OPTIONS,PUT,DELETE");
        sb.append(ReqMessageConstant.CRLF);

        sb.append(ReqMessageConstant.CRLF);
        sb.append(response.getBody().toString());
        sb.append(ReqMessageConstant.CRLF);
        String resp = sb.toString();
//        System.out.println("resp:\r\n" + resp);
        response.getOut().write(resp.getBytes(StandardCharsets.UTF_8));
        response.getOut().flush();
    }
}
