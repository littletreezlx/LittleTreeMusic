package com.example.littletreemusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.littletreemusic.R;

/**
 * Created by ZLX Vincent on 2017/8/29.
 */

public class TitleFragment0 extends Fragment {

    RelativeLayout mtitletemp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){

        View view= inflater.inflate(R.layout.fragment_main_title,mtitletemp,true);
        mtitletemp=(RelativeLayout)view.findViewById(R.id.title_temp);

        return view;
    }
}
