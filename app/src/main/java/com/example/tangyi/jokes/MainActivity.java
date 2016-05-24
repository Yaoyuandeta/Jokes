package com.example.tangyi.jokes;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.tangyi.jokes.Tools.ContentFragment;
import com.example.tangyi.jokes.Tools.MyFragment;


/**
 *
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private View view;
    private Boolean bl;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化数据
        initFragment();
        initSettingLayout();


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
        Toast.makeText(MainActivity.this,"onClick执行", Toast.LENGTH_SHORT).show();
    }
    private void initSettingLayout(){
    }


}
