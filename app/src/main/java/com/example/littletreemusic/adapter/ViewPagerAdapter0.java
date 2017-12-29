package com.example.littletreemusic.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class ViewPagerAdapter0 extends PagerAdapter {


    private List<View> views;


    public ViewPagerAdapter0(List<View> views) {
        this.views = views;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (views.get(position) != null){
            container.addView(views.get(position));
        }

        //每次滑动的时候把视图添加到viewpager
        return views.get(position);
    }

    @Override
    public int getCount() {

        return views.size();
    }


}

