package com.example.tangyi.jokes.Tools;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tangyi.jokes.Activity.AboutActivity;
import com.example.tangyi.jokes.R;

/**
 * Created by 在阳光下唱歌 on 2016/5/11.
 */
public class MyFragment extends Fragment {
    private LinearLayout aboutLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.setting_layout,null);
        aboutLayout=(LinearLayout)view.findViewById(R.id.about_layout);
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AboutActivity.class));
            }
        });
        return view;
    }
}
