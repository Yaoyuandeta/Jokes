package com.example.tangyi.jokes.Tools;

import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by 在阳光下唱歌 on 2016/5/24.
 */
public class Result2 {
    private ArrayList<Image> data;
    public void setData(ArrayList<Image> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result2{" +
                "data=" + data +
                '}';
    }

    public ArrayList<Image> getData() {
        return data;
    }
    public class Image{
        private String content;
        private String url;
        public void setContent(String content) {
            this.content = content;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getContent() {
            return content;
        }
        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Image{" +
                    "content='" + content + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
