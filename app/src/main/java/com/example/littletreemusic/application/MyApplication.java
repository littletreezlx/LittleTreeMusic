package com.example.littletreemusic.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.littletreemusic.di.Component.app.AppComponent;
import com.example.littletreemusic.di.Component.app.AppModule;
import com.example.littletreemusic.di.Component.app.DaggerAppComponent;
import com.example.littletreemusic.di.Component.app.MusicServiceModule;
import com.example.littletreemusic.di.Component.app.NetWorkModule;
import com.example.littletreemusic.service.MusicService;

import org.litepal.LitePal;

public class MyApplication extends Application {


    static AppComponent mAppComponent;
    ServiceConnection serviceConnection;
    MusicService musicService;
    Intent serviceIntent;
    String url="http://192.168.2.172:8080/";


    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        serviceIntent = new Intent(this,MusicService.class);
        serviceIntent.putExtra("mode","app");
        getApplicationContext().startService(serviceIntent);
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService = ((MusicService.MusicBinder) service).getService();
                mAppComponent = DaggerAppComponent.builder()
                        .appModule(new AppModule(MyApplication.this))
                        .netWorkModule(new NetWorkModule(url))
                        .musicServiceModule(new MusicServiceModule(musicService))
                        .build();
//                mAppComponent.inject(MyApplication.this);
//                mAppComponent.inject(musicServicePresenter);
//                mAppComponent.inject(musicService);

            }

            @Override
            public void onServiceDisconnected (ComponentName name){
            }
        };
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(getApplicationContext().toString(), "onTerminate");
        Log.d("app", musicService.toString());
        unbindService(serviceConnection);
        stopService(serviceIntent);

    }

    public static AppComponent getAppComponent(){
        return  mAppComponent;
    }


}
