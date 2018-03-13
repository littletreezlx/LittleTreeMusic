package com.example.littletreemusic.di.Component.play;


import android.app.Application;
import android.content.SharedPreferences;

import com.example.littletreemusic.activity.play.PlayActivity;
import com.example.littletreemusic.di.Component.app.AppComponent;
import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.PicturePresenter;
import com.example.littletreemusic.presenter.play.PlayFMPresenter;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.util.common.NetworkUtil;
import com.example.littletreemusic.util.common.ToastUtil;
import com.google.gson.Gson;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerActivity
@Component(dependencies = AppComponent.class,modules = PlayActivityModule.class)
public interface PlayActivityComponent {

    void inject(PlayActivity playActivity);

    void inject(PlayFMPresenter playFMPresenter);

    Application getApplication();
    SharedPreferences getSharedPreferences();
    Gson getGson();
    Retrofit getRetrofit();
    PicturePresenter getPicturePresenter();
    MusicService getMusicService();
    NetworkUtil getNetworkUtil();
    ToastUtil getToastUtil();


    PlayFMPresenter getPlayFMPresenter();
    PlayActivity getPlayActivity();

}
