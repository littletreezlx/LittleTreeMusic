package com.example.littletreemusic.di.Component.play;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.play.ViewPagerContract;
import com.example.littletreemusic.presenter.play.ViewPagerPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class ViewPagerModule {

    ViewPagerContract.IViewPagerView mIViewPagerView;
    ViewPagerPresenter mViewPagerPresenter;

    public ViewPagerModule(ViewPagerContract.IViewPagerView iViewPagerView){
        mIViewPagerView=iViewPagerView;
        mViewPagerPresenter=new ViewPagerPresenter();
    }

    @PerFragment
    @Provides
    ViewPagerPresenter provideViewPagerPresenter(){
        return mViewPagerPresenter;
    }

    @PerFragment
    @Provides
    ViewPagerContract.IViewPagerView provideIViewPagerView(){
        return mIViewPagerView;
    }


}
