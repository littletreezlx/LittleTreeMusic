package com.example.littletreemusic.di.Component.app;

import com.example.littletreemusic.di.scopes.PerApp;
import com.example.littletreemusic.service.MusicService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

@Module
public class MusicServiceModule {

    MusicService mMusicService;
//    MusicServicePresenter mMusicServicePresenter;

    public MusicServiceModule(MusicService musicService) {
        mMusicService=musicService;
//        mMusicServicePresenter=new MusicServicePresenter();
    }

    @Provides
    @PerApp
    MusicService provideMusicService() {
        return mMusicService;
    }

//    @Provides
//    @PerApp
//    MusicServicePresenter provideMusicServicePresenter() {
//        return mMusicServicePresenter;
//    }

}
