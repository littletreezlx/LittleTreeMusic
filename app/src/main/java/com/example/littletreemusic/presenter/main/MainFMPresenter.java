package com.example.littletreemusic.presenter.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.activity.main.MainBodyFragment;
import com.example.littletreemusic.activity.main.MainBottomFragment;
import com.example.littletreemusic.activity.main.MainSongListFragment;
import com.example.littletreemusic.activity.main.MainTagListFragment;
import com.example.littletreemusic.activity.main.MainTitleFragment;

import javax.inject.Inject;


public class MainFMPresenter implements MainFMContract.IMainFMPresenter{


    @Inject
    MainActivity mainActivity;


    MainTitleFragment title0;
    MainBodyFragment body0;
    MainBottomFragment bottom0;
    MainSongListFragment songList;
    MainSongListFragment playList;
    MainTagListFragment tagList;

    DrawerLayout mDrawerLayout;
    FragmentManager fm;
    FragmentTransaction transaction;

    public boolean isHomePage=true;


    @Override
    public void init() {
        mDrawerLayout=mainActivity.drawerLayout;
        fm=mainActivity.getFragmentManager();
        transaction=fm.beginTransaction();
        this.title0 = new MainTitleFragment();
        this.body0 = new MainBodyFragment();
        this.bottom0 = new MainBottomFragment();
        transaction.add(R.id.main_title, title0)
                .add(R.id.main_body, body0)
                .add(R.id.main_bottom, bottom0);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }


    @Override
    public void toFullSongList() {
        songList=new MainSongListFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("mode",-2);
        songList.setArguments(bundle);
        title0.setTitle("所有歌曲");
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body, songList)
                .hide(body0);
        goOutHome();
    }
    @Override
    public void FromFullSongList() {
        title0.backToHome();
        transaction=fm.beginTransaction();
        transaction.remove(songList)
                .show(body0);
        songList=null;
        goBackHome();
    }


    @Override
    public void toPlayList() {
        playList=new MainSongListFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("mode",-1);
        playList.setArguments(bundle);
        title0.setTitle("我的歌单");
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body, playList)
                .hide(body0);
        goOutHome();
    }
    @Override
    public void FromPlayList() {
        title0.backToHome();
        transaction=fm.beginTransaction();
        transaction.remove(playList)
                .show(body0);
        playList=null;
        goBackHome();
    }


    @Override
    public void toTagList() {
        tagList=new MainTagListFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body, tagList)
                .hide(body0);
        goOutHome();
    }

    @Override
    public void FromTagList() {
        title0.backToHome();
        transaction=fm.beginTransaction();
        transaction.remove(tagList)
                .show(body0);
        tagList=null;
        goBackHome();
    }


    @Override
    public void toOneTagSongList(int tagPosition,String tagName) {
        songList=new MainSongListFragment();
        title0.setTitle(tagName);
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body, songList)
                .hide(tagList);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }
    @Override
    public void FromOneTagSongList() {
        title0.backToHome();
        transaction=fm.beginTransaction();
        transaction.remove(songList)
                .show(tagList);
        songList=null;

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }


    @Override
    public void toCommunity() {

    }
    @Override
    public void fromCommunity() {
        title0.backToHome();
    }


    @Override
    public void backPressed(){
        if (songList != null && tagList == null){
            FromFullSongList();
        }else if (playList != null){
            FromPlayList();
        }else if (songList != null){
            FromOneTagSongList();
        }else if (tagList != null){
            FromTagList();
        }
    }


    private void goOutHome(){
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        isHomePage=false;
    }
    private void goBackHome(){
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        isHomePage=true;
    }






}


