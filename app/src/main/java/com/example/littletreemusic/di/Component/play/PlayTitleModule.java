package com.example.littletreemusic.di.Component.play;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.play.PlayTitleContract;
import com.example.littletreemusic.presenter.play.PlayTitlePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class PlayTitleModule {

    PlayTitleContract.IPlayTitleView mIPlayTitleView;
    PlayTitlePresenter mPlayTitlePresenter;

    public PlayTitleModule(PlayTitleContract.IPlayTitleView iPlayTitleView){
        mIPlayTitleView=iPlayTitleView;
        mPlayTitlePresenter=new PlayTitlePresenter();
    }

    @PerFragment
    @Provides
    PlayTitlePresenter providePlayTitlePresenter(){
        return mPlayTitlePresenter;
    }

    @PerFragment
    @Provides
    PlayTitleContract.IPlayTitleView provideIPlayTitleView(){
        return mIPlayTitleView;
    }


}
