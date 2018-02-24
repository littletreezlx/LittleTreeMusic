package com.example.littletreemusic.di.Component.changebp;


import android.app.Application;
import android.content.SharedPreferences;

import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.PicturePresenter;
import com.example.littletreemusic.service.MusicService;
import com.google.gson.Gson;

import dagger.Subcomponent;
import retrofit2.Retrofit;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerActivity
@Subcomponent
public interface ChangeBPActivityComponent {
    void inject(ChangeBPActivityComponent changeBPActivityComponent);

    Application getApplication();

    SharedPreferences getSharedPreferences();
    Gson getGson();
    Retrofit getRetrofit();
    PicturePresenter getPicturePresenter();

    MusicService getMusicService();
}
