package com.example.littletreemusic.presenter.community;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.community.CommentFragment;
import com.example.littletreemusic.activity.community.CommunityHomeFragment;
import com.example.littletreemusic.activity.community.LiveFragment;
import com.example.littletreemusic.activity.community.MiddleMenuFragment;
import com.example.littletreemusic.activity.community.RandomPictureFragment;
import com.example.littletreemusic.activity.community.RandomSongFragment;
import com.example.littletreemusic.activity.community.SongInfoFragment;
import com.example.littletreemusic.activity.community.TopSongFragment;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.activity.main.MainTitleFragment;
import com.example.littletreemusic.activity.play.ViewPagerFragment;

import javax.inject.Inject;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class CommunityFMPresenter implements CommunityFMContract.ICommunityFMPresenter {


    MainTitleFragment title0;
    CommunityHomeFragment communityHome;
    RandomSongFragment randomSong;
    RandomPictureFragment randomPicture;
    TopSongFragment topSong;
    LiveFragment live;
    SongInfoFragment songInfo;
    MiddleMenuFragment middleMenu;
    ViewPagerFragment viewPagerFragment;
    CommentFragment comment;


    FragmentManager fm;
    FragmentTransaction transaction;
    public int nowLocation=0;
    private static final int LOCATION_COMMUNITYHOME=0,LOCATION_RANDOMSONG=1,LOCATION_RANDOMPICTURE=2,
            LOCATION_TOPSONG=3,LOCATION_LIVE=4,LOCATION_SONGINFO=5,LOCATION_COMMENT=6;

    public CommunityFMPresenter(MainActivity mainActivity){
        fm=mainActivity.getFragmentManager();
        title0=(MainTitleFragment)fm.findFragmentByTag("title0");
        communityHome=(CommunityHomeFragment) fm.findFragmentByTag("communityHome");
        nowLocation=LOCATION_COMMUNITYHOME;
    }

    @Override
    public void toRandomSong() {
        randomSong=new RandomSongFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body,randomSong,"community1")
                .hide(communityHome);
        goOutHome();
        nowLocation=LOCATION_RANDOMSONG;
    }

    @Override
    public void fromRandomSong() {
        transaction=fm.beginTransaction();
        transaction.remove(randomSong)
                .show(communityHome);
        randomSong=null;
        goBackHome();
    }

    @Override
    public void toRandomPicture() {
        randomPicture=new RandomPictureFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body,randomPicture,"community1")
                .hide(communityHome);
        goOutHome();
        nowLocation=LOCATION_RANDOMPICTURE;
    }

    @Override
    public void fromRandomPicture() {
        transaction=fm.beginTransaction();
        transaction.remove(randomPicture)
                .show(communityHome);
        randomPicture=null;
        goBackHome();

    }

    @Override
    public void toTopSong() {
        topSong=new TopSongFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body, topSong,"community1")
                .hide(communityHome);
        goOutHome();
        nowLocation=LOCATION_TOPSONG;
    }

    @Override
    public void fromTopSong() {
        transaction=fm.beginTransaction();
        transaction.remove(topSong)
                .show(communityHome);
        topSong=null;
        goBackHome();
    }

    @Override
    public void toLive() {
        live=new LiveFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body, live,"community1")
                .hide(communityHome);
        goOutHome();
        nowLocation=LOCATION_LIVE;
    }

    @Override
    public void fromLive() {
        transaction=fm.beginTransaction();
        transaction.remove(live)
                .show(communityHome);
        live=null;
        goBackHome();
    }

    @Override
    public void toSongInfo() {
        songInfo=new SongInfoFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body,songInfo)
                .hide(fm.findFragmentByTag("community1"));
        goOutHome();
        nowLocation=LOCATION_SONGINFO;
    }

    @Override
    public void fromSongInfo() {
        transaction=fm.beginTransaction();
        transaction.remove(songInfo)
                .show(fm.findFragmentByTag("community1"));
        songInfo=null;
        goBackHome();
    }

    @Override
    public void toComment() {
        comment=new CommentFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_body, live)
                .hide(songInfo);
        goOutHome();
        nowLocation=LOCATION_LIVE;
    }

    @Override
    public void fromComment() {
        transaction=fm.beginTransaction();
        transaction.remove(comment)
                .show(songInfo);
        comment=null;
        goBackHome();
    }

    @Override
    public void backPressed(){
        switch (nowLocation){
            case LOCATION_COMMUNITYHOME:
                break;
            case LOCATION_RANDOMSONG:
                fromRandomSong();
                break;
            case LOCATION_RANDOMPICTURE:
                fromRandomPicture();
                break;
            case LOCATION_TOPSONG:
                fromTopSong();
                break;
            case LOCATION_LIVE:
                fromLive();
                break;
            case LOCATION_SONGINFO:
                fromSongInfo();
                break;
            case LOCATION_COMMENT:
                fromComment();
                break;
            default:break;
        }
    }

    private void goOutHome(){

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
    private void goBackHome(){
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
        nowLocation=LOCATION_COMMUNITYHOME;
    }


}

