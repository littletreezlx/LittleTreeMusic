package com.example.littletreemusic.presenter.navigation;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface NavFMContract {

    interface INavFMView extends IView {
    }

    interface INavFMPresenter extends IPresenter<INavFMView> {


        void toNavHeadshots();
        void fromNavHeadshots();

        void toNavPersonInfo();
        void fromNavPersonInfo();

        void toNavChangeBP();
        void fromNavChangeBP();

//        void toNavSleep();
//        void fromNavSleep();

        void toNavAbout();
        void fromNavAbout();

        void toNavSetting();
        void fromNavSetting();

        void toNavLogin();
        void FromNavLogin();

        void toNavRegister();
        void FromNavRegister();

        void toNavAlterPD();
        void FromNavAlterPD();

        void backPressed();

    }
}
