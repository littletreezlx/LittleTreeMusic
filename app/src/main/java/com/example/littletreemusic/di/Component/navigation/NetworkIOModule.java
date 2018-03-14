package com.example.littletreemusic.di.Component.navigation;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.navigation.NetworkIOContract;
import com.example.littletreemusic.presenter.navigation.NetworkIOPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class NetworkIOModule {

    NetworkIOContract.INetworkIOView iNetworkIOView;
    NetworkIOPresenter networkIOPresenter;

    public NetworkIOModule(NetworkIOContract.INetworkIOView iNetworkIOView){
        this.iNetworkIOView=iNetworkIOView;
        networkIOPresenter=new NetworkIOPresenter();
    }

    @PerFragment
    @Provides
    NetworkIOPresenter provideNetworkIOPresenter(){
        return networkIOPresenter;
    }

    @PerFragment
    @Provides
    NetworkIOContract.INetworkIOView provideINetworkIOView(){
        return iNetworkIOView;
    }


}
