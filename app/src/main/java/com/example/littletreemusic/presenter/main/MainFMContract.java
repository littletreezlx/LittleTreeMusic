package com.example.littletreemusic.presenter.main;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

public interface MainFMContract {


    interface IMainFMView extends IView {

//        void showToast();
    }



    interface IMainFMPresenter extends IPresenter<IMainFMView> {

//        跳转歌曲列表
        void toFullSongList();
        void FromFullSongList();


//        跳转歌单
        void toPlayList();
        void FromPlayList();


//        跳转标签列表
        void toTagList();
        void FromTagList();
        void toOneTagSongList(int tagPosition,String tagName);
        void FromOneTagSongList();

//        跳转社区
        void toCommunity();
        void fromCommunity();

        void backPressed();
    }

}
