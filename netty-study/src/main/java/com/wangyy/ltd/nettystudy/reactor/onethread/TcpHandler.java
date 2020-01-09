package com.wangyy.ltd.nettystudy.reactor.onethread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class TcpHandler implements Runnable {

    private final SelectionKey selectionKey;
    private final SocketChannel socketChannel;

    int state;

    public TcpHandler(SelectionKey selectionKey, SocketChannel socketChannel) {
        this.selectionKey = selectionKey;
        this.socketChannel = socketChannel;
        state = 0; // 初始狀態設定為READING
    }

    @Override
    public void run() {
        
        try {
            if (state == 0) read();
            else send();
        } catch (IOException e) {
            System.out.println("[Warning!] A client has been closed.");
            closeChannel();
            e.printStackTrace();
        }
        
    }

    private synchronized void read() throws IOException {
        // non-blocking下不可用Readers，因為Readers不支援non-blocking
        byte[] bts = new byte[1024];
        ByteBuffer byteBuffer = ByteBuffer.wrap(bts);

        int read = socketChannel.read(byteBuffer);

        if (read == -1) {
            System.err.println("[Warning!] A client has been closed...");
            closeChannel();
            return;
        }


        String str = new String(bts);
        if (!str.isEmpty()) {
            process(str);
            System.out.println(socketChannel.socket().getRemoteSocketAddress().toString() + " --> " + str);
            state = 1;//改变状态

            //将此键的 interest 集合设置为给定值
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            //使一個阻塞住的selector操作立即返回
            //wakeup 使尚未返回的第一个选择操作立即返回
            selectionKey.selector().wakeup();
        }
    }


    // get message from message queue
    private void send() throws IOException {
        String log = "Your message has sent to " + socketChannel.socket().getLocalSocketAddress().toString() + "\r\n";
        
        //wrap自動把buf的position設為0，所以不需要再flip()
        ByteBuffer byteBuffer = ByteBuffer.wrap(log.getBytes());
        
        while (byteBuffer.hasRemaining()) {
            //回傳給client回應字符串，發送buf的position位置 到limit位置為止之間的內容
            socketChannel.write(byteBuffer);
        }
        
        
        state = 0;
        // 通過key改變通道註冊的事件  注意此处的改变
        selectionKey.interestOps(SelectionKey.OP_READ);
        selectionKey.selector().wakeup();
    }

    private void closeChannel() {
        try {
            selectionKey.cancel();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(String str) {
        // do process(decode, logically process, encode)...
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
