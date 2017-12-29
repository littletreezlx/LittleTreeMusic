package com.example.littletreemusic.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.MainActivity;
import com.example.littletreemusic.adapter.GridViewAdapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 春风亭lx小树 on 2017/12/26.
 */

public class BodyFragment2 extends Fragment {

    RelativeLayout mbodytemp;
    GridView gridView;
    GridViewAdapter2 adapter;
    List<String> tagList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_tags_body, mbodytemp, true);
        mbodytemp = (RelativeLayout) view.findViewById(R.id.body_temp);
        gridView = (GridView) view.findViewById(R.id.fragment_tags_gridview);
        SharedPreferences sp_tag=getActivity().getSharedPreferences("sp_tag", Context.MODE_PRIVATE);
        tagList = new ArrayList<>();
        Set tagSet=sp_tag.getStringSet("TagSet",null);
        if (tagSet != null){
            tagList.addAll(tagSet);
            adapter=new GridViewAdapter2(getActivity(),tagList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.toOneTag(position+10);
                }
            });
        }
        return view;

    }
}



