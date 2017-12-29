package com.example.littletreemusic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.littletreemusic.R;

import java.util.List;
import java.util.Random;

/**
 * Created by 春风亭lx小树 on 2017/12/26.
 */

public class GridViewAdapter2 extends BaseAdapter{

    private List<String> tags;
    private LayoutInflater inflater;

    public GridViewAdapter2(Context context, List<String> tags) {
        super();
        this.tags = tags;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return tags.size();
    }

    @Override
    public Object getItem(int position){
        return tags.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        GridViewAdapter2.ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new GridViewAdapter2.ViewHolder();
            convertView=inflater.inflate(R.layout.fragment_tags_body_gridview_item,null);
//            viewHolder.frameLayout=(FrameLayout)convertView.findViewById(R.id.fragment_tags_gridview_bp);
            viewHolder.textView=(TextView)convertView.findViewById(R.id.fragment_tags_gridview_tagname);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(GridViewAdapter2.ViewHolder)convertView.getTag();
        }

        viewHolder.textView.setText(tags.get(position));

//        Random random0 = new Random();
//        int r0 = random0.nextInt(256);
//        int g0 = random0.nextInt(256);
//        int b0 = random0.nextInt(256);
//        viewHolder.frameLayout.setBackgroundColor(Color.rgb(r0,g0,b0));

        Random random1 = new Random();
        int r1 = random1.nextInt(256);
        int g1 = random1.nextInt(256);
        int b1 = random1.nextInt(256);
        viewHolder.textView.setTextColor(Color.rgb(r1,g1,b1));

        return convertView;
    }

    class ViewHolder
    {
//        private FrameLayout frameLayout;
        private TextView textView;
    }

}
