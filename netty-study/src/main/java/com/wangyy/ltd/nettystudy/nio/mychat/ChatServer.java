package com.wangyy.ltd.nettystudy.nio.mychat;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class ChatServer {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private long timeout=2000;

    public ChatServer(){
        try {
            //服务端channel
            serverSocketChannel= ServerSocketChannel.open();
            //选择器对象
            selector= Selector.open();
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(9090));
            //设置非阻塞式
            serverSocketChannel.configureBlocking(false);
            //把ServerSocketChannel注册给Selector
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//监听连接
            System.out.println("服务端准备就绪");

//            start();
        }catch (Exception e){
             e.printStackTrace();
        }
    }
}
