package com.example.littletreemusic.di.Component.main;

import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.main.MainFMPresenter;
import com.example.littletreemusic.presenter.main.SearchSongPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class MainActivityModule {


    MainFMPresenter mMainFMPresenter;
    MainActivity mMainActivity;
    SearchSongPresenter mSearchSongPresenter;


    public MainActivityModule(MainActivity mainActivity){
        mMainActivity=mainActivity;
//        mMainFMPresenter=new MainFMPresenter(mainActivity.getFragmentManager(),
//                mainActivity.drawerLayout);
        mMainFMPresenter=new MainFMPresenter();
        mSearchSongPresenter=new SearchSongPresenter();

    }

    @PerActivity
    @Provides
    MainActivity provideMainActivity(){
        return mMainActivity;
    }

    @PerActivity
    @Provides
    MainFMPresenter provideMainFMPresenter(){
        return mMainFMPresenter;
    }

    @PerActivity
    @Provides
    SearchSongPresenter provideSearchSongPresenter(){
        return mSearchSongPresenter;
    }






}
