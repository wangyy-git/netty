package com.wangyy.ltd.nettystudy.myjedis.interfaces;

import java.net.Socket;

public interface JedisSocket {

    void send(String instruction);

    String read();
}
