package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavPersonalInfoContract;
import com.example.littletreemusic.presenter.navigation.NavPersonalInfoPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NavPersonalInfoModule {

    NavPersonalInfoContract.INavPersonalInfoView mINavPersonalInfoView;
    NavPersonalInfoPresenter mNavPersonalInfoPresenter;

    public NavPersonalInfoModule(NavPersonalInfoContract.INavPersonalInfoView iNavPersonalInfoView){
        mINavPersonalInfoView=iNavPersonalInfoView;
        mNavPersonalInfoPresenter=new NavPersonalInfoPresenter();
    }

    @PerFragment
    @Provides
    NavPersonalInfoPresenter provideNavPersonalInfoPresenter(){
        return mNavPersonalInfoPresenter;
    }

    @PerFragment
    @Provides
    NavPersonalInfoContract.INavPersonalInfoView provideINavPersonalInfoView(){
        return mINavPersonalInfoView;
    }


}
