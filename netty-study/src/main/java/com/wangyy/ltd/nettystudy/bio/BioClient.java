package com.wangyy.ltd.nettystudy.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class BioClient {
    public static void main(String[] args) {
        try {
            Socket accept = new Socket("127.0.0.1", 800);
            new Thread(new BioClientHandler(accept)).start();

            OutputStream outputStream = accept.getOutputStream();
            Scanner scanner=new Scanner(System.in);
            System.out.print("请输入要发送的消息：");

            while (true){
                String s = scanner.nextLine();
                if(s.trim().equals("over")){
                    break;
                }
                outputStream.write(s.getBytes());
                outputStream.flush();
            }


//            new Thread(() -> {
//                while (true) {
//                    byte[] bts = new byte[1024];
//                    try {
//                        int read = accept.getInputStream().read(bts);
//
//                        if (read > 0) {
//                            System.out.println(new String(bts));
//                            break;
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();

//            while (true) {
//                Scanner sc = new Scanner(System.in);
//                System.out.print("输入：");
//                String line = sc.nextLine();
//                accept.getOutputStream().write(line.getBytes());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
