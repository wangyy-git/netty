package com.wangyy.ltd.nettyapply.netty.OneServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


public class OneServerInitializer extends ChannelInitializer<SocketChannel> {
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("OneServerHandler",new OneServerHandler());
    }
}
