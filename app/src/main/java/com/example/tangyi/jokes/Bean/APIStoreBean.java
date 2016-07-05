package com.example.tangyi.jokes.Bean;

import java.util.ArrayList;

/**
 * Created by 在阳光下唱歌 on 2016/7/4.
 */
public class APIStoreBean {
    private ShowApiResBody showapi_res_body;
    public void setShowapi_res_body(ShowApiResBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public ShowApiResBody getShowapi_res_body() {

        return showapi_res_body;
    }
    public class ShowApiResBody{
        private ArrayList<Content> contentlist;

        public void setContentlist(ArrayList<Content> contentlist) {
            this.contentlist = contentlist;
        }

        public ArrayList<Content> getContentlist() {

            return contentlist;
        }

        public class Content{
            private String ct;
            private String text;
            private String title;
            private String img;

            public void setImg(String img) {
                this.img = img;
            }

            public void setTitle(String title) {

                this.title = title;
            }

            public String getImg() {

                return img;
            }

            public String getTitle() {

                return title;
            }

            public void setText(String text) {
                this.text = text;
            }

            public void setCt(String ct) {

                this.ct = ct;
            }

            public String getText() {

                return text;
            }

            public String getCt() {

                return ct;
            }
        }

    }


}
