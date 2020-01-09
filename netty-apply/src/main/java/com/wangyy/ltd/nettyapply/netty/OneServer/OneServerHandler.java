package com.wangyy.ltd.nettyapply.netty.OneServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;


public class OneServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) httpObject;
            
            String requestUri = httpRequest.getUri();
            System.out.println("requestUri --> " + requestUri);

            HttpHeaders headers = httpRequest.headers();
            System.out.println("head -> " + headers);
            ByteBuf byteBuf = Unpooled.copiedBuffer("this is netty", CharsetUtil.UTF_8);

            FullHttpResponse fullHttpResponse=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,byteBuf);
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            
            
            ctx.writeAndFlush(fullHttpResponse);
            
        }
    }
}
