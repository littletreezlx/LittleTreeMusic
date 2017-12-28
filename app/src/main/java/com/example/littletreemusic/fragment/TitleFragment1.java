package com.example.littletreemusic.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZLX Vincent on 2017/8/30.
 */

public class TitleFragment1 extends Fragment {

    TextView titleText;
    RelativeLayout mtitletemp;
    Button backbutton,searchbutton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view= inflater.inflate(R.layout.fragment_universal_title,mtitletemp,true);


        mtitletemp=(RelativeLayout)view.findViewById(R.id.title_temp);
        backbutton=(Button)view.findViewById(R.id.button_back);
        searchbutton=(Button)view.findViewById(R.id.button_search);
        titleText=(TextView)view.findViewById(R.id.fragment_universal_title_text);

        int mode = getArguments().getInt("mode");
        switch (mode){
            case 0:
                titleText.setText("我的音乐");
                break;
            case 1:
                titleText.setText("我的最爱");
                break;
            case 2:
                titleText.setText("我的标签");
                break;
            default:
                SharedPreferences sp_tag=getActivity().getSharedPreferences("sp_tag", Context.MODE_PRIVATE);
                List<String> tagList = new ArrayList<>();
                tagList.addAll(sp_tag.getStringSet("TagSet",null));
                String tagName = tagList.get(mode-10);
                titleText.setText(tagName);
                break;
        }

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.back();
            }
        });

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "还未完善", Toast.LENGTH_SHORT).show();
            }
        });

        return view;


    }



}


