package com.example.littletreemusic.di.Component.navigation;


import com.example.littletreemusic.activity.navigation.NavHeadshotsFragment;
import com.example.littletreemusic.di.Component.main.MainActivityComponent;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavHeadshotsPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class,modules = NavHeadshotsModule.class)
public interface NavHeadshotsComponent {

    void inject(NavHeadshotsFragment navHeadshotsFragment);

    void inject(NavHeadshotsPresenter navHeadshotsPresenter);

}
