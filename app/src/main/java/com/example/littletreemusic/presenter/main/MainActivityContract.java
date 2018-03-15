package com.example.littletreemusic.presenter.main;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface MainActivityContract {

    interface IMainActivityView extends IView {

        void autoLoginSucceed();

        void autoLoginFaided();

        void showWaitingView();

        void cancelWaitingView();
    }


    interface IMainActivityPresenter extends IPresenter<IMainActivityView> {

        void searchSongs();

        void autoLogin();
        
    }
}
