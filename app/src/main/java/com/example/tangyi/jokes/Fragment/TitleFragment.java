package com.example.tangyi.jokes.Fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tangyi.jokes.R;

/**
 * Created by 在阳光下唱歌 on 2016/7/3.
 */
public class TitleFragment extends Fragment {
    private RadioGroup radioGroup;
    private RadioButton radioButton1,radioButton2;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.titie_fragment_home,container,false);
        radioGroup=(RadioGroup)view.findViewById(R.id.radio_group);
        radioButton1=(RadioButton)view.findViewById(R.id.radio_button1);
        radioButton2=(RadioButton)view.findViewById(R.id.radio_button2);

        //初始化RadioButton图片大小
        initRadioButton();
        return view;
    }
    //初始化RadioButton图片大小
    private void initRadioButton(){
        Drawable jokeDrawable=getResources().getDrawable(R.drawable.tab_joke_selector,null);
        //第一0是距左右边距离，第二0是距上下边距离，第三长度,第四宽度
        jokeDrawable.setBounds(0,0,80,80);
        //设置到第二个参数意为将图片放上面，放入的参数位置不同，图片的摆放位置不同。也可以四个参数全部为null，当然这样的结果就是没有图片。
        radioButton1.setCompoundDrawables(null,jokeDrawable, null, null);

        Drawable photoDrawable= getResources().getDrawable(R.drawable.tab_photo_selector,null);
        photoDrawable.setBounds(0, 0,80,80);
        radioButton2.setCompoundDrawables(null,photoDrawable, null,null);
        radioGroup.check(R.id.radio_button1);
    }
}
