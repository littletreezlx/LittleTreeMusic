package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavLoginContract;
import com.example.littletreemusic.presenter.navigation.NavLoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NavLoginModule {

    NavLoginContract.INavLoginView mINavLoginView;
    NavLoginPresenter mNavLoginPresenter;

    public NavLoginModule(NavLoginContract.INavLoginView iNavLoginView){
        mINavLoginView=iNavLoginView;
        mNavLoginPresenter=new NavLoginPresenter();
    }

    @PerFragment
    @Provides
    NavLoginPresenter provideNavLoginPresenter(){
        return mNavLoginPresenter;
    }

    @PerFragment
    @Provides
    NavLoginContract.INavLoginView provideINavLoginView(){
        return mINavLoginView;
    }


}
