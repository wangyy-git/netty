package com.wangyy.ltd.nettystudy.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * BIO:blocking I/O 同步并阻塞
 * 服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程
 * 进行处理，如果这个连接不做任何事情会造成不必要的线程开销，当然可以通过线程池机制改善
 */
public class BioServer {
    /**
     * 此处有3个socket
     * 客户端ServerSocket，
     * 通信时服务端会额外再开一个socket一个连接的socket
     */

    public BioServer() {
    }

    public static void main(String[] args) {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(800);
            while (true) {
                //这个socket是与客户端通信的socket
                System.out.println("client --> " + socket.getInetAddress().toString());

                ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));

                while (true) {
                    Socket accept = socket.accept(); //这个线程是阻塞的  等待客户端的响应
                    System.out.println("连接源 --> " + accept.getInetAddress());
                    executor.execute(new BioServerHandler(accept));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
