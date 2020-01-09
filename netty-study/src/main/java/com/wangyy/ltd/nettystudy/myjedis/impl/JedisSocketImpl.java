package com.wangyy.ltd.nettystudy.myjedis.impl;

import com.wangyy.ltd.nettystudy.myjedis.interfaces.JedisSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class JedisSocketImpl implements JedisSocket {

    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    public JedisSocketImpl(String ip,int port) {
        try {
            socket = new Socket(ip, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String instruction) {
        try {
            outputStream.write(instruction.getBytes());
//            outputStream.flush();
        } catch (IOException e) {
            System.out.println("send 方法异常！");
            e.printStackTrace();
        }
    }

    @Override
    public String read() {
        byte[] bts = new byte[1024];
        try {
            int read = inputStream.read(bts);
            return new String(bts,0,read, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("read 方法异常！");
            e.printStackTrace();
        }
        return null;
    }
}
