package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavRegisterContract;
import com.example.littletreemusic.presenter.navigation.NavRegisterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NavRegisterModule {

    NavRegisterContract.INavRegisterView mINavRegisterView;
    NavRegisterPresenter mNavRegisterPresenter;

    public NavRegisterModule(NavRegisterContract.INavRegisterView iNavRegisterView){
        mINavRegisterView=iNavRegisterView;
        mNavRegisterPresenter=new NavRegisterPresenter();
    }

    @PerFragment
    @Provides
    NavRegisterPresenter provideNavRegisterPresenter(){
        return mNavRegisterPresenter;
    }

    @PerFragment
    @Provides
    NavRegisterContract.INavRegisterView provideINavRegisterView(){
        return mINavRegisterView;
    }


}
