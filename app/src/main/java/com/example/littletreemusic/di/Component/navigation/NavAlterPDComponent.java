package com.example.littletreemusic.di.Component.navigation;


import com.example.littletreemusic.activity.navigation.NavAlterPDFragment;
import com.example.littletreemusic.di.Component.main.MainActivityComponent;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavAlterPDPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class,modules = NavAlterPDModule.class)
public interface NavAlterPDComponent {

    void inject(NavAlterPDFragment navAlterPDFragment);

    void inject(NavAlterPDPresenter navAlterPDPresenter);

}
