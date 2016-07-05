package com.example.tangyi.jokes.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tangyi.jokes.Bean.JsonBean;
import com.example.tangyi.jokes.R;
import com.example.tangyi.jokes.Tools.ViewHolder;

import java.util.ArrayList;

/**
 * 聚合数据资源的ListView适配器
 */
public class ListAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<JsonBean.Result.Data> jokesDataList;
    private JsonBean.Result.Data data;
    public ListAdapter(Activity mActivity,ArrayList<JsonBean.Result.Data> jokesDataList,JsonBean.Result.Data data){
        this.mActivity=mActivity;
        this.jokesDataList=jokesDataList;
        this.data=data;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=View.inflate(mActivity, R.layout.jokes_list_item,null);
            holder=new ViewHolder();
            holder.contentText=(TextView)convertView.findViewById(R.id.content_text);
            holder.timerText=(TextView)convertView.findViewById(R.id.timer_text);
            holder.jpgImg =(ImageView)convertView.findViewById(R.id.content_image_jpg);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        data = (JsonBean.Result.Data)getItem(position);
        holder.contentText.setText(data.getContent());
        holder.timerText.setText(data.getUpdatetime());
        Glide.with(mActivity)
                .load(data.getUrl())
                .into(holder.jpgImg);
        return convertView;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Object getItem(int position) {
        return jokesDataList.get(position);
    }
    @Override
    public int getCount() {
        return jokesDataList.size();
    }
}
