package com.example.littletreemusic.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.littletreemusic.R;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.model.Song;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 春风亭lx小树 on 2017/12/14.
 */

public class ListViewAdapter0 extends BaseAdapter {

    Context context;
    List<String> list=new ArrayList<>();
    private LayoutInflater inflater;
    String tagName;
    public static List<String> adcheckedTags=new ArrayList<>();


    public ListViewAdapter0(Context context, List list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_tag_item, null);
            holder.tagcb = (CheckBox) convertView.findViewById(R.id.item_tag_checkbox);
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
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("提示");
                    builder.setMessage("是否删除该标签?");
                    builder.setIcon(R.drawable.icon_checked);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tagName = list.get(position);

                            SharedPreferences sp_tag=context.getSharedPreferences("sp_tag",Context.MODE_PRIVATE);
                            Set<String> tagSet=sp_tag.getStringSet("TagSet",null);
                            Set<String> tagSet2=new HashSet<>();
                            tagSet2.addAll(tagSet);
                            tagSet2.remove(tagName);
                            SharedPreferences.Editor editor=context.getSharedPreferences("sp_tag",Context.MODE_PRIVATE).edit();
                            editor.remove("TagSet");
                            editor.putStringSet("TagSet",tagSet2);
                            editor.apply();

                                list.clear();
                                list.addAll(tagSet2);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                    return false;
                }
            });
            holder.tagcb.setText(tagName);
            Song playingSong= DataSupport.where("uri=?", MusicService.playinguristr).find(Song.class).get(0);
            List<String> pstList=playingSong.getTagList();
            if (pstList.contains(tagName)){
                holder.tagcb.setChecked(true);
            }
        }
        return convertView;
    }

//    protected void confirmAddTagDialog(final String tagName){
//        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
//        dialog.setTitle("标签");
//        dialog.setMessage("确认添加该标签吗？");
//        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Song playingSong= DataSupport.where("uri=?", MusicService.playinguristr).find(Song.class).get(0);
//                List<String> pstList=playingSong.getTagList();
//                int updateId=playingSong.getId();
//                if (pstList == null){
//                    pstList=new ArrayList<>();
//                }
//                pstList.add(tagName);
//                Song updateSong = new Song();
//                updateSong.setTagList(pstList);
//                updateSong.update(updateId);
//            }
//        });
//        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//    }

    protected class ViewHolder{
        CheckBox tagcb;
    }




}
