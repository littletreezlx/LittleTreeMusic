package com.example.littletreemusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.littletreemusic.activity.play.ViewPagerFragment0;
import com.example.littletreemusic.activity.play.ViewPagerFragment1;
import com.example.littletreemusic.activity.play.ViewPagerFragment2;
import com.example.littletreemusic.di.Component.play.ViewPagerComponent;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private ViewPagerFragment0 f0 = null;
    private ViewPagerFragment1 f1 = null;
    private ViewPagerFragment2 f2 = null;

    public ViewPagerAdapter(FragmentManager fm, ViewPagerComponent viewPagerComponent) {
        super(fm);
        f0 = new ViewPagerFragment0();
        f1 = new ViewPagerFragment1();
        f2 = new ViewPagerFragment2();
        viewPagerComponent.inject(f0);
        viewPagerComponent.inject(f1);
        viewPagerComponent.inject(f2);
    }
//
//    @Override
//    public boolean isViewFromObject(View arg0, Object arg1) {
//        return arg0 == arg1;
//    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = f0;
                break;
            case 1:
                fragment = f1;
                break;
            case 2:
                fragment = f2;
                break;

           default:break;
        }
        return fragment;
    }


}

