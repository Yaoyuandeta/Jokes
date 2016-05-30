package com.example.tangyi.jokes.Tools;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tangyi.jokes.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * 侧边栏的Fragment
 */
public class ContentFragment extends Fragment {
    private View view;
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
    private MyListView homeList;
    //主页的图片ListView
    private MyListView imageList;
    //Json数据对象
    private ArrayList<Result.Data> jokesDataList;
    //Json图片数据对象
    private ArrayList<Result2.Image> imageDataList;
    //ListView适配器
    private HomeListAdapter listAdapter;
    //ListView图片适配器
    private PhotoListAdapter photoAdapter;
    //声明文字页下拉刷新控件
    private SwipeRefreshLayout swipeLayout;
    //声明图片页下拉刷新控件
    private SwipeRefreshLayout photoSwipe;
    //API page页数字段值
    private int page=1;
    private Result.Data data;
    //下一页的数据链接
    private String mMoreUrl;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_content,null);
        homeViewPager=(HomeViewPager)view.findViewById(R.id.viewpager_home);
        rg=(RadioGroup)view.findViewById(R.id.radio_main);


        //初始化ViewPager的函数。
        initViewPagerData();
        initView();
        return view;
    }
    //加载下一页数据
    private void getMoreDataFromServer(){
        HttpUtils utils=new HttpUtils();
        for (page=2;page<50;page++) {
            //mMoreUrl=URLList.CATEGORY_URL1+page+URLList.CATEGORY_URL2;
            utils.send(HttpRequest.HttpMethod.GET,URLList.CATEGORY_URL1+page+URLList.CATEGORY_URL2,
                    //请求的是什么内容，泛型就写入相对应的数据类型。
                    new RequestCallBack<String>() {

                        //请求成功
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result=responseInfo.result;
                            processData(result);
                        }

                        //请求失败
                        @Override
                        public void onFailure(HttpException e, String s) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                        }



                    });
        }
        //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，

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
        //ListView在View1的布局文件中，就必须在该布局文件中寻找Id并实例化，否则会空指针异常。
        homeList=(MyListView)view1.findViewById(R.id.home_list);
        homeList.setOnRefreshListener(new MyListView.OnRefreshListener() {
            @Override
            public void onLoadMore() {

                    getMoreDataFromServer();

            }
        });

        //图片List实例化
        imageList=(MyListView)view2.findViewById(R.id.image_list);
        //图片页下拉刷新
        photoSwipe=(SwipeRefreshLayout)view2.findViewById(R.id.swiperefresh_layout2);
        photoSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromImageServer();
                photoSwipe.setRefreshing(false);
            }
        });
        photoSwipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
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
                        break;
                    default:
                        break;
                }

            }
        });
        //初始化文字信息方法
        getDataFromServer();
        getDataFromImageServer();
    }

    //利用xUtils框架请求数据。
    private void getDataFromServer(){
        HttpUtils utils=new HttpUtils();
        //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
        utils.send(HttpRequest.HttpMethod.GET, URLList.CATEGORY_URL1+page+URLList.CATEGORY_URL2,
                //请求的是什么内容，泛型就写入相对应的数据类型。
                new RequestCallBack<String>() {


                    //请求成功
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        processData(result);
                    }

                    //请求失败
                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                    }

                });
    }
    //使用Gson解析Json数据
    private void processData(String json){

            Gson gson = new Gson();
            /**
             * gson.fromJson()函数意思是将Json数据转换为JAVA对象。
             * Json数据中字段对应的内容就是JAVA对象中字段内所存储的内容。
             */
            JsonBean fromJson = gson.fromJson(json, JsonBean.class);
            //定义Json数据对象。
            jokesDataList = fromJson.getResult().getData();

            if (data!=null){
                page++;
                mMoreUrl = URLList.CATEGORY_URL1+page+URLList.CATEGORY_URL2;
            }else{
                mMoreUrl=null;
            }

            listAdapter = new HomeListAdapter();
            //当数据对象不为null时，也就是里面有数据时，设置适配器用于填充进ListView.
            if (jokesDataList!=null) {
                    homeList.setAdapter(listAdapter);
            }
    }
    //填充ListView的适配器。
    public class HomeListAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView=View.inflate(getActivity(),R.layout.joke_list_item,null);
                holder=new ViewHolder();
                holder.contentText=(TextView)convertView.findViewById(R.id.content_text);
                holder.timerText=(TextView)convertView.findViewById(R.id.timer_text);
                convertView.setTag(holder);
            }else {
                holder=(ViewHolder)convertView.getTag();
            }
            data = (Result.Data)getItem(position);
            holder.contentText.setText("        "+ data.getContent());
            holder.timerText.setText("更新时间："+ data.getUpdatetime());
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
        class ViewHolder{
            public TextView contentText;
            public TextView timerText;
        }
    }

    private void getDataFromImageServer(){
        HttpUtils utils=new HttpUtils();
        //send就是发送请求。参数一代表获取数据。参数二是请求API的地址，
        utils.send(HttpRequest.HttpMethod.GET, URLList.IMAGECATEGORY_URL,
                //请求的是什么内容，泛型就写入相对应的数据类型。
                new RequestCallBack<String>() {
                    //请求失败
                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                    }

                    //请求成功
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result=responseInfo.result;
                        //processImageData(result);
                    }
                });
    }
    private void processImageData(String json){
        Gson gson = new Gson();
        /**
         * gson.fromJson()函数意思是将Json数据转换为JAVA对象。
         * Json数据中字段对应的内容就是JAVA对象中字段内所存储的内容。
         */
        JsonImageBean imageJson = gson.fromJson(json, JsonImageBean.class);
        //定义Json数据对象。
        imageDataList =imageJson.getResult().getData();
        photoAdapter = new PhotoListAdapter();
        //当数据对象不为null时，也就是里面有数据时，设置适配器用于填充进ListView.
        if (imageDataList!=null) {
            imageList.setAdapter(photoAdapter);
        }
        Log.d("TAG","imageJson"+imageJson);
    }
    public class PhotoListAdapter extends BaseAdapter {
        //Xuitls框架里的用于填充图片的方法类
        private BitmapUtils mBitmapUtils;
        public PhotoListAdapter(){
            //在适配器的构造函数中新建BitmapUtils类，构造函数中传入所依附的Activity
            mBitmapUtils=new BitmapUtils(getActivity());
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView==null){
                convertView=View.inflate(getActivity(),R.layout.photo_list_item,null);
                holder=new ViewHolder();
                holder.contentText=(TextView)convertView.findViewById(R.id.photo_content_text);
                holder.gifImage=(GifImageView)convertView.findViewById(R.id.url_gifimg);
                convertView.setTag(holder);
            }else {
                holder=(ViewHolder)convertView.getTag();
            }
            Result2.Image image=(Result2.Image)getItem(position);
            holder.contentText.setText(image.getContent());
            //通过”趣图“的Json对象的getUrl()函数可以得到图片链接的Url，赋值给imgUrl.
            String imgUrl=image.getUrl();
            /**
             *  图片填充机制为下载图片—将图片设置给ImageView。
             *  BitmapUtils的display()方法传入需要填充的ImageView和图片的下载链接
             *  BitmapUtils底层封装好了这两步，只需将两个参数传入即可完成填充。
             */

            mBitmapUtils.display(holder.gifImage,imgUrl);
            return convertView;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public Object getItem(int position) {
            return imageDataList.get(position);
        }
        @Override
        public int getCount() {
            return imageDataList.size();
        }
        class ViewHolder{
            public TextView contentText;
            public GifImageView gifImage;
        }
    }
}

