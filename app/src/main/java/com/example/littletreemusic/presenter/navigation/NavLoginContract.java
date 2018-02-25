package com.example.littletreemusic.presenter.navigation;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface NavLoginContract {

    interface INavLoginView extends IView {

        void showLoginSucceed();

        void showLoginFailedReason(String reason);

        void showProgressBar();
    }

    interface INavLoginPresenter extends IPresenter<INavLoginView> {

        void login(String account, String password, Boolean isChecked);


    }
}
