package com.wangyy.ltd.nettyapply.netty.ServerAndClient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("ctx -- " + ctx.channel().remoteAddress() + " --> " + msg);
        ctx.writeAndFlush("form server"+ UUID.randomUUID());

//        System.out.print(LocalDateTime.now() + "长江 ---> ");
//        Scanner scanner = new Scanner(System.in);
//        ctx.writeAndFlush("这是来自长江的回答");
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        ctx.writeAndFlush("这是来自长江的回答");
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
    
}
