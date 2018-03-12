package com.example.littletreemusic.di.Component.main;

import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.community.CommunityFMPresenter;
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


    NavFMPresenter navFMPresenter;
    CommunityFMPresenter communityFMPresenter;
    MainFMPresenter mainFMPresenter;
    MainActivity mainActivity;
    MainActivityPresenter mainActivityPresenter;
    MainActivityContract.IMainActivityView iMainActivityView;


    public MainActivityModule(MainActivity mainActivity, MainActivityContract.IMainActivityView iMainActivityView){
        this.iMainActivityView=iMainActivityView;
        this.mainActivity=mainActivity;
//        mMainFMPresenter=new MainFMPresenter(mainActivity.getFragmentManager(),
//                mainActivity.drawerLayout);
        navFMPresenter=new NavFMPresenter();
        mainFMPresenter=new MainFMPresenter();
        communityFMPresenter=new CommunityFMPresenter();
        mainActivityPresenter =new MainActivityPresenter();

    }

    @PerActivity
    @Provides
    MainActivity provideMainActivity(){
        return mainActivity;
    }

    @PerActivity
    @Provides
    MainFMPresenter provideMainFMPresenter(){
        return mainFMPresenter;
    }

    @PerActivity
    @Provides
    CommunityFMPresenter provideCommunityFMPresenter(){
        return communityFMPresenter;
    }

    @PerActivity
    @Provides
    NavFMPresenter provideNavFMPresenter(){
        return navFMPresenter;
    }

    @PerActivity
    @Provides
    MainActivityPresenter provideMainActivityPresente(){
        return mainActivityPresenter;
    }

    @PerActivity
    @Provides
    MainActivityContract.IMainActivityView provideIMainActivityView(){
        return iMainActivityView;
    }




}
