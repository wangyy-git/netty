package com.wangyy.ltd.nettyapply.luban.twodome;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;

public class TestClientHandler extends SimpleChannelInboundHandler<String> {

    private String msg;
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        this.msg = s;
        System.out.println(channelHandlerContext.channel().remoteAddress()+",client output"+s);
//        channelHandlerContext.writeAndFlush("form client"+ LocalDateTime.now());
    }


    //通道就绪
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Scanner sc = new Scanner(System.in);
        for (int i=0;i<10;i++){
//            System.out.println("msg ->" + msg);
//            System.out.print("输入： -> ");
//            String next = sc.next();
//            ctx.writeAndFlush(next);
            
            ctx.writeAndFlush("来自客户端的问候");
        }
    }

    //有异常发生
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
