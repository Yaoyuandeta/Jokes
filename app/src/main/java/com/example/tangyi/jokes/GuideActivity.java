package com.example.tangyi.jokes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tangyi.jokes.Tools.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 在阳光下唱歌 on 2016/5/9.
 */
//初次进入的引导页界面。
public class GuideActivity extends Activity {
    private Button guideButton;
    private ViewPager guideViewPager;
    private View view1,view2,view3;
    private List<View> list;
    //含有三个小灰点的LinearLayout。
    private LinearLayout pointGrayLayout;
    //小红点.
    private ImageView pointRed;
    //小红点从页面1到页面2的总移动距离。
    private int pointRedDis;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guideViewPager=(ViewPager)findViewById(R.id.guide_viewpager);
        //小红点
        pointRed=(ImageView)findViewById(R.id.point_red);
        initData();

        //设置ViewPager的滑动监听。
        guideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**页面滑动过程中的回调。参数分别为：当前位置(从0开始，比如第一个页面是0，第二个页面上是1，以此类推。)
             * 第二个参数为移动的偏移量，以百分比为单位。需要注意的是，当第一个页面偏移到第二个页面上时，百分比不会为100，而是重置为0.
             * 第三个参数为具体的偏移量，单位不详，貌似是dp。在这里这个参数不重要。
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /**更新小红点的距离。
                 * 已经得知小红点总的移动距离，再乘以页面滑动距离的百分比就能知道回调此函数时需要移动的具体距离值。
                 * 但是由于每次换到下一页面时，百分比就会被清零。
                 * 所以移动的距离应该是当前页面的滑动距离加上滑动的页面数乘更换页面的移动总距离。
                 * (滑动了一个界面就是一个总距离，两个界面就是两个总距离，0个界面就为0。)
                 * 这个每次需要移动的具体距离值重新定义变量为leftMargin。
                 */
                int leftMargin=(int)(pointRedDis*positionOffset)+position*pointRedDis;
                //这段代码相当于是小红点在获取他自己的坐标集合，这个坐标集合定义给变量params。
                RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)pointRed.getLayoutParams();
                //目前为止，相对于父控件而言，小红点的左外边距为0，将移动的距离赋值给params。
                params.leftMargin=leftMargin;
                //重新设定小红点的坐标集合，更新小红点的距离。
                pointRed.setLayoutParams(params);

            }
            //某个页面被选中时的回调。
            @Override
            public void onPageSelected(int position) {

            }
            //页面状态发生改变时的回调。(停止变为滑动，滑动变为停止。这就叫做状态改变。)
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //包含有三个小灰点的线性布局。
        pointGrayLayout=(LinearLayout)findViewById(R.id.pointgray_layout);

        //暂不说明，需要了解的知识点。
        pointRed.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //移除监听，避免重复回调。
                pointRed.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //小红点总的移动距离=第二个小灰点的left值-第一个小灰点的left值。(注意：这是页面1到页面2的总距离。)
                pointRedDis=pointGrayLayout.getChildAt(1).getLeft()-pointGrayLayout.getChildAt(0).getLeft();
            }
        });
    }
    //初始化数据
    private void initData(){
        LayoutInflater layoutInflater=getLayoutInflater();
        view1=layoutInflater.inflate(R.layout.viewpager_guide1,null);
        view2=layoutInflater.inflate(R.layout.viewpager_guide2,null);
        view3=layoutInflater.inflate(R.layout.viewpager_guide3,null);
        guideButton=(Button)view3.findViewById(R.id.guide_button);
        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.setBoolean(GuideActivity.this,"is_first_enter",false);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });
        list=new ArrayList<View>();
        list.add(view1);
        list.add(view2);
        list.add(view3);
        guideViewPager.setAdapter(new PagerAdapter() {
            //ViewPager要展示的页面个数。
            @Override
            public int getCount() {
                return list.size();
            }

            //销毁Item。
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }

            //初始化Item布局。
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
    }
}
