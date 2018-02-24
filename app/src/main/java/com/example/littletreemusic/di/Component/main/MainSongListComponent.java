package com.example.littletreemusic.di.Component.main;


import com.example.littletreemusic.activity.main.MainSongListFragment;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.main.SongListPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class,modules = MainSongListModule.class)
public interface MainSongListComponent {

    void inject(SongListPresenter songListPresenter);

    void inject(MainSongListFragment mainSongListFragment);
}
