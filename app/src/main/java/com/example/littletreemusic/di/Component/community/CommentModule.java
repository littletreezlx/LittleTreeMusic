package com.example.littletreemusic.di.Component.community;

import com.example.littletreemusic.di.scopes.PerFragment;
import com.example.littletreemusic.presenter.community.CommentContract;
import com.example.littletreemusic.presenter.community.CommentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/21.
 */


@Module
public class CommentModule {

    CommentContract.ICommentView  iCommentView;
    CommentPresenter commentPresenter;


    public CommentModule(CommentContract.ICommentView iCommentIview){
        this.iCommentView=iCommentIview;
        commentPresenter=new CommentPresenter();
    }

    @PerFragment
    @Provides
    CommentContract.ICommentView provideICommentIview(){
        return iCommentView;
    }

    @PerFragment
    @Provides
    CommentPresenter provideCommentPresenter(){
        return commentPresenter;
    }


}
