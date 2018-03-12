package com.example.littletreemusic.di.Component.community;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.SongInfoContract;
import com.example.littletreemusic.presenter.community.SongInfoPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class SongInfoModule {

    SongInfoContract.ISongInfoView  iSongInfoView;
    SongInfoPresenter SongInfoPresenter;


    public SongInfoModule(SongInfoContract.ISongInfoView iSongInfoIview){
        this.iSongInfoView=iSongInfoIview;
        SongInfoPresenter=new SongInfoPresenter();
    }

    @PerFragment
    @Provides
    SongInfoContract.ISongInfoView provideISongInfoIview(){
        return iSongInfoView;
    }

    @PerFragment
    @Provides
    SongInfoPresenter provideSongInfoPresenter(){
        return SongInfoPresenter;
    }


}
