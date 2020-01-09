package com.wangyy.ltd.nettystudy.myjedis.enums;

/**
 * redis采用的是RESP协议
 * 详情见https://www.jianshu.com/p/daa3cb672470
 * Integer第一个字节以:开头，随后紧跟数字，以CRLF结束  ":1000\\r\\n"
 * CRLF(Carriage-Return Line-Feed 回车换行)
 * 这里列举协议规定的一些分隔符
 */
public enum RESPCharater {

    END("\r\n"),
    START("*"),
    LENGTH("$");


    private String character;

    RESPCharater(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }}

