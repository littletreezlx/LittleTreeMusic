package com.example.littletreemusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.littletreemusic.R;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class ViewPagerFragment1 extends Fragment {
    ViewPager mviewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_play_viewpager1, mviewPager, true);
        mviewPager = (ViewPager) view.findViewById(R.id.play_viewpager);
        return view;
    }
}
