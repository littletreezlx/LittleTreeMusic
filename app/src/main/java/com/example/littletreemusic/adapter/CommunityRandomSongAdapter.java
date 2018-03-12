package com.example.littletreemusic.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.pojo.Song;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */

public class CommunityRandomSongAdapter extends RecyclerView.Adapter<CommunityRandomSongAdapter.ViewHolder> {

    private List<Song> songs;
    private List<Bitmap> pictures;
    OnRecyclerClickListener mOnRecyclerClickListener;

    public interface OnRecyclerClickListener {
        void onImageClick(View view, int position);

        void onTextClick(View view, int position);
    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener onRecyclerClickListener) {
        mOnRecyclerClickListener = onRecyclerClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.com_randomsong_item_tv_title)
        TextView tv_Title;
        @BindView(R.id.com_randomsong_item_tv_artist)
        TextView tv_Artist;
        @BindView(R.id.com_randomsong_item_tv_layout)
        LinearLayout layout_Clickable;
        @BindView(R.id.com_randomsong_item_headshots)
        ImageButton btn_Headshots;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CommunityRandomSongAdapter(List<Song> songs,List<Bitmap> pictures) {
        this.songs = songs;
        this.pictures=pictures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.com_randomsong_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.btn_Headshots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerClickListener.onImageClick(v, position);
            }
        });
        holder.layout_Clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerClickListener.onTextClick(v, position);
            }
        });

//        holder.btn_Headshots.setBackground(pictures.get(0));
        holder.tv_Title.setText(songs.get(0).getTitle());
        holder.tv_Artist.setText(songs.get(0).getArtist());


    }


    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
