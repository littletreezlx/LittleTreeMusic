package com.example.littletreemusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.littletreemusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZLX Vincent on 2017/9/12.
 */

public class GridViewAdapter1 extends BaseAdapter {


    private int[] images;
    //  private String[] titles = new String[]{"pic1", "pic2", "pic3", "pic4"};
    //  private int[] images = new int[]{R.drawable.ic100, R.drawable.ic100, R.drawable.ic100, R.drawable.ic100};
    private LayoutInflater inflater;
    private List<Picture> pictures;

    public GridViewAdapter1(Context context, int images[]){
        super();
        this.images=images;
        pictures=new ArrayList<>();
        inflater=LayoutInflater.from(context);
        for ( int i=0;i<images.length;i++){
            Picture picture=new Picture(images[i]);
            pictures.add(picture);
        }
    }

    @Override
    public int getCount(){
        return pictures.size();
    }

    @Override
    public Object getItem(int position){
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.activity_changebp_gridview_item,null);
            viewHolder.image0=(ImageView)convertView.findViewById(R.id.changebp_gridview_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.image0.setBackgroundResource(pictures.get(position).getImageId());

        return convertView;
    }

    class ViewHolder
    {
        ImageView image0;
    }


    class Picture
    {
        private int imageId;
        public Picture()
        {
            super();
        }
        public Picture( int imageId)
        {
            super();
            this.imageId = imageId;
        }
        public int getImageId()
        {
            return imageId;
        }
        public void setImageId(int imageId)
        {
            this.imageId = imageId;
        }
    }



}

