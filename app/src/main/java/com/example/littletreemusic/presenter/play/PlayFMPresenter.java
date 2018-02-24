package com.example.littletreemusic.presenter.play;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.play.PlayActivity;
import com.example.littletreemusic.activity.play.PlayBottomFragment;
import com.example.littletreemusic.activity.play.PlayTitleFragment;
import com.example.littletreemusic.activity.play.SeekBarFragment;
import com.example.littletreemusic.activity.play.ViewPagerFragment;


public class PlayFMPresenter implements PlayFMContract.IPlayFMPresenter{

    public boolean isHomePage;
    PlayTitleFragment title;
    ViewPagerFragment viewPager;
    SeekBarFragment   seekBar;
    PlayBottomFragment bottom;
    FragmentManager fm;
    FragmentTransaction transaction;

    PlayFMContract.IPlayFMView iPlayFMView;

//    @Override
//    public void bindView(PlayFMContract.IPlayFMView view) {
//
//    }

    @Override
    public void init(Context context) {
        PlayActivity playActivity=((PlayActivity)context);
        fm = playActivity.getFragmentManager();
//        VifpAdapter = new ViewPagerAdapter(fm);
//        viewPager.setAdapter(fpAdapter);
//        viewPager.setCurrentItem(1);
//        viewPager.addOnPageChangeListener(this);
//        viewPager.setPageTransformer(true, new DepthPageTransformer());
        title = new PlayTitleFragment();
        viewPager = new ViewPagerFragment();
        seekBar = new SeekBarFragment();
        bottom  = new PlayBottomFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.play_title, title)
                .add(R.id.play_viewpager, viewPager)
                .add(R.id.play_seekbar, seekBar)
                .add(R.id.play_bottom, bottom);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}


