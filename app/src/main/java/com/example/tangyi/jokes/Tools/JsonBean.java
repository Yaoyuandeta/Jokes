package com.example.tangyi.jokes.Tools;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Gson解析需要的数据类
 * 数据类的创建原则：逢{}创建对象，逢[]创建集合(一般情况下使用ArrayList)
 * 所有字段名称必须要和Json返回的字段一致。
 */
public class JsonBean {
    private int error_code;
    private String reason;
    private Result result;
    public void setError_code(int error_code){
        this.error_code=error_code;
    }
    public int getError_code(){
        return error_code;
    }
    public void setReason(String reason){
        this.reason=reason;
    }
    public String getReason(){
        return reason;
    }
    public void setResult(Result result){
        this.result=result;
    }
    public Result getResult(){
        return result;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "result=" + result +
                '}';
    }
}
