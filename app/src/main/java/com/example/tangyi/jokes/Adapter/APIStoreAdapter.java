package com.example.tangyi.jokes.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tangyi.jokes.Bean.APIStoreBean;
import com.example.tangyi.jokes.R;
import com.example.tangyi.jokes.Tools.ViewHolder;

import java.util.ArrayList;

/**
 * Created by 在阳光下唱歌 on 2016/7/5.
 */
public class APIStoreAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<APIStoreBean.ShowApiResBody.Content> APIDataList;
    private APIStoreBean.ShowApiResBody.Content APIData;
    public APIStoreAdapter(Activity mActivity,ArrayList<APIStoreBean.ShowApiResBody.Content> APIDataList,
                           APIStoreBean.ShowApiResBody.Content APIData){
        this.mActivity=mActivity;
        this.APIDataList=APIDataList;
        this.APIData=APIData;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=View.inflate(mActivity, R.layout.jokes_list_item,null);
            holder=new ViewHolder();
            holder.contentText=(TextView)convertView.findViewById(R.id.content_text);
            holder.timerText=(TextView)convertView.findViewById(R.id.timer_text);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        APIData = (APIStoreBean.ShowApiResBody.Content)getItem(position);
        holder.contentText.setText(APIData.getText());
        holder.timerText.setText(APIData.getCt());
        return convertView;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Object getItem(int position) {
        return APIDataList.get(position);
    }
    @Override
    public int getCount() {
        return APIDataList.size();
    }

}
