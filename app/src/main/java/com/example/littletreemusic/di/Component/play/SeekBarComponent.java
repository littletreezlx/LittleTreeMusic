package com.example.littletreemusic.di.Component.play;


import com.example.littletreemusic.activity.play.SeekBarFragment;
import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.play.SeekBarPresenter;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = PlayActivityComponent.class,modules = SeekBarModule.class)
public interface SeekBarComponent {
    void inject(SeekBarFragment seekBarFragment);

    void inject(SeekBarPresenter seekBarPresenter);
}
