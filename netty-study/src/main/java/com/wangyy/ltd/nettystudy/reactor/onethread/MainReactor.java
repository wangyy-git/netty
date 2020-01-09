package com.wangyy.ltd.nettystudy.reactor.onethread;

/**
 * 反应器设计模式(Reactor pattern)是一种为处理并发服务请求，
 * 并将请求提交到一个或者多个服务处理程序的事件设计模式。
 * 当客户端请求抵达后，服务处理程序使用多路分配策略，由一个非阻塞的线程来接收所有的请求，
 * 然后派发这些请求至相关的工作线程进行处理。
 */
public class MainReactor {

    public static void main(String[] args) {
        Reactor reactor = new Reactor();
        reactor.run();
    }
}
