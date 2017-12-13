package com.example.littletreemusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.littletreemusic.R;

import java.util.ArrayList;

/**
 * Created by ZLX Vincent on 2017/8/30.
 */

public class GridViewAdapter0 extends BaseAdapter {

    private String[] titles;
    private int[] images;
  //  private String[] titles = new String[]{"pic1", "pic2", "pic3", "pic4"};
  //  private int[] images = new int[]{R.drawable.ic100, R.drawable.ic100, R.drawable.ic100, R.drawable.ic100};
    private LayoutInflater inflater;
    private ArrayList<Picture> pictures;

    public GridViewAdapter0(Context context,String titles[],int images[]){
        super();
        this.titles=titles;
        this.images=images;
        pictures=new ArrayList<Picture>();
        inflater=LayoutInflater.from(context);
        for ( int i=0;i<images.length;i++){
            Picture picture=new Picture(titles[i],images[i]);
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
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.fragment_main_body_gridview_item,null);
            viewHolder.title=(TextView)convertView.findViewById(R.id.fragment_body_gridview_text);
            viewHolder.image=(ImageView)convertView.findViewById(R.id.fragment_body_gridview_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.image.setBackgroundResource(pictures.get(position).getImageId());
        viewHolder.title.setText(pictures.get(position).getTitle());
        return convertView;
    }

    class ViewHolder
    {
        public TextView title;
        public ImageView image;
    }

    class Picture
    {
        private String title;
        private int imageId;

        public Picture()
        {
            super();
        }

        public Picture(String title, int imageId)
        {
            super();
            this.title = title;
            this.imageId = imageId;
        }
        public String getTitle()
        {
            return title;
        }
        public void setTitle(String title)
        {
            this.title = title;
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
