package com.example.littletreemusic.presenter.navigation;

import com.example.littletreemusic.pojo.RegisterMsg;
import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface NavRegisterContract {

    interface INavRegisterView extends IView {

        void showRegisterSucceed();

        void showRegisterFailedReason(String reason);

        void showProgressBar();
    }

    interface INavRegisterPresenter extends IPresenter<INavRegisterView> {

        void register(RegisterMsg registerMsg);


    }
}
