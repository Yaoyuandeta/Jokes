package com.example.tangyi.jokes;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.tangyi.jokes.Tools.ContentFragment;


/**
 *
 */
public class MainActivity extends AppCompatActivity {
    //主页面的侧滑控件（Google官方控件）
    private DrawerLayout drawerLayout;
    private ScrollView mScrollView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化数据
        initFragment();
        initSettingLayout();
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mScrollView=(ScrollView)findViewById(R.id.drawer_scroll);
    }
    private void initFragment(){

        FragmentManager fm=getSupportFragmentManager();
        //开启FragmentManager的事务
        FragmentTransaction fmTransaction=fm.beginTransaction();
        //利用事务机制替换掉主页面内容的布局文件。不直接在布局文件中写入控件是为了解耦。
        fmTransaction.replace(R.id.content_frameLayout,new ContentFragment());
        //开启事务。
        fmTransaction.commit();
        //禁用DrawerLayout的滑动
    }
    public void stClick(View v){
        if (drawerLayout != null && mScrollView != null){
            drawerLayout.openDrawer(mScrollView);
        }
    }
    private void initSettingLayout(){
    }
}
