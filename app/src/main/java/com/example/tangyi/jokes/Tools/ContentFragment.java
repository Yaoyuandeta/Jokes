package com.example.tangyi.jokes.Tools;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangyi.jokes.R;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧边栏的Fragment
 */
public class ContentFragment extends Fragment {
    //装载Fragment的View
    private View view;
    //主页面的RadioGroup和RadioButton
    private RadioGroup radioGroup;
    private RadioButton radioButton1,radioButton2;
    //主页上的ViewPager;
    private HomeViewPager homeViewPager;
    //装载ViewPager的两个View，也就是两个布局文件。
    private View view1,view2;
    //存储两个布局文件的List集合。
    private List<View> list;
    //主页底栏下的RadioGroup。
    private RadioGroup rg;
    //主页的文字ListView;
    private MyListView  textList;
    //附页的ListView
    private MyListView twoTextList;
    //Json数据对象
    private ArrayList<Result.Data> jokesDataList;
    private ArrayList<Result2.Data> twoJokesDataList;
    //ListView适配器
    private TextListAdapter listAdapter;
    //ListView附页适配器
    private TwoListAdapter twoListAdapter;
    //声明文字页下拉刷新控件
    private SwipeRefreshLayout swipeLayout;
    //声明附页下拉刷新控件
    private SwipeRefreshLayout twoSwipe;
    //API page页数字段值
    private int page=2;
    private int page2=2;
    private Result.Data data;
    private Result2.Data data2;
    private TimeStamp timeStamp=new TimeStamp();
    private String s;
    private String s1;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_content,null);
        homeViewPager=(HomeViewPager)view.findViewById(R.id.viewpager_home);
        rg=(RadioGroup)view.findViewById(R.id.radio_main);

        AdView adView=new AdView(getActivity(), AdSize.FIT_SCREEN);
        LinearLayout adLayout=(LinearLayout)view.findViewById(R.id.adLayout);
        adLayout.addView(adView);

        //初始化ViewPager的函数。
        initViewPagerData();
        initView();
        return view;
    }

    /**
     * 当加载更多数据时，就调用此方法
     * 请求成功后，在onSuccess()方法中调用processData()方法进行数据解析时，第二个参数传入的是true
     * 就会执行该方法中if语句中的else语句。
     */
    private void getMoreDataFromServer(){
        HttpUtils utils=new HttpUtils();
        //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
        utils.send(HttpRequest.HttpMethod.GET,URLList.CATEGORY_URL1+page+URLList.CATEGORY_URL2,
                    new RequestCallBack<String>() {

                        //请求成功
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result=responseInfo.result;
                            processData(result,true);
                        }

                        //请求失败
                        @Override
                        public void onFailure(HttpException e, String s) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                        }



                    });

    }
    private void getMoreTwoFromServer(){
        s1 = timeStamp.getTimeStamp(8);
        if (s1!=null){
            HttpUtils utils=new HttpUtils();
            if (s1!=null){
                //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
                utils.send(HttpRequest.HttpMethod.GET,URLList.TWO_CATEGORY_URL1+page2+URLList.TWO_CATEGORY_URL2+s1
                        , new RequestCallBack<String>() {

                            //请求成功
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                String result=responseInfo.result;
                                processTwoData(result,true);
                            }

                            //请求失败
                            @Override
                            public void onFailure(HttpException e, String s) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                            }



                        });
            }
        }



    }
    //此函数用于设置RadioButton的自定义图片的大小
    private void initView(){
        radioGroup = (RadioGroup)view.findViewById(R.id.radio_main);
        radioButton1 = (RadioButton)view.findViewById(R.id.radio_button1);
        radioButton2 = (RadioButton)view.findViewById(R.id.radio_button2);



        //定义底部标签图片大小
        Drawable jokeDrawable=getResources().getDrawable(R.drawable.tab_joke_selector,null);
        //第一0是距左右边距离，第二0是距上下边距离，第三长度,第四宽度
        jokeDrawable.setBounds(0,0,50,50);
        //设置到第二个参数意为将图片放上面，放入的参数位置不同，图片的摆放位置不同。也可以四个参数全部为null，当然这样的结果就是没有图片。
        radioButton1.setCompoundDrawables(null,jokeDrawable, null, null);

        Drawable photoDrawable= getResources().getDrawable(R.drawable.tab_photo_selector,null);
        photoDrawable.setBounds(0, 0,50,50);
        radioButton2.setCompoundDrawables(null,photoDrawable, null,null);


        //初始化底部标签
        radioGroup.check(R.id.radio_button1);// 默认勾选首页，初始化时候让首页默认勾选
    }
    //初始化主页ViewPager的数据
    private void initViewPagerData(){
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        view1=layoutInflater.inflate(R.layout.viewpager_home1,null);
        view2=layoutInflater.inflate(R.layout.viewpager_home2,null);
        //实例化文字页SwipeRefreshLayout.(Google官方下拉刷新控件)
        swipeLayout=(SwipeRefreshLayout)view1.findViewById(R.id.swiperefresh_layout);
        //下拉监听器，刷新回调函数（一般用于更新UI）
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
                //设置刷新完成，进度圆圈圈停止转动。
                swipeLayout.setRefreshing(false);
            }
        });
        //设置刷新小圆圈的进度颜色，旋转一圈即随机更改颜色。
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新，200差不多了。其实默认的距离也很合适。
        swipeLayout.setDistanceToTriggerSync(200);
        //设置刷新控件的大小，这是默认选项。
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //ListView在View1的布局文件中，就必须在该布局文件中寻找Id并实例化，否则会空指针异常。
        textList=(MyListView)view1.findViewById(R.id.home_list);
        //上拉加载监听
        textList.setOnRefreshListener(new MyListView.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                getMoreDataFromServer();
                if (data!=null) {
                    page++;
                }else{
                    page=2;
                    Toast.makeText(getContext(),"没有更多数据！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //附页List实例化
        twoTextList=(MyListView)view2.findViewById(R.id.two_list);
        twoTextList.setOnRefreshListener(new MyListView.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                getMoreTwoFromServer();
                if (data2!=null) {
                    page2++;
                }else{
                    page2=2;
                    Toast.makeText(getContext(),"没有更多数据！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //附页下拉刷新
        twoSwipe=(SwipeRefreshLayout)view2.findViewById(R.id.swiperefresh_layout2);
        twoSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromTwoServer();
               twoSwipe.setRefreshing(false);
            }
        });
        twoSwipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        list=new ArrayList<>();
        list.add(view1);
        list.add(view2);
        homeViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
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
        //设置RadioGroup的监听，点击到那个按钮时，ViewPager就跳转到哪个页面。
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //第一个参数是当前的RadioGroup对象，第二个参数是当前被选中的RadioButton的ID。
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //判断当前是哪个按钮被选中。
                switch (checkedId){
                    //“笑话”页面
                    case R.id.radio_button1:
                        /**setCurrentItem()方法是切换到指定的View。在ViewPager中，所有的子布局都是从0开始编号进行排序存储的。
                         * 该方法第一个参数就是要切换的View的编号（可以这么理解），第二个参数决定切换时是否带有动画，这里选择的是不带动画。
                         */
                        homeViewPager.setCurrentItem(0,false);
                        break;
                    //“趣图”页面
                    case R.id.radio_button2:
                        homeViewPager.setCurrentItem(1,false);
                        getDataFromTwoServer();
                        break;
                    default:
                        break;
                }

            }
        });

        //初始化文字信息方法
       getDataFromServer();
       //getDataFromTwoServer();
    }

    //利用xUtils框架请求数据。
    private void getDataFromServer(){
        HttpUtils utils=new HttpUtils();
        //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
        utils.send(HttpRequest.HttpMethod.GET,URLList.CATEGORY_URL1+1+URLList.CATEGORY_URL2,
                //请求的是什么内容，泛型就写入相对应的数据类型。
                new RequestCallBack<String>() {
                    //请求成功
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        processData(result,false);
                    }
                    //请求失败
                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                    }

                });

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

                listAdapter = new TextListAdapter();
                //当数据对象不为null时，也就是里面有数据时，设置适配器用于填充进ListView.
                if (jokesDataList!=null) {
                    textList.setAdapter(listAdapter);
                }

            }else{
                //加载了更多数据的时候，重新创建Json数据对象
                ArrayList<Result.Data> moreJokesData=fromJson.getResult().getData();
                //追加到第一个数据集合中，进行合并
                jokesDataList.addAll(moreJokesData);
                //刷新ListView
                listAdapter.notifyDataSetChanged();
            }
    }

    //填充ListView的适配器。
    public class TextListAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
             ViewHolder viewHolder;
            if (convertView==null){
                convertView=View.inflate(getActivity(),R.layout.joke_list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.contentText=(TextView)convertView.findViewById(R.id.content_text);
                viewHolder.timerText=(TextView)convertView.findViewById(R.id.timer_text);
                convertView.setTag(viewHolder);
            }else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            data = (Result.Data)getItem(position);
            viewHolder.contentText.setText("        "+ data.getContent());
            viewHolder.timerText.setText("更新时间："+ data.getUpdatetime());
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
    //请求附页数据
    private void getDataFromTwoServer(){
        s = timeStamp.getTimeStamp(8);
        HttpUtils utils = new HttpUtils();
            //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
            utils.send(HttpRequest.HttpMethod.GET, URLList.TWO_CATEGORY_URL1+1+URLList.TWO_CATEGORY_URL2+s
                    //请求的是什么内容，泛型就写入相对应的数据类型。
                    ,new RequestCallBack<String>() {
                        //请求失败
                        @Override
                        public void onFailure(HttpException e, String s) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                        }

                        //请求成功
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                            processTwoData(result, false);
                        }
                    });
        Log.d("TAG",s);


    }
    private void processTwoData(String json,boolean isMore){
        Gson gson = new Gson();
        /**
         * gson.fromJson()函数意思是将Json数据转换为JAVA对象。
         * Json数据中字段对应的内容就是JAVA对象中字段内所存储的内容。
         */
        TwoJsonBean fromJson = gson.fromJson(json, TwoJsonBean.class);

            if (!isMore) {
                //定义Json数据对象。
                twoJokesDataList = fromJson.getResult().getData();
                twoListAdapter = new TwoListAdapter();

                //当数据对象不为null时，也就是里面有数据时，设置适配器用于填充进ListView.
                if (twoJokesDataList != null) {
                    twoTextList.setAdapter(twoListAdapter);
                }
            }else {
                //加载了更多数据的时候，重新创建Json数据对象
                ArrayList<Result2.Data> moreTwoData=fromJson.getResult().getData();
                //追加到第一个数据集合中，进行合并
                twoJokesDataList.addAll(moreTwoData);
                //刷新ListView
                twoListAdapter.notifyDataSetChanged();
            }

    }
    public class TwoListAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView=View.inflate(getActivity(),R.layout.jokes_list_item,null);
                holder=new ViewHolder();
                holder.contentText=(TextView)convertView.findViewById(R.id.two_content_text);
                holder.timerText=(TextView)convertView.findViewById(R.id.two_timer_text);
                convertView.setTag(holder);
            }else {
                holder=(ViewHolder)convertView.getTag();
            }
            data2 = (Result2.Data)getItem(position);
            holder.contentText.setText("        "+ data2.getContent());
            holder.timerText.setText("更新时间："+ data2.getUpdatetime());

            return convertView;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public Object getItem(int position) {
            return twoJokesDataList.get(position);
        }
        @Override
        public int getCount() {
            return twoJokesDataList.size();
        }

    }
}

