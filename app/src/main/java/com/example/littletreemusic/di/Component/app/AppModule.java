package com.example.littletreemusic.di.Component.app;

import android.app.Application;

import com.example.littletreemusic.di.scopes.PerApp;
import com.example.littletreemusic.presenter.PicturePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

@Module
public class AppModule {

    PicturePresenter mPicturePresenter;
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
        mPicturePresenter=new PicturePresenter();
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
