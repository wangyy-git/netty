package com.wangyy.ltd.nettystudy.reactor.onethread;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable {

    private Selector selector;
    private ServerSocketChannel ssChannel;

    public Acceptor(Selector selector, ServerSocketChannel ssChannel) {
        this.selector = selector;
        this.ssChannel = ssChannel;
    }


    @Override
    public void run() {

        try {
            SocketChannel acceptChannel = ssChannel.accept();
            System.out.println("acceptor aChannel -->" + acceptChannel.getRemoteAddress().toString() + "is connected ...");

            if (acceptChannel != null ) {
                acceptChannel.configureBlocking(false);

                //SocketChannel向selector註冊一個OP_READ事件，然後返回該通道的key
                SelectionKey selectionKey = acceptChannel.register(selector, SelectionKey.OP_READ);
                selector.wakeup();

                //attach(Object ob) 将给定的对象附加到此键
                //attachment()      获取当前的附加对象
                selectionKey.attach(new TcpHandler(selectionKey,acceptChannel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
