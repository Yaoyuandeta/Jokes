package com.example.tangyi.jokes.Tools;

/**
 * Created by 在阳光下唱歌 on 2016/5/18.
 */
public class URLList {
    public static final String APPKEY="3acdc878a9d5f3902d97bb81a3d5a9b0";
    //聚合最新文字
    public static final String CATEGORY_URL1="http://japi.juhe.cn/joke/content/text.from?key="+APPKEY+"&page=";
    public static final String CATEGORY_URL2="&pagesize=20";
    //聚合随机文字
    public static final String TWO_CATEGORY_URL1="http://japi.juhe.cn/joke/content/list.from?key="+APPKEY+"&page=";
    public static final String TWO_CATEGORY_URL2="&pagesize=20&sort=desc&time=";
    //聚合最新趣图
    public static final String IMAGE_URL1="http://japi.juhe.cn/joke/img/text.from?key="+APPKEY+"&page=";
    public static final String IMAGE_URL2="&pagesize=20";
    //聚合随机趣图
    public static final String TWO_IMAGE_URL1="http://japi.juhe.cn/joke/img/list.from?key="+APPKEY+"&page=";
    public static final String TWO_IMAGE_URL2="&pagesize=20&sort=DESC&time=";
    //百度文字
    public static final String APISTORE_TEXT_URL="http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text?page=";
    public static final String APISTORE_KEY="70cb8b0073d1e7ed81634214d5e2ea5e";
    //百度趣图
    public static final String APISTORE_IMAGE_URL="http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_pic?page=";
}
