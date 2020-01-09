package com.wangyy.ltd.nettystudy.myjedis.impl;

import com.wangyy.ltd.nettystudy.myjedis.enums.RESPMethod;
import com.wangyy.ltd.nettystudy.myjedis.interfaces.JedisMethod;
import com.wangyy.ltd.nettystudy.myjedis.interfaces.JedisSocket;
import com.wangyy.ltd.nettystudy.myjedis.utils.JedisCommandAppendUtils;

import java.nio.charset.StandardCharsets;

public class JedisMethodImpl implements JedisMethod {
    private JedisSocket socket = new JedisSocketImpl("192.168.195.108",6379);

    @Override
    public String set(String key, String value) {
        socket.send(JedisCommandAppendUtils.commandUtil(RESPMethod.SET,key.getBytes(StandardCharsets.UTF_8),value.getBytes(StandardCharsets.UTF_8)));
        return socket.read();
    }

    @Override
    public String get(String key) {
        socket.send(JedisCommandAppendUtils.commandUtil(RESPMethod.GET,key.getBytes(StandardCharsets.UTF_8)));
        return socket.read();
    }

    /**
     * 为何调用read()?
     */
    @Override
    public String increase(String key, String value) {
        socket.send(JedisCommandAppendUtils.commandUtil(RESPMethod.INCREASE,key.getBytes(StandardCharsets.UTF_8),value.getBytes(StandardCharsets.UTF_8)));
        return socket.read();
    }
}
