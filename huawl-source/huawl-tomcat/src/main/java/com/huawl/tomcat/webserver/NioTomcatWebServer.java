package com.huawl.tomcat.webserver;

import com.huawl.tomcat.processor.CommonBioHandler;
import com.huawl.tomcat.processor.HttpHandler;
import com.huawl.tomcat.processor.HttpBioProcessor;
import com.huawl.tomcat.processor.HttpServlet;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Luther
 * @version 1.0
 * @date 2021/8/17 15:39
 */
public class NioTomcatWebServer implements WebServer,Runnable{

    volatile boolean start = true;

    private ExecutorService executorService;

    private HttpServlet servlet;

    public NioTomcatWebServer(HttpServlet servlet) {
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
        ServerSocketChannel server = null;
        Selector selector = null;
        HttpHandler handler = new CommonBioHandler();
        try {
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress("127.0.0.1",8080));
            server.configureBlocking(false);
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);

            servlet.init();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        ByteBuffer readBuff = ByteBuffer.allocate(1024);
        ByteBuffer writeBuff = ByteBuffer.allocate(128);
        writeBuff.put("received".getBytes());
        writeBuff.flip();

        while (start){

            try {
            int nReady = selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isAcceptable()) {
                    // 创建新的连接，并且把连接注册到selector上，而且，
                    // 声明这个channel只对读操作感兴趣。
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    readBuff.clear();
                    socketChannel.read(readBuff);

                    readBuff.flip();
                    System.out.println("received : " + new String(readBuff.array()));
                    key.interestOps(SelectionKey.OP_WRITE);
                    executorService.execute(new HttpBioProcessor(socketChannel.socket(), servlet, handler));
                } else if (key.isWritable()) {
                    writeBuff.rewind();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuff);
                    key.interestOps(SelectionKey.OP_READ);
                    executorService.execute(new HttpBioProcessor(socketChannel.socket(), servlet, handler));
                }
            }
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
}
