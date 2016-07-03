package com.example.tangyi.jokes.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.tangyi.jokes.R;


/**
 *
 */
public class MainActivity extends Activity {
    //主页面的侧滑控件（Google官方控件）
    private DrawerLayout drawerLayout;
    private LinearLayout left;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        left=(LinearLayout)findViewById(R.id.drawer_fragment);
    }
    //侧滑菜单监听
    public void stClick(View v){
        if (drawerLayout != null &&left!= null){
            drawerLayout.openDrawer(left);
        }
    }


}
