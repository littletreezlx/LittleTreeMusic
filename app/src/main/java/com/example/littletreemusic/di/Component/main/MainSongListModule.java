package com.example.littletreemusic.di.Component.main;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.main.SongListContract;
import com.example.littletreemusic.presenter.main.SongListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class MainSongListModule {

    SongListContract.ISongListView  mISongListIview;
    SongListPresenter mSongListPresenter;


    public MainSongListModule(SongListContract.ISongListView iSongListIview){
        mISongListIview=iSongListIview;
        mSongListPresenter=new SongListPresenter();
    }

    @PerFragment
    @Provides
    SongListContract.ISongListView provideISongListIview(){
        return mISongListIview;
    }

    @PerFragment
    @Provides
    SongListPresenter provideSongListPresenter(){
        return mSongListPresenter;
    }


}
