package com.huawl.tomcat.webserver;

import com.huawl.tomcat.processor.*;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/17 15:39
 */
public class BioTomcatWebServer implements WebServer,Runnable{

    volatile boolean start = true;

    private ExecutorService executorService;

    private HttpServlet servlet;

    public BioTomcatWebServer(HttpServlet servlet) {
        this.servlet = servlet;
    }

    @Override
    public void start() {
        executorService = new ThreadPoolExecutor(16,100,
                2, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),new ThreadPoolExecutor.AbortPolicy());
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        ServerSocket server = null;
        HttpHandler handler = new CommonBioHandler();
        try {
            server = new ServerSocket(8080,50, InetAddress.getByName("127.0.0.1"));
            servlet.init();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        while (start){
            Socket socket =  null;
            try {
                socket = server.accept();
                executorService.execute(new HttpBioProcessor(socket,servlet,handler));
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
}
