package com.example.tangyi.jokes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.tangyi.jokes.Tools.SharedPreferencesUtils;

import net.youmi.android.AdManager;


/**
 * 启动页Activity
 * Created by 在阳光下唱歌 on 2016/5/9.
 */
public class SplashActivity extends Activity {
    private static final String APP_ID="7e8c74720c9fee12";
    private static final String APP_SECRET="4db7b7071fb84b9d";
    private RelativeLayout splashLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AdManager.getInstance(this).
                init(APP_ID, APP_SECRET,false);
        splashLayout=(RelativeLayout)findViewById(R.id.splash_layout);
        initAnimation();
    }
    //初始化动画效果。
    private void initAnimation(){
        Animation animation= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.alpha_scale);
        animation.setFillAfter(true);
        splashLayout.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //利用SharedPreferencesUtils工具类的静态函数获取是否是第一进入APP，如果为true，就跳转到新手页面。反之跳转到主页面。
                Boolean isFirstEnter= SharedPreferencesUtils.getBoolean(SplashActivity.this,"is_first_enter",true);
                Intent intent;
                if (isFirstEnter){
                    intent=new Intent(SplashActivity.this,GuideActivity.class);
                }else {
                    intent=new Intent(SplashActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();//关闭当前页面
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
