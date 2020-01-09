package com.wangyy.ltd.nettystudy.reactor.onethread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 对于客户端的所以请求，都又一个专门的线程去进行处理，
 * 这个线程无线循环去监听是否又客户的请求到来，
 * 一旦收到客户端的请求，就将其分发给响应的处理器进行处理。
 */
public class Reactor implements Runnable {

    private Selector selector;
    private ServerSocketChannel ssChannel;
    public Reactor(){
        try {
            //Selector 一般称 为选择器 ，当然你也可以翻译为 多路复用器 。
            // 它是Java NIO核心组件中的一个
            // 用于检查一个或多个NIO Channel（通道）的状态是否处于可读、可写。
            // 如此可以实现单线程管理多个channels,也就是可以管理多个网络链接。
            selector = Selector.open();

            ssChannel = ServerSocketChannel.open();

            InetSocketAddress address = new InetSocketAddress(666);
            ssChannel.socket().bind(address); //在ServerSocketChannel绑定监听端口
            ssChannel.configureBlocking(false);//Channel必须是非阻塞的
            SelectionKey selectionKey = ssChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("selectionKey --> " + selectionKey);

            selectionKey.attach(new Acceptor(selector,ssChannel));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 在線程被中斷前持續運行
        while (!Thread.interrupted()) {
            System.out.println("Waiting for new event on port: " + ssChannel.socket().getLocalPort() + "...");

            try {
                // 若沒有事件就緒則不往下執行
                if (selector.select() == 0) continue;
            } catch (IOException e) {
                e.getMessage();
            }
            // 取得所有已就緒事件的key集合
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();

            Iterator<SelectionKey> iterator =  selectionKeySet.iterator();

            while (iterator.hasNext()) {
                dispatch(iterator.next());
                //必须remove 否则会被连续
                iterator.remove();
            }


        }

    }

    /*
     * name: dispatch(SelectionKey key)
     * description: 調度方法，根據事件綁定的對象開新線程
     */
    private void dispatch(SelectionKey key) {
        System.out.println("dispatch key --> " + key);
        //对于上面的attach方法
        Runnable r = (Runnable) (key.attachment()); // 根據事件之key綁定的對象開新線程
        System.out.println("runnable r --> {} " + r);
        if (r != null)
            //开启了一个新线程
            r.run();
    }

}
