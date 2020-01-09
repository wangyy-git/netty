package com.wangyy.ltd.nettystudy.myjedis.interfaces;

/**
 * 指定jedis的基础方法
 */
public interface JedisMethod {

    String set(String key, String value);

    String get(String key);

    String increase(String key,String value);
}
