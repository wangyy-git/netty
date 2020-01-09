package com.wangyy.ltd.nettyapply.netty.ServerAndClient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));//计算当前待发送消息的二进制字节长度，将该长度添加到ByteBuf的缓冲区头中
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));  //将byte数据解码成String
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));  //将字符串编码成byte数据
        pipeline.addLast(new NettyClientHandler());
    }
}
