package com.example.littletreemusic.di.Component.play;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.play.SeekBarContract;
import com.example.littletreemusic.presenter.play.SeekBarPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class SeekBarModule {

    SeekBarContract.ISeekBarView mISeekBarView;
    SeekBarPresenter mSeekBarPresenter;

    public SeekBarModule(SeekBarContract.ISeekBarView iSeekBarView){
        mISeekBarView=iSeekBarView;
        mSeekBarPresenter=new SeekBarPresenter();
    }

    @PerFragment
    @Provides
    SeekBarPresenter provideSeekBarPresenter(){
        return mSeekBarPresenter;
    }

    @PerFragment
    @Provides
    SeekBarContract.ISeekBarView provideISeekBarView(){
        return mISeekBarView;
    }


}
