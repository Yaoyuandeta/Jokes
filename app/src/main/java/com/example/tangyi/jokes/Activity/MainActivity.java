package com.example.tangyi.jokes.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.tangyi.jokes.Fragment.ContentFragment;
import com.example.tangyi.jokes.Fragment.OtherContentFragment;
import com.example.tangyi.jokes.R;


/**
 *
 */
public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener{
    private DrawerLayout drawerLayout;
    private LinearLayout left;
    private RadioGroup mRadioGroup;
    private ContentFragment contentFragment;
    private OtherContentFragment otherContentFragment;
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
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_frame,contentFragment);
        transaction.commit();

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
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (checkedId){
            case R.id.radio_button1:
                if (contentFragment==null){
                    contentFragment=new ContentFragment();
                }
                transaction.replace(R.id.content_frame,contentFragment);
                transaction.commit();
                break;
            case R.id.radio_button2:
                if (otherContentFragment==null){
                    otherContentFragment=new OtherContentFragment();
                }
                transaction.replace(R.id.content_frame,otherContentFragment);
                transaction.commit();
                break;
            default:
                break;
        }
    }
}
