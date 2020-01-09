package com.wangyy.ltd.nettystudy.reactor.onethread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientReactor {

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("127.0.0.1", 666);
            System.out.println("连接 目的地。。。。");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            String input;

            while((input=stdIn.readLine()) != null) { // 讀取輸入  
                out.println("input -> " + input); // 發送輸入的字符串  
                out.flush(); // 強制將緩衝區內的數據輸出  
                if(input.equals("exit")) {
                    break;
                }
                System.out.println("server: "+in.readLine());
            }
            clientSocket.close();
            System.out.println("client stop....");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
