package com.example.littletreemusic.di.Component.play;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.play.PlayBottomContract;
import com.example.littletreemusic.presenter.play.PlayBottomPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class PlayBottomModule {

    PlayBottomContract.IPlayBottomView mIPlayBottomView;
    PlayBottomPresenter mPlayBottomPresenter;

    public PlayBottomModule(PlayBottomContract.IPlayBottomView iPlayBottomView){
        mIPlayBottomView=iPlayBottomView;
        mPlayBottomPresenter=new PlayBottomPresenter();
    }

    @PerFragment
    @Provides
    PlayBottomPresenter providePlayBottomPresenter(){
        return mPlayBottomPresenter;
    }

    @PerFragment
    @Provides
    PlayBottomContract.IPlayBottomView provideIPlayBottomView(){
        return mIPlayBottomView;
    }


}
