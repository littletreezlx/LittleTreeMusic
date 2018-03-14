package com.example.littletreemusic.di.Component.app;

import android.app.Application;

import com.example.littletreemusic.di.scopes.PerApp;
import com.example.littletreemusic.presenter.PicturePresenter;
import com.example.littletreemusic.util.common.FileUtil;
import com.example.littletreemusic.util.common.NetworkUtil;
import com.example.littletreemusic.util.common.ToastUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

@Module
public class AppModule {

    PicturePresenter picturePresenter;
    Application application;
    NetworkUtil networkUtil;
    ToastUtil toastUtil;
    FileUtil fileUtil;

    public AppModule(Application application) {
        this.application = application;
        picturePresenter=new PicturePresenter();
        networkUtil=new NetworkUtil(application);
        toastUtil=new ToastUtil(application);
        fileUtil=new FileUtil(application);

    }

    @Provides
    @PerApp
    NetworkUtil networkUtil() {
        return networkUtil;
    }

    @Provides
    @PerApp
    ToastUtil provideToastUtil() {
        return toastUtil;
    }

    @Provides
    @PerApp
    FileUtil provideFileUtil() {
        return fileUtil;
    }

    @Provides
    @PerApp
    Application provideApplication() {
        return application;
    }

    @Provides
    @PerApp
    PicturePresenter providePicturePresenter() {
        return picturePresenter;
    }





}
