package com.example.littletreemusic.di.Component.community;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.RandomPictureContract;
import com.example.littletreemusic.presenter.community.RandomPicturePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class RandomPictureModule {

    RandomPictureContract.IRandomPictureView  iRandomPictureView;
    RandomPicturePresenter randomPicturePresenter;


    public RandomPictureModule(RandomPictureContract.IRandomPictureView iRandomPictureIview){
        this.iRandomPictureView=iRandomPictureIview;
        randomPicturePresenter=new RandomPicturePresenter();
    }

    @PerFragment
    @Provides
    RandomPictureContract.IRandomPictureView provideIRandomPictureIview(){
        return iRandomPictureView;
    }

    @PerFragment
    @Provides
    RandomPicturePresenter provideRandomPicturePresenter(){
        return randomPicturePresenter;
    }


}
