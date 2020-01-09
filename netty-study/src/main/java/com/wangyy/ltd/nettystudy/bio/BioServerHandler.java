package com.wangyy.ltd.nettystudy.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BioServerHandler implements Runnable {
    private Socket socket;

    public BioServerHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        InputStream inputStream;
        OutputStream outputStream;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            int count;
            byte[] bts = new byte[1024];

            while ((count = inputStream.read(bts)) != -1) {
                String content = new String(bts, 0, count, StandardCharsets.UTF_8);
                System.out.println("content : " + content);

                String answer = content.equalsIgnoreCase("sj")?new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()):"发错了";
                outputStream.write(answer.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
