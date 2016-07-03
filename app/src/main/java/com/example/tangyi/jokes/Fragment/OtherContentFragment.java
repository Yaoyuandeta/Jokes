package com.example.tangyi.jokes.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tangyi.jokes.R;

/**
 * Created by 在阳光下唱歌 on 2016/7/4.
 */
public class OtherContentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bate_home,container,false);
        return  view;
    }
}
