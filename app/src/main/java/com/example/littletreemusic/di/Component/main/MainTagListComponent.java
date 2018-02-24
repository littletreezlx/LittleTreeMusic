package com.example.littletreemusic.di.Component.main;


import com.example.littletreemusic.activity.main.MainTagListFragment;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.main.TagListPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class,modules = MainTagListModule.class)
public interface MainTagListComponent {
    void inject(MainTagListFragment mainTagListFragment);

    void inject(TagListPresenter tagListPresenter);
}
