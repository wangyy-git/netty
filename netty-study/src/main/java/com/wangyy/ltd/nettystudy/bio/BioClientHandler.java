package com.wangyy.ltd.nettystudy.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioClientHandler implements Runnable {

    private Socket socket;

    public BioClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream=null;
        try {
            inputStream = socket.getInputStream();
            int count;
            byte[] bytes = new byte[1024];
            while ((count = inputStream.read(bytes))!=-1){
                System.out.println("\n收到服务器消息：  "+new String(bytes,0,count, StandardCharsets.UTF_8));
                System.out.print("请输入要发送的消息：");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
