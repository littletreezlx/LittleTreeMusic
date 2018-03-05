package com.example.littletreemusic.presenter.community;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface CommunityFMContract {

    interface ICommunityFMView extends IView {
    }

    interface ICommunityFMPresenter extends IPresenter<ICommunityFMView> {

        void init();

        void backPressed();

    }
}
