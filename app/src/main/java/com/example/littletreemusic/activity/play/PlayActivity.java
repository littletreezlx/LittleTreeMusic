package com.example.littletreemusic.activity.play;

import android.os.Bundle;

import com.example.littletreemusic.R;
import com.example.littletreemusic.application.MyApplication;
import com.example.littletreemusic.base.BaseActivity;
import com.example.littletreemusic.di.Component.play.DaggerPlayActivityComponent;
import com.example.littletreemusic.di.Component.play.PlayActivityComponent;
import com.example.littletreemusic.di.Component.play.PlayActivityModule;
import com.example.littletreemusic.presenter.play.PlayFMPresenter;

import javax.inject.Inject;


public class PlayActivity extends BaseActivity{

    @Inject
    PlayFMPresenter playFMPresenter;

    PlayActivityComponent playActivityComponent;

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void setView() {
        setContentView(R.layout.play_activity);
    }

    @Override
    public void onCreateBusiness() {
        playActivityComponent= DaggerPlayActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .playActivityModule(new PlayActivityModule(this))
                .build();
        playActivityComponent.inject(this);
        playFMPresenter.init(this);
    }

    public PlayActivityComponent getPlayActivityComponent(){
        return playActivityComponent;
    }


}

