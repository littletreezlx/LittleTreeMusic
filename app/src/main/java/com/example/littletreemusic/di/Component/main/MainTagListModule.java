package com.example.littletreemusic.di.Component.main;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.main.TagListContract;
import com.example.littletreemusic.presenter.main.TagListPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/22.
 */

@Module
public class MainTagListModule {

    TagListContract.ITagListView mITagListIview;
    TagListPresenter mTagListPresenter;


    public  MainTagListModule(TagListContract.ITagListView iTagListIview){
        mITagListIview=iTagListIview;
        mTagListPresenter=new TagListPresenter();
    }

    @PerFragment
    @Provides
    TagListContract.ITagListView provideITagListIview(){
        return mITagListIview;
    }

    @PerFragment
    @Provides
    TagListPresenter provideTagListPresenter(){
        return mTagListPresenter;
    }
}
