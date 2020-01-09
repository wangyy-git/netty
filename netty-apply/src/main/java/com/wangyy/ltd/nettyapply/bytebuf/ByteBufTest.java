package com.wangyy.ltd.nettyapply.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

public class ByteBufTest {
    public static void main(String[] args) {
        test_1();
    }
    
    private static void test_1(){
        //非池化创建 -- 是在内存中新开了一片区域
        //大小为10字节
        //池化 是从netty已经存在的池里面取出一片区域
        ByteBuf byteBuf = Unpooled.buffer(10);
        System.out.println("原始 bytebuf  --> " + byteBuf.toString());
        System.out.println("原始 bytebuf 内容  --> " + Arrays.toString(byteBuf.array()));
        
        //写入一下内容
        byte[] bts = new byte[]{1,2,3,4,5};
        ByteBuf writeBytes = byteBuf.writeBytes(bts);
        System.out.println("地址 -> " + (writeBytes == byteBuf)); //true
        System.out.println("写入之后 bytebuf -> " + byteBuf.toString());//widx = 5
        System.out.println("写入之后 bytebuf 内容  --> " + Arrays.toString(byteBuf.array()));
        
        //读取内容
        //是有指针标记  每读取一次 则之前就向下走
        byte b1 = byteBuf.readByte();
        byte b2 = byteBuf.readByte();
        System.out.println("读取内容为 -> " + b1 + "      " + b2);
        System.out.println("读取之后内容为 -> " + byteBuf.toString()); //ridx readindex变成2
        System.out.println("读取之后内容为 -> " + Arrays.toString(byteBuf.array()));
        ByteBuf byteBuf1 = byteBuf.readerIndex(6);
        System.out.println("byteBuf1 -> " + byteBuf1);
        //java.lang.IndexOutOfBoundsException: readerIndex(2) + length(5) exceeds writerIndex(5)
        //此处长度只能为widx - ridx
//        byte[] bytes = new byte[3];
//        byteBuf.readBytes(bytes);
//        System.out.println("使用数组读取 -> " + Arrays.toString(bytes));
        
        
        //将读取的内容丢弃 
        //读过的内容才是卡丢弃的
        //丢弃前 已经被读过了 ridx=2  widx=5  丢弃后读索引被清为0  widx = widx - ridx = 3
        //[1, 2, 3, 4, 5, 0, 0, 0, 0, 0] -> [3, 4, 5, 4, 5, 0, 0, 0, 0, 0]
        //后面三位前移ridx  widx = 3 若果写 则会从index = 4处写入
        byteBuf.discardReadBytes();
        System.out.println("丢弃读取内容后的 --> " + byteBuf.toString());//ridx: 0, widx: 3
        System.out.println("丢弃读取内容后的 --> " + Arrays.toString(byteBuf.array()));//内容不变
        
        //清空读写指针
        //读写指针全为0 但内容是不变的
        //无法读取了 因为ridx = 0 IndexOutOfBoundsException
        byteBuf.clear();
        System.out.println("清空读写指针内容后的 --> " + byteBuf.toString());//ridx: 0, widx: 0
        System.out.println("清空读写指针内容后的 --> " + Arrays.toString(byteBuf.array()));

        //清空后重新写入
        byte[] bts_2 = new byte[]{6,7};
        byteBuf.writeBytes(bts_2);
        System.out.println("清空后写入之后 bytebuf -> " + byteBuf.toString());//widx = 5
        //[6, 7, 5, 4, 5, 0, 0, 0, 0, 0]
        //写指针widx变成2
        System.out.println("清空后写入之后 bytebuf 内容  --> " + Arrays.toString(byteBuf.array()));
        
        
        //清零
        //清零后内容全为0  但指针索引不变 仍承接上一步操作 为2 
        byteBuf.setZero(0,byteBuf.capacity());
        System.out.println("清0后内容的 --> " + byteBuf.toString());//ridx: 0, widx: 2
        System.out.println("清0后内容的 --> " + Arrays.toString(byteBuf.array()));
        
        //超过容量的写入
        //capacity会增加64位
        byte[] bts_3 = {1,2,3,4,5,6,7,8,9,10,11,12};
        byteBuf.writeBytes(bts_3);
        System.out.println("超过容量的写入后内容的 --> " + byteBuf.toString());//ridx: 0, widx: 2
        System.out.println("超过容量的写入后内容的 --> " + Arrays.toString(byteBuf.array()));
        System.out.println("超过容量的写入后内容的 --> 容量 " + byteBuf.capacity()); //64
        
    }
}
