package com.wangyy.ltd.nettystudy.myjedis.enums;

public enum RESPMethod {

    SET("SET"),
    GET("GET"),
    INCREASE("INCR");

    private String method;

    RESPMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
