package com.example.littletreemusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.littletreemusic.R;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.service.MusicService;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 春风亭lx小树 on 2017/12/14.
 */

public class TagListViewAdapter extends BaseAdapter {

    Context context;
    List<String> list=new ArrayList<>();
    String tagName;
    public static List<String> adcheckedTags=new ArrayList<>();


    public TagListViewAdapter(Context context, List list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected class ViewHolder{
        CheckBox tagcb;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.play_dialog_item, null);
            holder.tagcb = (CheckBox) convertView.findViewById(R.id.tag_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list == null) {
            holder.tagcb.setText("暂无标签");
        } else {
            tagName = list.get(position);
            holder.tagcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String tn = buttonView.getText().toString();
                    if (isChecked) {
                        buttonView.setChecked(true);
                        if (!adcheckedTags.contains(tn)){
                            adcheckedTags.add(tn);
                        }
                    } else {
                        buttonView.setChecked(false);
                        if (adcheckedTags.contains(tn)){
                            adcheckedTags.remove(tn);
                        }
                    }
                }
            });

            holder.tagcb.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });
            holder.tagcb.setText(tagName);
//            选中标签列表中正在播放的歌曲的标签项。
            List<Song> playingList = DataSupport.where("uri=?", MusicService.playingUriStr).find(Song.class);
            if (playingList !=null && playingList.size() != 0) {
                Song playingSong = playingList.get(0);
                List<String> pstList=playingSong.getTagList();
                if (pstList.contains(tagName)){
                    holder.tagcb.setChecked(true);
                }
            }
        }
        return convertView;
    }

//    public void findCheckedTags(){
//
//    }




    public void updateTagList(List<String> tagList){
        list=tagList;
        notifyDataSetChanged();
    }




}
