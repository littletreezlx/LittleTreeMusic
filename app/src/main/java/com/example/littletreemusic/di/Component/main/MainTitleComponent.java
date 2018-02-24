package com.example.littletreemusic.di.Component.main;


import com.example.littletreemusic.activity.main.MainTitleFragment;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.main.MainTitlePresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class,modules = MainTitleModule.class)
public interface MainTitleComponent {


    void inject(MainTitleFragment mainTitleFragment);

    void inject(MainTitlePresenter mainTitlePresenter);


}
