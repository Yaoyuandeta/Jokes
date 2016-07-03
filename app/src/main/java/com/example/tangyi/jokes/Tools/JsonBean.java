package com.example.tangyi.jokes.Tools;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Gson解析需要的数据类
 * 数据类的创建原则：逢{}创建对象，逢[]创建集合(一般情况下使用ArrayList)
 * 所有字段名称必须要和Json返回的字段一致。
 */
public class JsonBean {
    private Result result;
    public void setResult(Result result){
        this.result=result;
    }
    public Result getResult(){
        return result;
    }
    public class Result {
        private ArrayList<Data> data;
        public void  setData(ArrayList<Data> data){
            this.data=data;
        }
        public ArrayList<Data> getData(){
            return data;
        }

        public class Data{
            private String content;
            private String updatetime;
            private String url;
            public void setContent(String content){
                this.content=content;
            }
            public String getContent(){
                return content;
            }
            public void setUpdatetime(String updatetime){
                this.updatetime=updatetime;
            }
            public String getUpdatetime(){
                return updatetime;
            }
            public void setUrl(String url){
                this.url=url;
            }
            public String getUrl(){
                return url;
            }
        }
    }
}
