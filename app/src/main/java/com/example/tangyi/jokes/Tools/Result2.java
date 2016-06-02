package com.example.tangyi.jokes.Tools;

import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by 在阳光下唱歌 on 2016/5/24.
 */
public class Result2 {
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


    }
}
