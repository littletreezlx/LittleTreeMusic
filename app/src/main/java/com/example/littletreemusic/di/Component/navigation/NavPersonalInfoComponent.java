package com.example.littletreemusic.di.Component.navigation;


import com.example.littletreemusic.activity.navigation.NavPersonalInfoFragment;
import com.example.littletreemusic.di.Component.main.MainActivityComponent;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavPersonalInfoPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class,modules = NavPersonalInfoModule.class)
public interface NavPersonalInfoComponent {

    void inject(NavPersonalInfoFragment navPersonalInfoFragment);

    void inject(NavPersonalInfoPresenter navPersonalInfoPresenter);

}
