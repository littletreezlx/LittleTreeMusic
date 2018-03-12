package com.example.littletreemusic.di.Component.community;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.RandomSongContract;
import com.example.littletreemusic.presenter.community.RandomSongPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class RandomSongModule {

    RandomSongContract.IRandomSongView  iRandomSongView;
    RandomSongPresenter randomSongPresenter;


    public RandomSongModule(RandomSongContract.IRandomSongView iRandomSongIview){
        this.iRandomSongView=iRandomSongIview;
        randomSongPresenter=new RandomSongPresenter();
    }

    @PerFragment
    @Provides
    RandomSongContract.IRandomSongView provideIRandomSongIview(){
        return iRandomSongView;
    }

    @PerFragment
    @Provides
    RandomSongPresenter provideRandomSongPresenter(){
        return randomSongPresenter;
    }


}
