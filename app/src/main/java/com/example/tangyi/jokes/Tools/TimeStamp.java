package com.example.tangyi.jokes.Tools;

import java.util.Random;

/**
 * Created by 在阳光下唱歌 on 2016/6/3.
 * 随机生成时间戳的类
 */
public class TimeStamp  {

    private StringBuilder sb;

    public  String getTimeStamp(int length){
        //创建StringBuilder合集
        sb = new StringBuilder();
        String str="142";
        //sb第一次追加
        sb.append(str);
        /**将本函数中接收的值加入for循环。由于前面已经追加过一个数，
         *如果想要一个10位的数的话，就可以传入9
         */
        for (int i=1;i<length;i++ ) {
            String str1=getOne();
            sb.append(str1);
        }
        return sb.toString();
    }
    public  String getOne(){
        Random r=new Random();
        int index=r.nextInt(10);
        return index+"";
    }
}
