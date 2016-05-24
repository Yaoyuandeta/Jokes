package com.example.tangyi.jokes.Tools;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 主页上的自定义ViewPager
 * 为了防止ViewPager的滑动与侧边栏滑动产生冲突。
 * 在重写的函数中禁用滑动即可。
 */
public class HomeViewPager extends ViewPager {
    //这是自定义ViewPager必须重写的构造方法。
    public HomeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //在此函数中，返回true即可禁止ViewPager的滑动。
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
