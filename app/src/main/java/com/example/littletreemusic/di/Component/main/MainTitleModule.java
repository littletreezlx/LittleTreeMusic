package com.example.littletreemusic.di.Component.main;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.main.MainTitleContract;
import com.example.littletreemusic.presenter.main.MainTitlePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class MainTitleModule {

    MainTitleContract.IMainTitleView mIMainTitleView;
    MainTitlePresenter mMainTitlePresenter;

    public MainTitleModule(MainTitleContract.IMainTitleView iMainTitleView){
        mIMainTitleView=iMainTitleView;
        mMainTitlePresenter=new MainTitlePresenter();
    }

    @PerFragment
    @Provides
    MainTitlePresenter provideMainTitlePresenter(){
        return mMainTitlePresenter;
    }

    @PerFragment
    @Provides
    MainTitleContract.IMainTitleView provideIMainTitleView(){
        return mIMainTitleView;
    }


}
