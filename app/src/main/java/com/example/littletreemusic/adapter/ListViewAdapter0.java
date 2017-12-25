package com.example.littletreemusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.littletreemusic.R;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.table.Song;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 春风亭lx小树 on 2017/12/14.
 */

public class ListViewAdapter0 extends BaseAdapter {

    Context context;
    ArrayList<String> list;
    private LayoutInflater inflater;
    String tagName;
    ArrayList<String> checkedTags=new ArrayList<>();


    public ListViewAdapter0(Context context, ArrayList list){
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
        ViewHolder holder = null;
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
//                    int index = (int)buttonView.getTag();

                    int ps=buttonView.getId();
                    String tn = list.get(ps);

                    if (isChecked)
                    {
                        buttonView.setChecked(true);
//                    SharedPreferences.Editor editor=context.getSharedPreferences("sp_CheckBox",Context.MODE_PRIVATE).edit();
                        checkedTags.add(tn);
                    }
                    else
                        buttonView.setChecked(false);
                        checkedTags.remove(ps);
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

    public ArrayList getCheckedTags(){
        return  checkedTags;
    }

    protected class ViewHolder{
        CheckBox tagcb;
    }




}
