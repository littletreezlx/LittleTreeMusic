package com.example.littletreemusic.di.Component.play;


import com.example.littletreemusic.activity.play.ViewPagerFragment;
import com.example.littletreemusic.activity.play.ViewPagerFragment0;
import com.example.littletreemusic.activity.play.ViewPagerFragment1;
import com.example.littletreemusic.activity.play.ViewPagerFragment2;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.play.ViewPagerPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = PlayActivityComponent.class,modules = ViewPagerModule.class)
public interface ViewPagerComponent {
    void inject(ViewPagerFragment viewPagerFragment);

    void inject(ViewPagerPresenter viewPagerPresenter);

    void inject(ViewPagerFragment0 viewPagerFragment0);

    void inject(ViewPagerFragment1 viewPagerFragment1);

    void inject(ViewPagerFragment2 viewPagerFragment2);
}
