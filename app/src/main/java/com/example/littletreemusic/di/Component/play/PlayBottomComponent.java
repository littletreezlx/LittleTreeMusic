package com.example.littletreemusic.di.Component.play;


import com.example.littletreemusic.activity.play.PlayBottomFragment;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.play.PlayBottomPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = PlayActivityComponent.class,modules = PlayBottomModule.class)
public interface PlayBottomComponent {
    void inject(PlayBottomFragment playBottomFragment);

    void inject(PlayBottomPresenter playBottomPresenter);

}
