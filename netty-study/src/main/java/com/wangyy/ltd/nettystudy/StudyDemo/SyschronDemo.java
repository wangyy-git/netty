package com.wangyy.ltd.nettystudy.StudyDemo;


/**
 * 同步及阻塞相关示例
 * 同步 正常调用
 * 异步 基于回调
 * 阻塞   没开线程的
 * 非阻塞  开了线程,异步
 */
public class SyschronDemo {

    public static void main(String[] args) {
        //同步阻塞
        Integer synchronization = synchronization(1, 2);
        System.out.println(synchronization + "---同步阻塞");

        //异步阻塞
        synchronization(new Monitor() {
            @Override
            public void returnMethod(Object obj) {
                System.out.println(obj+"---异步阻塞");
            }
        },1,2);

        //异步阻塞2
        Monitor2 monitor2 = new Monitor2();
        synchronization2(monitor2,1,2);
        System.out.println(monitor2.returnData+"---异步阻塞2");


        //异步非阻塞
        Monitor2 monitor3 = new Monitor2();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronization2(monitor3,1,2);
            }
        }).start();
        System.out.println(monitor3.getReturnData()+"---异步非阻塞");


        //同步非阻塞2
        Monitor2 monitor4 = new Monitor2();
        new Thread(new MyRunnable(monitor4)).start();
        System.out.println(monitor4.getReturnData()+"---异步非阻塞2");

        //同步非阻塞
        new Thread(new Runnable() {
            @Override
            public void run() {
                Integer synchronization2 = synchronization(1, 2);
                System.out.println(synchronization2+"---同步非阻塞");
            }
        }).start();
    }

    private static Integer synchronization(int a,int b){
        return a+b;
    }

    private static void synchronization(Monitor monitor,int a, int b){
        monitor.returnMethod(a+b);
    }

    interface Monitor{
        void returnMethod(Object object);
    }

    private static void synchronization2(Monitor2 monitor,int a,int b){
        int c=a+b;
        monitor.setReturnData(c);
        monitor.setDone(true);
    }

    static class Monitor2{
        private Object returnData;
        private boolean isDone;

        public Object getReturnData() {
            if(isDone){
                return returnData;
            }else{
                return getReturnData();
            }
        }

        public void setReturnData(Object returnData) {
            this.returnData = returnData;
        }

        public void setDone(boolean done) {
            isDone = done;
        }

        public boolean isDone() {
            return isDone;
        }
    }

    static class MyRunnable implements Runnable{
        Monitor2 monitor2;

        private MyRunnable(Monitor2 monitor2){
            this.monitor2=monitor2;
        }

        @Override
        public void run() {
            synchronization2(monitor2,1,2);
        }
    }
}
