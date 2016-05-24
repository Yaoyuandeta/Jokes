package com.example.tangyi.jokes.Tools;

/**
 * Created by 在阳光下唱歌 on 2016/5/24.
 */
public class JsonImageBean {
    private Result2 result;
    public void setResult(Result2 result){
        this.result=result;
    }
    public Result2 getResult(){
        return result;
    }

    @Override
    public String toString() {
        return "JsonImageBean{" +
                "result=" + result +
                '}';
    }
}
