package com.example.littletreemusic.di.Component.play;

import com.example.littletreemusic.activity.play.PlayActivity;
import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.play.PlayFMPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class PlayActivityModule {


    PlayFMPresenter mPlayFMPresenter;
    PlayActivity mPlayActivity;


    public PlayActivityModule(PlayActivity playActivity){
        mPlayActivity=playActivity;
        mPlayFMPresenter=new PlayFMPresenter();
    }

    @PerActivity
    @Provides
    PlayActivity providePlayActivity(){
        return mPlayActivity;
    }

    @PerActivity
    @Provides
    PlayFMPresenter providePlayFMPresenter(){
        return mPlayFMPresenter;
    }




}
