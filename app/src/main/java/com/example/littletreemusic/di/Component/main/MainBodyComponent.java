package com.example.littletreemusic.di.Component.main;


import com.example.littletreemusic.activity.main.MainBodyFragment;
import com.example.littletreemusic.di.scopes.PerFragment;

import dagger.Component;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */


@PerFragment
@Component(dependencies = MainActivityComponent.class)
public interface MainBodyComponent {
    void inject(MainBodyFragment mainBodyFragment);
}
