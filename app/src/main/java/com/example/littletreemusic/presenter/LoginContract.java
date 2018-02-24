package com.example.littletreemusic.presenter;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

public interface LoginContract {

    interface ILoginView extends IView{

        void showLoginSucceed();

        void showLoginFailedReason();

        void showProgressBar();

    }

    interface ILoginPresenter extends IPresenter<ILoginView>{


        void login(String account, String password);

        void toRegisterFragment();

        void toAlterPasswordFragment();
    }

}
