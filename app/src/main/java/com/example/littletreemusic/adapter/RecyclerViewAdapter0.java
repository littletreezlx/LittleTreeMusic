package com.example.littletreemusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.model.Song;
import com.example.littletreemusic.service.MusicService;

import java.util.List;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */

public class RecyclerViewAdapter0 extends RecyclerView.Adapter<RecyclerViewAdapter0.ViewHolder> implements SectionIndexer
{

    private Context context;
    private List<Song> songs;
    private LayoutInflater inflater;
//    OnRecyclerClickListener onRecyclerClickListener;
    private int selectedPosition = -1;
    private int lastPosition = -1;


//    public void setOnRecyclerClickListener(OnRecyclerClickListener monRecyclerClickListener){
//        this.onRecyclerClickListener=monRecyclerClickListener;
//    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text0,text1,firstLetter;
        ImageView image0,image1;



        public ViewHolder(View view) {
            super(view);
            image0=(ImageView)view.findViewById(R.id.fragment_universal_body_recycler_image0);
            image1=(ImageView)view.findViewById(R.id.fragment_universal_body_recycler_image1);
            text0=(TextView) view.findViewById(R.id.fragment_universal_body_recycler_text0);
            text1=(TextView) view.findViewById(R.id.fragment_universal_body_recycler_text1);
            firstLetter=(TextView) view.findViewById(R.id.fragment_universal_body_recycler_fltext);
        }
    }

    public RecyclerViewAdapter0(Context context, List<Song> songs){
        this.context=context;
        this.songs=songs;
    }

    public void updateRecyclerView(List<Song> songs){
        this.songs = songs;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_universal_body_recycler_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);

//        holder.itemView.setSelected(true);
        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int updatePosition=songs.get(holder.getLayoutPosition()).getId();
                Song song=new Song();
                song.setToDefault("isFavourite");
                song.update(updatePosition);
                holder.image1.setVisibility(View.GONE);
                holder.image0.setVisibility(View.VISIBLE);
            }
        });

        holder.image0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updatePosition=songs.get(holder.getLayoutPosition()).getId();
                Song song=new Song();
                song.setIsFavourite(1);
                song.update(updatePosition);
                holder.image0.setVisibility(View.GONE);
                holder.image1.setVisibility(View.VISIBLE);
            }
        });
        holder.text0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MusicService.class);
                String uri=songs.get(holder.getLayoutPosition()).getUri();
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


        Song song=songs.get(position);
        if (song.getIsFavourite() == 1){
            holder.image0.setVisibility(View.GONE);
            holder.image1.setVisibility(View.VISIBLE);

        }else if (song.getIsFavourite() == 0){
            holder.image1.setVisibility(View.GONE);
            holder.image0.setVisibility(View.VISIBLE);
        }
        holder.text0.setText(song.getTitle());
        holder.text1.setText(song.getArtist());
        holder.itemView.setSelected(selectedPosition == position);

        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            holder.firstLetter.setVisibility(View.VISIBLE);
            holder.firstLetter.setText(song.getSortLetters());
        }else{
            holder.firstLetter.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount(){
        return songs.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return songs.get(position).getSortLetters().charAt(0);
    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = songs.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

//     public interface OnRecyclerClickListener{
//        void onImageClick(View view,int position);
//        void onTextClick(View view,int position);
//    }

}
