package com.example.littletreemusic.di.Component.app;

import android.app.Application;

import com.example.littletreemusic.di.scopes.PerApp;
import com.example.littletreemusic.presenter.PicturePresenter;
import com.example.littletreemusic.util.NetworkUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

@Module
public class AppModule {

    PicturePresenter mPicturePresenter;
    Application mApplication;
    NetworkUtil mNetworkUtil;

    public AppModule(Application application) {
        mApplication = application;
        mPicturePresenter=new PicturePresenter();
        mNetworkUtil=new NetworkUtil(application);
    }

    @Provides
    @PerApp
    NetworkUtil networkUtil(Application application) {
        return mNetworkUtil;
    }

    @Provides
    @PerApp
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @PerApp
    PicturePresenter providePicturePresenter() {
        return mPicturePresenter;
    }





}
