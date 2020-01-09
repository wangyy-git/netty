package com.wangyy.ltd.nettyapply.netty.ServerAndClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    
    private String msg;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        this.msg = msg;
        System.out.println(ctx.channel().remoteAddress() + ",client output " + msg);
        for (int i = 0; i < 10; i++) {
            System.out.println("msg -> " + msg);
            ctx.writeAndFlush(LocalDateTime.now() + " -> this is yellow river");
        }
    }
    
    //channelActive方法完全走完之后  才走的channelRead0
    //并且channelRead0会和channelActive循环次数一直
//    @Override
//    public void channelActive(ChannelHandlerContext ctx){
////        while (true) {
////            //先收到服务端的相应
////            //若没有非空判断  此处报空指针
////            if ("滚".equals(msg) || "get out".equals(msg)) {
////                return;
////            }
////            System.out.println(LocalDateTime.now() + "长江 ---> " + msg);
////            System.out.print(LocalDateTime.now() + "黄河 ---> ");
////            Scanner scanner = new Scanner(System.in);
////            ctx.writeAndFlush(scanner.next());
//////            ctx.writeAndFlush("form client "+ LocalDateTime.now());
////        }
//        
//        
////                Thread.sleep(1000);
//        
//            
//        
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
    
}
