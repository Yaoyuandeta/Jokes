package com.example.tangyi.jokes.Tools;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tangyi.jokes.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.TabPageIndicator;


import java.util.ArrayList;
import java.util.List;

/**
 * 侧边栏的Fragment
 */
public class ContentLayout extends LinearLayout implements ViewPager.OnPageChangeListener{
    private static final String[] TITLE=new String[]{"最新","段子","趣图","随机"};
    private TabPageIndicator mIndicator;
    //装载Fragment的View
    private View view1,view2,view3,view4;
    //主页上的ViewPager;
    private ViewPager viewPager;
    //存储两个布局文件的List集合。
    private List<View> list;

    //主页的文字ListView;
    private MyListView textList1,textList2,textList3,textList4;

    //ListView附页适配器
    //声明文字页下拉刷新控件
    private SwipeRefreshLayout swipeLayout1,swipeLayout2,swipeLayout3,swipeLayout4;
    //API page页数字段值
    private int page=2;
    private int page2=2;

    private String s;
    private String s1;

    public ContentLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.content_fragment,this);
        viewPager =(ViewPager)findViewById(R.id.viewpager_home);


    }








    /**
     * 当加载更多数据时，就调用此方法
     * 请求成功后，在onSuccess()方法中调用processData()方法进行数据解析时，第二个参数传入的是true
     * 就会执行该方法中if语句中的else语句。
     */
    private void getMoreDataFromServer(){
        HttpUtils utils=new HttpUtils();
        switch (viewPager.getCurrentItem()) {
            case 0:
            //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
            utils.send(HttpRequest.HttpMethod.GET, URLList.CATEGORY_URL1 + page + URLList.CATEGORY_URL2,
                    new RequestCallBack<String>() {

                        //请求成功
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                           // processData(result, true);
                        }

                        //请求失败
                        @Override
                        public void onFailure(HttpException e, String s) {
                            e.printStackTrace();
                        }


                    });
                break;
            case 1:
                if (s1!=null){
                    //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
                    utils.send(HttpRequest.HttpMethod.GET,URLList.TWO_CATEGORY_URL1+page2+URLList.TWO_CATEGORY_URL2+s1
                            , new RequestCallBack<String>() {

                                //请求成功
                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String result=responseInfo.result;
                                  //  processData(result,true);
                                }

                                //请求失败
                                @Override
                                public void onFailure(HttpException e, String s) {
                                    e.printStackTrace();
                                }



                            });
                }
                break;
            default:
                break;

        }

    }










    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //getDataFromServer();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

