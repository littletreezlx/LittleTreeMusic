package com.example.littletreemusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.littletreemusic.R;
import com.example.littletreemusic.view.ViewPager_view0;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class ViewPagerFragment0 extends Fragment {

    ViewPager mviewPager;
    ViewPager_view0 view0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_play_viewpager0, mviewPager, true);
        mviewPager = (ViewPager) view.findViewById(R.id.play_viewpager);
        view0=(ViewPager_view0) view.findViewById(R.id.view0);

        return view;
    }
}
