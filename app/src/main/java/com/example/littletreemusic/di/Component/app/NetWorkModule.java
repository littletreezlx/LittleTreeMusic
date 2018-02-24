package com.example.littletreemusic.di.Component.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.littletreemusic.di.scopes.PerApp;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

@Module
public class NetWorkModule {

    String mBaseUrl;

    public NetWorkModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }


    @Provides
    @PerApp
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("sp", Context.MODE_PRIVATE);
    }

    @Provides
    @PerApp
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

//    @Provides
//    @PerApp
//    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .baseUrl(mBaseUrl)
//                .client(okHttpClient)
//                .build();
//        return retrofit;

    @Provides
    @PerApp
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
//                    .client(okHttpClient)
                .build();
        return retrofit;
    }



}
