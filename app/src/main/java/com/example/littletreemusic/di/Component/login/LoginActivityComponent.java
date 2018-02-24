package com.example.littletreemusic.di.Component.login;


import android.app.Application;
import android.content.SharedPreferences;

import com.example.littletreemusic.activity.login.LoginFragment;
import com.example.littletreemusic.di.Module.LoginModule;
import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.LoginPresenter;
import com.google.gson.Gson;

import dagger.Subcomponent;
import retrofit2.Retrofit;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerActivity
@Subcomponent(modules = LoginModule.class)
public interface LoginActivityComponent {
    void inject(LoginFragment loginFragment);

    void inject(LoginPresenter loginPresenter);

    Application getApplication();
    SharedPreferences getSharedPreferences();
    Gson getGson();
    Retrofit getRetrofit();
}
