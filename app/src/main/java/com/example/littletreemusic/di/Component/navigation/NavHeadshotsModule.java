package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NavHeadshotsContract;
import com.example.littletreemusic.presenter.navigation.NavHeadshotsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NavHeadshotsModule {

    NavHeadshotsContract.INavHeadshotsView mINavHeadshotsView;
    NavHeadshotsPresenter mNavHeadshotsPresenter;

    public NavHeadshotsModule(NavHeadshotsContract.INavHeadshotsView iNavHeadshotsView){
        mINavHeadshotsView=iNavHeadshotsView;
        mNavHeadshotsPresenter=new NavHeadshotsPresenter();
    }

    @PerFragment
    @Provides
    NavHeadshotsPresenter provideNavHeadshotsPresenter(){
        return mNavHeadshotsPresenter;
    }

    @PerFragment
    @Provides
    NavHeadshotsContract.INavHeadshotsView provideINavHeadshotsView(){
        return mINavHeadshotsView;
    }


}
