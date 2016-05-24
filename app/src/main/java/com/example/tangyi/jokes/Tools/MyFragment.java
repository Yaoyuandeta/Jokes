package com.example.tangyi.jokes.Tools;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tangyi.jokes.R;

/**
 * Created by 在阳光下唱歌 on 2016/5/11.
 */
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.setting_layout,null);
        return view;
    }
}
