package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavChangeBPContract;
import com.example.littletreemusic.presenter.navigation.NavChangeBPPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NavChangeBPModule {

    NavChangeBPContract.INavChangeBPView mINavChangeBPView;
    NavChangeBPPresenter mNavChangeBPPresenter;

    public NavChangeBPModule(NavChangeBPContract.INavChangeBPView iNavChangeBPView){
        mINavChangeBPView=iNavChangeBPView;
        mNavChangeBPPresenter=new NavChangeBPPresenter();
    }

    @PerFragment
    @Provides
    NavChangeBPPresenter provideNavChangeBPPresenter(){
        return mNavChangeBPPresenter;
    }

    @PerFragment
    @Provides
    NavChangeBPContract.INavChangeBPView provideINavChangeBPView(){
        return mINavChangeBPView;
    }


}
