package com.example.littletreemusic.di.Component.community;


import com.example.littletreemusic.activity.community.RandomSongFragment;
import com.example.littletreemusic.di.Component.main.MainActivityComponent;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.RandomSongPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class, modules = RandomSongModule.class)
public interface RandomSongComponent {
    void inject(RandomSongFragment randomSongFragment);

    void inject(RandomSongPresenter randomSongPresenter);
    
}