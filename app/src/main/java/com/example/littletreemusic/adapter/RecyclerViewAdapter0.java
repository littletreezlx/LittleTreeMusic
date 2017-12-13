package com.example.littletreemusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.table.Song;

import java.util.List;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */

public class RecyclerViewAdapter0 extends RecyclerView.Adapter<RecyclerViewAdapter0.ViewHolder>
{

    private Context context;
    private List<Song> songs;
    private LayoutInflater inflater;
    OnRecyclerClickListener onRecyclerClickListener;
    private int selectedPosition = -1;
    private int lastPosition = -1;




    public void setOnRecyclerClickListener(OnRecyclerClickListener monRecyclerClickListener){
        this.onRecyclerClickListener=monRecyclerClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text0,text1;
        ImageView image0,image1;
        private OnRecyclerClickListener mListener;

        public ViewHolder(View view,OnRecyclerClickListener onRecyclerClickListener) {
            super(view);
            image0=(ImageView)view.findViewById(R.id.fragment_universal_body_recycler_image0);
            image1=(ImageView)view.findViewById(R.id.fragment_universal_body_recycler_image1);
            text0=(TextView) view.findViewById(R.id.fragment_universal_body_recycler_text0);
            text1=(TextView) view.findViewById(R.id.fragment_universal_body_recycler_text1);
//            this.mListener=onRecyclerClickListener;
//            text0.setOnClickListener(this);
//            text1.setOnClickListener(this);
        }

//        public void onClick(View v) {
//            if(mListener != null){
//                if (v.equals(image0)){
//                    mListener.onImageClick(image0,getLayoutPosition());
//                }else if (v.equals(image1)) {
//                    mListener.onImageClick(image1, getLayoutPosition());
//                }else if (v.equals(text0)){
//                    mListener.onTextClick(text0,getLayoutPosition());
//                }else if (v.equals(text1)){
//                    mListener.onTextClick(text1,getLayoutPosition());
//                }
//            }
//        }

    }

    public RecyclerViewAdapter0(Context context, List<Song> songs){
        this.context=context;
        this.songs=songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_universal_body_recycler_item,parent,false);
        final ViewHolder holder=new ViewHolder(view,onRecyclerClickListener);


//        holder.itemView.setSelected(true);
        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song=new Song();
                song.setToDefault("isFavourite");
                song.update(holder.getLayoutPosition());
                holder.image1.setVisibility(View.GONE);
                holder.image0.setVisibility(View.VISIBLE);
            }
        });

        holder.image0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song=new Song();
                song.setIsFavourite(1);
                song.update(holder.getLayoutPosition());
                holder.image0.setVisibility(View.GONE);
                holder.image1.setVisibility(View.VISIBLE);
            }
        });
        holder.text0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MusicService.class);
                String uri=songs.get(holder.getLayoutPosition()).getUri();
                intent.putExtra("mode","list_op");
                intent.putExtra("position", holder.getLayoutPosition());
                intent.putExtra("uri",uri);
                context.startService(intent);


                notifyItemChanged(selectedPosition);
                selectedPosition =holder.getLayoutPosition();
                notifyItemChanged(selectedPosition);




//                失败的操作，导致多选变色
//                lastPosition=nowPositoin;
//                nowPositoin=holder.getLayoutPosition();
//                if (view.findViewWithTag("now") != null){
//                    view.findViewWithTag("now").setTag("last");
//                }
//                    holder.itemView.setTag("now");
//                if (view.findViewWithTag("last") != null){
//                    view.findViewWithTag("last").setBackgroundColor(Color.parseColor("#ffffff"));
//                }
//                view.findViewWithTag("now").setBackgroundColor(Color.parseColor("#99CC33"));
//                holder.itemView.setBackgroundColor(Color.parseColor("#99CC33"));
//                selectedPosition =holder.getLayoutPosition();
//                notifyItemChanged(lastPosition);
//                notifyItemChanged(nowPositoin);

            }
        });
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MusicService.class);
                String uri=songs.get(holder.getLayoutPosition()).getUri();
                intent.putExtra("mode","list_op");
                intent.putExtra("position", holder.getLayoutPosition());
                intent.putExtra("uri",uri);
                context.startService(intent);

                notifyItemChanged(selectedPosition);
                selectedPosition =holder.getLayoutPosition();
                notifyItemChanged(selectedPosition);
            }
        });




        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (songs.get(position).getIsFavourite() == 1){
            holder.image0.setVisibility(View.GONE);
            holder.image1.setVisibility(View.VISIBLE);

        }else if (songs.get(position).getIsFavourite() == 0){
            holder.image1.setVisibility(View.GONE);
            holder.image0.setVisibility(View.VISIBLE);
        }
        holder.text0.setText(songs.get(position).getTitle());
        holder.text1.setText(songs.get(position).getArtist());
        holder.itemView.setSelected(selectedPosition == position);


    }


    @Override
    public int getItemCount(){
        return songs.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }


     public interface OnRecyclerClickListener{
        void onImageClick(View view,int position);
        void onTextClick(View view,int position);
    }



}
