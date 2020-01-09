package com.wangyy.ltd.nettystudy.nio;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Random;

public class NioDemo {

    public static void main(String[] args) throws IOException {
        //nio_1();
//        nio_channel();
//        nio_slice();
        System.out.println(System.nanoTime());
        nio_wrap();
    }

    /**
     * 基础DEMO
     */
    private static void nio_1(){
        IntBuffer intBuffer = IntBuffer.allocate(8);
        for (int i=0;i<intBuffer.capacity();i++){
            int anInt = new Random().nextInt(12 );
            intBuffer.put(anInt);
        }

        System.out.println(intBuffer);

        intBuffer.flip();

        for (int i = 0; i < intBuffer.capacity(); i++) {
            //get结束后 position自动往下走 所以不要index也会依次向下取数据
            System.out.println(intBuffer.get(i));
        }

        while (intBuffer.hasRemaining()) {
            System.out.print(intBuffer.get() + " ---- ");
        }
    }


    private static void nio_channel() throws IOException {
        File file = new File("E:\\MyProject\\studyproject\\netty-study\\src\\main\\resources\\nio\\nio.txt");

        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024); //堆内缓存
        channel.read(byteBuffer);
        byteBuffer.flip();


        FileOutputStream outputStream = new FileOutputStream("nio_out.txt");
        FileChannel channelWrite = outputStream.getChannel();


        byte[] bytes = new byte[1024];
        int i = 0;
        while (byteBuffer.hasRemaining()) {
            bytes[i++] = byteBuffer.get();
            channelWrite.write(byteBuffer);
        }

        outputStream.close();
        fileInputStream.close();

        System.out.println(new String(bytes));

    }


    private static void nio_slice(){
        ByteBuffer byteBuffer=ByteBuffer.allocate(10);
        for(int i=0;i<byteBuffer.capacity();++i){
            System.out.print((byte)i + " -- ");
            byteBuffer.put((byte)i);
        }

        //若移动后想重新回来 可用mark方法老做标记
        //可以再调用reset()方法 来讲mark返给position
        byteBuffer.mark(); //mark = position
        byteBuffer.position(2);
        byteBuffer.limit(8);
        ByteBuffer resetBuffer = byteBuffer.slice();
        //此处resetBuffer的capacity为6
        //也就是slice只是复制了position至limit 即buffer中呗读取的部分 其他部分不变
        //也就是只是复制了对应的内存地址
        //所以在下面对resetBuffer的相关元素进行调整时原生buffer的也跟着改变了
        //而且对应原生buffer的位置就是position -> limit的位置


        System.out.println("reset ca -- " + resetBuffer.capacity());

        System.out.println();
        System.out.print("reset");
        for(int i=0;i<resetBuffer.capacity();i++){
            byte anInt = resetBuffer.get();
            System.out.print(anInt + " -- ");
            resetBuffer.put(i, (byte) (anInt*2));
        }

        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());
        System.out.println();
        System.out.println("byteBuffer");
        while (byteBuffer.hasRemaining()){
            System.out.print(byteBuffer.get() + " -- ");
        }
    }

    private static void nio_readOnly(){
        ByteBuffer byteBuffer=ByteBuffer.allocate(10);
        for(int i=0;i<byteBuffer.capacity();i++){
            byteBuffer.put((byte)i);
        }
        //只可读  一写就报错 ReadOnlyBufferException
        ByteBuffer byteBuffer1 = byteBuffer.asReadOnlyBuffer();

        System.out.println(byteBuffer.getClass());
        System.out.println(byteBuffer1.getClass());
        byteBuffer1.flip();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer1.position());
        for(int i=0;i<byteBuffer1.capacity();i++){
            System.out.println(byteBuffer1.get());
        }
    }


    /**
     * 两者是内存地址的相互引用
     * 所以改其中一个  另一个也被更改
     */
    private static void nio_wrap(){
        byte[] bytes=new byte[]{'a','b','c'};

        //Wraps a byte array into a buffer
        ByteBuffer byteBuffer=ByteBuffer.wrap(bytes);
        bytes[0]='b';
        byteBuffer.put(2,(byte)'d');
        for(int i=0;i<byteBuffer.capacity();i++){
            System.out.println((char)byteBuffer.get());
        }

        for (byte b : bytes) {
            System.out.print((char)b + " -- ");
        }
    }
}
