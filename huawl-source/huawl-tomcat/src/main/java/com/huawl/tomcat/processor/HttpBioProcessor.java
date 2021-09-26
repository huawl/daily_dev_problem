package com.huawl.tomcat.processor;

import com.huawl.tomcat.connector.HttpServletRequest;
import com.huawl.tomcat.connector.HttpServletResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/13 17:47
 */
public class HttpBioProcessor implements Runnable{

    private Socket socket;

    private HttpServlet servlet;

    private HttpHandler handler;

    private HttpServletRequest request;

    private HttpServletResponse response;

    public HttpBioProcessor(Socket socket, HttpServlet servlet, HttpHandler handler){
        this.socket = socket;
        this.servlet = servlet;
        this.handler = handler;
    }

    @Override
    public void run(){
        try {
            request = new HttpServletRequest(socket.getInputStream());
            response = new HttpServletResponse(socket.getOutputStream());
            handler.handlerRequest(request,response);
            servlet.service(request,response);
            handler.handlerResponse(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(!socket.isClosed()){
                    socket.close();
                }
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
