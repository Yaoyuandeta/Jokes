package com.example.tangyi.jokes.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.tangyi.jokes.Fragment.ContentFragment;
import com.example.tangyi.jokes.Fragment.OtherContentFragment;
import com.example.tangyi.jokes.R;
import com.nineoldandroids.view.ViewHelper;


/**
 *
 */
public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener{
    private DrawerLayout drawerLayout;
    private LinearLayout left;
    private RadioGroup mRadioGroup;
    private ContentFragment contentFragment;
    private OtherContentFragment otherContentFragment;
    private FragmentManager fm;
    private OtherContentFragment mContent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initData();

        setDefaultFragment();
    }
    //控件实例化
    private void initData(){
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        left=(LinearLayout)findViewById(R.id.drawer_fragment);


        mRadioGroup=(RadioGroup)findViewById(R.id.radio_group);
        mRadioGroup.setOnCheckedChangeListener(this);
    }
    //设置默认的Fragment
    private void setDefaultFragment()
    {
        /**
         *动态加载Fragment：
         * 1.创建需要动态加载的Fragment对象
         * 2.获取FragmentManager，即Fragment管理器。
         * 3.开启事务。通过调用FragmentManager的beginTransaction()开启。
         * 4.FragmentTransaction调用replace()方法动态加载Fragment。需要传入被替换的控件ID，以及需要加载的Fragment实例。
         * 5.提交事务。
         */
        contentFragment=new ContentFragment();
        fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_frame,contentFragment);
        transaction.commit();



        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState)
            {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                View mContent =drawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;



                    float leftScale = 1 - 0.3f * scale;

                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent,
                            mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);


            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                drawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }


    public void switchContent(Fragment from,Fragment to) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (!to.isAdded()) { // 先判断是否被add过
            transaction.hide(from).add(R.id.content_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }



    //标题栏侧滑按钮监听函数
    public void stClick(View v){
        if (drawerLayout != null &&left!= null){
            drawerLayout.openDrawer(left);
        }
    }
    //底部Title的Fragment的RadioGroup监听回调
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_button1:
                if (contentFragment==null){
                    contentFragment=new ContentFragment();
                    Log.d("TAG","content:==null");
                }
                switchContent(otherContentFragment,contentFragment);
                break;
            case R.id.radio_button2:
                if (otherContentFragment==null){
                    otherContentFragment=new OtherContentFragment();
                    Log.d("TAG","othercontent:==null");
                    FragmentTransaction transaction=fm.beginTransaction();
                    transaction.replace(R.id.content_frame,otherContentFragment);
                    transaction.commit();
                }else {
                    switchContent(contentFragment, otherContentFragment);
                }
                break;
            default:
                break;
        }
    }

}
