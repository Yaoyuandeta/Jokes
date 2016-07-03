package com.example.tangyi.jokes.Fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tangyi.jokes.R;
import com.example.tangyi.jokes.Tools.JsonBean;
import com.example.tangyi.jokes.Tools.MyListView;
import com.example.tangyi.jokes.Tools.TimeStamp;
import com.example.tangyi.jokes.Tools.URLList;
import com.example.tangyi.jokes.Tools.ViewHolder;
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
 * Created by 在阳光下唱歌 on 2016/7/3.
 */
public class ContentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        MyListView.OnRefreshListener,ViewPager.OnPageChangeListener {
    private static final String[] TITLE=new String[]{"最新","随机"};
    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private View view1,view2;
    private List<View> list;
    private SwipeRefreshLayout swipeLayout1,swipeLayout2;
    private MyListView textList1,textList2;
    private int page=2,page2=2;
    private String s,s1;
    //Json数据对象
    private ArrayList<JsonBean.Result.Data> jokesDataList;
    private JsonBean.Result.Data data;

    //ListView适配器
    private ListAdapter listAdapter;
    private TimeStamp timeStamp=new TimeStamp();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_fragment,container,false);
        mIndicator=(TabPageIndicator)view.findViewById(R.id.indicator);
        mViewPager=(ViewPager)view.findViewById(R.id.viewpager_home);
        //初始化ViewPager数据
        initViewPager();
        return view;
    }
    private void initViewPager(){
        view1=LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_home1,null);
        view2=LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_home1,null);
        list = new ArrayList<>();
        list.add(view1);
        list.add(view2);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TITLE[position%TITLE.length];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
        mIndicator.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);

        //实例化文字页SwipeRefreshLayout.(Google官方下拉刷新控件)
        swipeLayout1 =(SwipeRefreshLayout) view1.findViewById(R.id.swiperefresh_layout);
        //下拉监听器，刷新回调函数（一般用于更新UI）
        swipeLayout1.setOnRefreshListener(this);
        //设置刷新圆圈的颜色。
        swipeLayout1.setColorSchemeResources(R.color.jokes_swipe_red);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新，200差不多了。其实默认的距离也很合适。
        swipeLayout1.setDistanceToTriggerSync(200);
        //ListView在View1的布局文件中，就必须在该布局文件中寻找Id并实例化，否则会空指针异常。
        textList1 =(MyListView) view1.findViewById(R.id.home_list);
        //上拉加载监听
        textList1.setOnRefreshListener(this);

        swipeLayout2=(SwipeRefreshLayout) view2.findViewById(R.id.swiperefresh_layout);
        swipeLayout2.setOnRefreshListener(this);
        swipeLayout2.setColorSchemeResources(R.color.jokes_swipe_red);
        swipeLayout2.setDistanceToTriggerSync(200);
        textList2 =(MyListView) view2.findViewById(R.id.home_list);
        textList2.setOnRefreshListener(this);

        //初始化ViewPager数据
        getDataFromServer();
    }

    //利用Xutils框架请求数据
    private void getDataFromServer(){
        HttpUtils utils = new HttpUtils();
        switch (mViewPager.getCurrentItem()) {
            case 0:
                //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
                utils.send(HttpRequest.HttpMethod.GET, URLList.CATEGORY_URL1 + 1 + URLList.CATEGORY_URL2,
                        //请求的是什么内容，泛型就写入相对应的数据类型。
                        new RequestCallBack<String>() {
                            //请求成功
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result = responseInfo.result;
                                processData(result, false);
                                Log.d("TAG","result:"+result);
                            }

                            //请求失败
                            @Override
                            public void onFailure(HttpException e, String s) {
                                e.printStackTrace();
                            }

                        });
                break;
            case 1:
                s = timeStamp.getTimeStamp(8);
                //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
                utils.send(HttpRequest.HttpMethod.GET, URLList.BATE_URL
                        //请求的是什么内容，泛型就写入相对应的数据类型。
                        ,new RequestCallBack<String>() {
                            //请求失败
                            @Override
                            public void onFailure(HttpException e, String s) {
                                e.printStackTrace();
                            }

                            //请求成功
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                s1=s;
                                String result = responseInfo.result;
                                Log.d("TAG","result:"+result);
                                //processData(result, false);
                            }
                        });
                break;

            default:
                break;
        }
    }

    /**
     * processData()函数用于解析Json数据
     * 使用Gson框架解析数据
     * 该方法中第一个参数为服务器传回的数据源，第二个参数作为标记，用于将ListView的数据进行合并，表示更多数据
     * 当isMore为非状态时，也就是不是更多数据的状态时，数据正常显示
     * 否则的话，也就是加载了更多的数据时，将更多数据的Json对象重新创建，并追加到原来的ListView中，进行合并
     */

    private void processData(String json,boolean isMore){

        Gson gson = new Gson();
        /**
         * gson.fromJson()函数意思是将Json数据转换为JAVA对象。
         * Json数据中字段对应的内容就是JAVA对象中字段内所存储的内容。
         */
        JsonBean fromJson = gson.fromJson(json, JsonBean.class);

        if (!isMore){
            //定义Json数据对象。
            jokesDataList = fromJson.getResult().getData();

            listAdapter = new ListAdapter();
            //当数据对象不为null时，也就是里面有数据时，设置适配器用于填充进ListView.
            if (jokesDataList!=null) {
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                        textList1.setAdapter(listAdapter);
                        break;
                    case 1:
                        textList2.setAdapter(listAdapter);
                        break;
                    default:
                        break;
                }
            }

        }else{
            //加载了更多数据的时候，重新创建Json数据对象
            ArrayList<JsonBean.Result.Data> moreJokesData=fromJson.getResult().getData();
            //追加到第一个数据集合中，进行合并
            jokesDataList.addAll(moreJokesData);
            //刷新ListView
            listAdapter.notifyDataSetChanged();
        }
    }


    //适配器
    public class ListAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView=View.inflate(getActivity(),R.layout.jokes_list_item,null);
                holder=new ViewHolder();
                holder.contentText=(TextView)convertView.findViewById(R.id.content_text);
                holder.timerText=(TextView)convertView.findViewById(R.id.timer_text);
                //holder.imageView=(ImageView)convertView.findViewById(R.id.content_image);
                convertView.setTag(holder);
            }else {
                holder=(ViewHolder)convertView.getTag();
            }
            data = (JsonBean.Result.Data)getItem(position);
            holder.contentText.setText(data.getContent());
            holder.timerText.setText(data.getUpdatetime());
           // Glide.with(getContext()).load(data.getUrl()).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imageView);
            return convertView;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public Object getItem(int position) {
            return jokesDataList.get(position);
        }
        @Override
        public int getCount() {
            return jokesDataList.size();
        }

    }
    //下拉刷新回调
    @Override
    public void onRefresh() {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                //getDataFromServer();
                //设置刷新完成，进度圆圈圈停止转动。
                swipeLayout1.setRefreshing(false);
                break;
            case 1:
                //getDataFromServer();
                swipeLayout2.setRefreshing(false);
                break;
            default:
                break;
        }
    }
    private void getMoreDataFromServer(){}
    //上拉加载回调
    @Override
    public void onLoadMore() {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                getMoreDataFromServer();
                if (data != null) {
                    page++;
                } else {
                    page = 2;
                    Toast.makeText(getActivity(), "没有更多数据！", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                getMoreDataFromServer();
                if (data != null) {
                    page2++;
                } else {
                    page2 = 2;
                    Toast.makeText(getActivity(), "没有更多数据！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    //ViewPager滑动回调，onPageScrolled()是滑动结束回调
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
