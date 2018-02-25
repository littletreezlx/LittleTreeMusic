package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavAlterPDContract;
import com.example.littletreemusic.presenter.navigation.NavAlterPDPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NavAlterPDModule {

    NavAlterPDContract.INavAlterPDView mINavAlterPDView;
    NavAlterPDPresenter mNavAlterPDPresenter;

    public NavAlterPDModule(NavAlterPDContract.INavAlterPDView iNavAlterPDView){
        mINavAlterPDView=iNavAlterPDView;
        mNavAlterPDPresenter=new NavAlterPDPresenter();
    }

    @PerFragment
    @Provides
    NavAlterPDPresenter provideNavAlterPDPresenter(){
        return mNavAlterPDPresenter;
    }

    @PerFragment
    @Provides
    NavAlterPDContract.INavAlterPDView provideINavAlterPDView(){
        return mINavAlterPDView;
    }


}
