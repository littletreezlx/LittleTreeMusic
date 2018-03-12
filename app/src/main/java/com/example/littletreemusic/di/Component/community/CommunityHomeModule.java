package com.example.littletreemusic.di.Component.community;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.CommunityHomeContract;
import com.example.littletreemusic.presenter.community.CommunityHomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class CommunityHomeModule {

    CommunityHomeContract.ICommunityHomeView  iCommunityHomeView;
    CommunityHomePresenter communityHomePresenter;


    public CommunityHomeModule(CommunityHomeContract.ICommunityHomeView iCommunityHomeIview){
        this.iCommunityHomeView=iCommunityHomeIview;
        communityHomePresenter=new CommunityHomePresenter();
    }

    @PerFragment
    @Provides
    CommunityHomeContract.ICommunityHomeView provideICommunityHomeIview(){
        return iCommunityHomeView;
    }

    @PerFragment
    @Provides
    CommunityHomePresenter provideCommunityHomePresenter(){
        return communityHomePresenter;
    }


}
