package com.example.littletreemusic.di.Component.community;


import com.example.littletreemusic.activity.community.TopSongFragment;
import com.example.littletreemusic.di.Component.main.MainActivityComponent;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.TopSongPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class, modules = TopSongModule.class)
public interface TopSongComponent {
    void inject(TopSongFragment topSongFragment);

    void inject(TopSongPresenter topSongPresenter);
    
}