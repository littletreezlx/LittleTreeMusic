package com.example.littletreemusic.di.Component.community;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.LiveContract;
import com.example.littletreemusic.presenter.community.LivePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class LiveModule {

    LiveContract.ILiveView  iLiveView;
    LivePresenter livePresenter;


    public LiveModule(LiveContract.ILiveView iLiveIview){
        this.iLiveView=iLiveIview;
        livePresenter=new LivePresenter();
    }

    @PerFragment
    @Provides
    LiveContract.ILiveView provideILiveIview(){
        return iLiveView;
    }

    @PerFragment
    @Provides
    LivePresenter provideLivePresenter(){
        return livePresenter;
    }


}
