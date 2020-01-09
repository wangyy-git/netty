package com.wangyy.ltd.nettystudy.myjedis;

import com.wangyy.ltd.nettystudy.myjedis.impl.JedisMethodImpl;
import com.wangyy.ltd.nettystudy.myjedis.interfaces.JedisMethod;

public class MyJedis {

    public static void main(String[] args) {
        JedisMethod jedis = new JedisMethodImpl();

        System.out.println("-----------------");
        System.out.println(jedis.get("taibai"));
    }


}
