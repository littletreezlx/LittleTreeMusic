package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavAboutContract;
import com.example.littletreemusic.presenter.navigation.NavAboutPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NavAboutModule {

    NavAboutContract.INavAboutView mINavAboutView;
    NavAboutPresenter mNavAboutPresenter;

    public NavAboutModule(NavAboutContract.INavAboutView iNavAboutView){
        mINavAboutView=iNavAboutView;
        mNavAboutPresenter=new NavAboutPresenter();
    }

    @PerFragment
    @Provides
    NavAboutPresenter provideNavAboutPresenter(){
        return mNavAboutPresenter;
    }

    @PerFragment
    @Provides
    NavAboutContract.INavAboutView provideINavAboutView(){
        return mINavAboutView;
    }


}
