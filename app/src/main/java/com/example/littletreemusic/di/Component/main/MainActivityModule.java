package com.example.littletreemusic.di.Component.main;

import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.main.MainActivityContract;
import com.example.littletreemusic.presenter.main.MainActivityPresenter;
import com.example.littletreemusic.presenter.main.MainFMPresenter;
import com.example.littletreemusic.presenter.navigation.NavFMPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class MainActivityModule {


    NavFMPresenter mNavFMPresenter;
    MainFMPresenter mMainFMPresenter;
    MainActivity mMainActivity;
    MainActivityPresenter mMainActivityPresenter;
    MainActivityContract.IMainActivityView mIMainActivityView;


    public MainActivityModule(MainActivity mainActivity, MainActivityContract.IMainActivityView iMainActivityView){
        mIMainActivityView=iMainActivityView;
        mMainActivity=mainActivity;
//        mMainFMPresenter=new MainFMPresenter(mainActivity.getFragmentManager(),
//                mainActivity.drawerLayout);
        mNavFMPresenter=new NavFMPresenter();
        mMainFMPresenter=new MainFMPresenter();
        mMainActivityPresenter =new MainActivityPresenter();

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
    NavFMPresenter provideNavFMPresenter(){
        return mNavFMPresenter;
    }

    @PerActivity
    @Provides
    MainActivityPresenter provideMainActivityPresente(){
        return mMainActivityPresenter;
    }

    @PerActivity
    @Provides
    MainActivityContract.IMainActivityView provideIMainActivityView(){
        return mIMainActivityView;
    }




}
