package com.wangyy.ltd.nettystudy.myjedis.utils;

import com.wangyy.ltd.nettystudy.myjedis.enums.RESPCharater;
import com.wangyy.ltd.nettystudy.myjedis.enums.RESPMethod;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JedisCommandAppendUtils {

    /**
     * 尊从RESP协议拼接字符
     * $6\r\nfoobar\r\n     表示字符串为 foobar，而6就是foobar的字符长度
     * "$0\r\n\r\n"         空字符串
     * "$-1\r\n"            null
     * 向redis传递一组命令(/表示换行)：
     * *3(一共3组) / $3 / SET(command) / $4(后者的length) / test / $3 / mxm
     */
    public static String commandUtil(RESPMethod command, byte[]... bts){
        StringBuilder str = new StringBuilder();
        //此处后边再理解一下1+bts.length结合  应该是基于协议的拼接
        //bts是可变数组，里面包含的是key value，指令是单独传进来的，所以要+1
        //命令本身占一行
        str.append(RESPCharater.START.getCharacter()).append(1+bts.length)
                .append(RESPCharater.END.getCharacter());
        //拼接命令
        str.append(RESPCharater.LENGTH.getCharacter()).append(command.getMethod().length())
                .append(RESPCharater.END.getCharacter());

        str.append(command.getMethod()).append(RESPCharater.END.getCharacter());

        //此处依次拼接成了$3 / SET / $4 / test / $3 / mxm
//        Arrays.stream(bts).forEach(bt -> {
//
//        });
        for (byte[] bt : bts) {
            str.append(RESPCharater.LENGTH.getCharacter())
                    //此处应该是bt.length 即使是汉字  因为就是解析成byte[]传递的
                    .append(bt.length)
                    .append(RESPCharater.END.getCharacter());
            str.append(new String(bt,StandardCharsets.UTF_8))
                    .append(RESPCharater.END.getCharacter());
        }

        System.out.println(str);
        return str.toString();
    }

    public static void main(String[] args) {
        commandUtil(RESPMethod.SET,"test".getBytes(),"这是测试".getBytes());
    }
}
