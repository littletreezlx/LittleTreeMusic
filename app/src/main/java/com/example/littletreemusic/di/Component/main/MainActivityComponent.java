package com.example.littletreemusic.di.Component.main;


import android.app.Application;
import android.content.SharedPreferences;

import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.di.Component.app.AppComponent;
import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.PicturePresenter;
import com.example.littletreemusic.presenter.community.CommunityFMPresenter;
import com.example.littletreemusic.presenter.main.MainActivityPresenter;
import com.example.littletreemusic.presenter.main.MainFMPresenter;
import com.example.littletreemusic.presenter.navigation.NavFMPresenter;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.util.common.FileUtil;
import com.example.littletreemusic.util.common.NetworkUtil;
import com.example.littletreemusic.util.common.ToastUtil;
import com.google.gson.Gson;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerActivity
@Component(dependencies = AppComponent.class,modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(MainFMPresenter mainFMPresenter);

    void inject(CommunityFMPresenter communityFMPresenter);

    void inject(NavFMPresenter navFMPresenter);

    void inject(MainActivityPresenter mainActivityPresenter);

    Application getApplication();
    SharedPreferences getSharedPreferences();
    Gson getGson();
    Retrofit getRetrofit();
    PicturePresenter getPicturePresenter();
    MusicService getMusicService();
    NetworkUtil getNetworkUtil();
    ToastUtil getToastUtil();
    FileUtil getFileUtil();



    MainFMPresenter getMainFMPresenter();
    CommunityFMPresenter getCommunityFMPresenter();
    NavFMPresenter getNavFMPresenter();
    MainActivity getMainActivity();
    MainActivityPresenter getSearchSongPresenter();

}
