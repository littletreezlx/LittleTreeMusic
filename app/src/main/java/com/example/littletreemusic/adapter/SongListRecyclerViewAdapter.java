package com.example.littletreemusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.pojo.Song;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */

public class SongListRecyclerViewAdapter extends RecyclerView.Adapter<SongListRecyclerViewAdapter.ViewHolder>
        implements SectionIndexer {

    private List<Song> songs;
    OnRecyclerClickListener mOnRecyclerClickListener;
    private int lastSelectedPosition = -1, playingPosition = -1;

    public interface OnRecyclerClickListener {
        void onImageClick(View view, int position);

        void onTextClick(View view, int position);

    }

    public void setOnRecyclerClickListener(OnRecyclerClickListener onRecyclerClickListener) {
        mOnRecyclerClickListener = onRecyclerClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_favourite)
        ImageButton btn_Favourite;
        @BindView(R.id.textView_firstletter)
        TextView tv_firstletter;
        @BindView(R.id.tv_title)
        TextView tv_Title;
        @BindView(R.id.tv_artist)
        TextView tv_Artist;
        @BindView(R.id.btn_checkbox)
        ImageButton btn_Checkbox;
        @BindView(R.id.relativelayout_clickable)
        RelativeLayout layout_Clickable;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public SongListRecyclerViewAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_songlist_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
//        final int position=holder.getLayoutPosition();

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.btn_Favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerClickListener.onImageClick(v,position);
            }
        });
        holder.layout_Clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerClickListener.onTextClick(v,position);
                Log.d("aaa","aaa");
            }
        });

//        holder.layout_Clickable.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });

        Song song = songs.get(position);
        if (song.getIsFavourite() == 1) {
            holder.btn_Favourite.setSelected(true);
        } else{
            holder.btn_Favourite.setSelected(false);
        }
        holder.tv_Title.setText(song.getTitle());
        holder.tv_Artist.setText(song.getArtist());

//        holder.itemView.setSelected(selectedPosition == position);
//        if (song.getId() == playingPosition) {
//            holder.text0.setTextSize(24);
//            holder.text1.setTextSize(16);
//            holder.text0.setTextColor(context.getResources().getColor(R.color.绿色));
//            holder.text1.setTextColor(context.getResources().getColor(R.color.绿色));
//        }

        //将position保存在itemView的Tag中，以便点击时进行获取???拿不到
//        holder.itemView.setTag(position);
//        holder.btn_Favourite.setTag(position);
//        holder.tv_Title.setTag(position);
//        holder.tv_Artist.setTag(position);

        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.tv_firstletter.setVisibility(View.VISIBLE);
            holder.tv_firstletter.setText(song.getSortLetter());
        } else {
            holder.tv_firstletter.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return songs.get(position).getSortLetter().charAt(0);
    }


    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = songs.get(i).getSortLetter();
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

//    public void showPlayingSong(int position) {
//        playingPosition = position;
//        notifyItemChanged(position);
//    }

//    public void updateItem(int position ){
//        if (lastSelectedPosition != -1){
//
//            notifyItemChanged(lastSelectedPosition);
//        }
//        notifyItemChanged(position);
//        lastSelectedPosition=position;
//    }

}
