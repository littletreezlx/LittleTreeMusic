package com.example.littletreemusic.di.Component.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.littletreemusic.di.scopes.PerApp;
import com.example.littletreemusic.presenter.PicturePresenter;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.util.common.FileUtil;
import com.example.littletreemusic.util.common.NetworkUtil;
import com.example.littletreemusic.util.common.ToastUtil;
import com.google.gson.Gson;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by niuxiaowei on 16/3/19.
 */

@PerApp
@Component(modules={AppModule.class, MusicServiceModule.class,NetWorkModule.class})
public interface AppComponent {

//    void inject(MyApplication myApplication);
//
//    void inject(MusicService musicService);
//
//    void inject(MusicServicePresenter musicServicePresenter);




    Application getApplication();

    SharedPreferences getSharedPreferences();
    Gson getGson();
    Retrofit getRetrofit();
    PicturePresenter getPicturePresenter();

    MusicService getMusicService();
    NetworkUtil getNetworkUtil();
    ToastUtil getToastUtil();
    FileUtil getFileUtil();

//    MainActivityComponent newMainActivityComponent();
//
//    ChangeBPActivityComponent newActivityComponent();
//
//    PlayActivityComponent newPlayComponent();
//
//    LoginActivityComponent newLoginComponent();


}
