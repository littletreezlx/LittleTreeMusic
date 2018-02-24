package com.example.littletreemusic.di.Component.play;


import com.example.littletreemusic.activity.play.PlayTitleFragment;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.play.PlayTitlePresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = PlayActivityComponent.class,modules = PlayTitleModule.class)
public interface PlayTitleComponent {
    void inject(PlayTitleFragment playTitleFragment);

    void inject(PlayTitlePresenter playTitlePresenter);
}
