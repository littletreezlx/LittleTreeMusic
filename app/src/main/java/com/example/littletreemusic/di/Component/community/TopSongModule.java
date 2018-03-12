package com.example.littletreemusic.di.Component.community;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.TopSongContract;
import com.example.littletreemusic.presenter.community.TopSongPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class TopSongModule {

    TopSongContract.ITopSongView  iTopSongView;
    TopSongPresenter topSongPresenter;


    public TopSongModule(TopSongContract.ITopSongView iTopSongIview){
        this.iTopSongView=iTopSongIview;
        topSongPresenter=new TopSongPresenter();
    }

    @PerFragment
    @Provides
    TopSongContract.ITopSongView provideITopSongIview(){
        return iTopSongView;
    }

    @PerFragment
    @Provides
    TopSongPresenter provideTopSongPresenter(){
        return topSongPresenter;
    }


}
