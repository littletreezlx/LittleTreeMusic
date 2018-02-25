package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavSettingContract;
import com.example.littletreemusic.presenter.navigation.NavSettingPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NavSettingModule {

    NavSettingContract.INavSettingView mINavSettingView;
    NavSettingPresenter mNavSettingPresenter;

    public NavSettingModule(NavSettingContract.INavSettingView iNavSettingView){
        mINavSettingView=iNavSettingView;
        mNavSettingPresenter=new NavSettingPresenter();
    }

    @PerFragment
    @Provides
    NavSettingPresenter provideNavSettingPresenter(){
        return mNavSettingPresenter;
    }

    @PerFragment
    @Provides
    NavSettingContract.INavSettingView provideINavSettingView(){
        return mINavSettingView;
    }


}
