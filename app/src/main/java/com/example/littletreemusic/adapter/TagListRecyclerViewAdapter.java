package com.example.littletreemusic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.littletreemusic.R;

import java.util.List;
import java.util.Random;

/**
 * Created by 春风亭lx小树 on 2017/12/26.
 */

public class TagListRecyclerViewAdapter extends RecyclerView.Adapter<TagListRecyclerViewAdapter.ViewHolder> {

    private List<String> tags;
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }


    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

//    构造器
    public TagListRecyclerViewAdapter(Context context, List<String> tags) {
        super();
        this.tags = tags;
        this.context=context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_TagName;
        public ViewHolder(View view) {
            super(view);
            tv_TagName=(TextView) view.findViewById(R.id.recyclerView_MyTags_tagname);
        }
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_taglist_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Random random1 = new Random();
        int r1 = random1.nextInt(256);
        int g1 = random1.nextInt(256);
        int b1 = random1.nextInt(256);
        holder.tv_TagName.setTextColor(Color.rgb(r1, g1, b1));
        holder.tv_TagName.setText(tags.get(holder.getLayoutPosition()));
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int position = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }

    }


    //    对外方法
//    public void addData(int position) {
//        tags.add("Insert One");
//        notifyItemInserted(position);
//    }
//
//    public void removeData(int position) {
//        tags.remove(position);
//        notifyItemRemoved(position);
//    }

    public String findTagById(int position){
        return tags.get(position);
    }


}
