package com.example.littletreemusic.presenter.main;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface MainTitleContract {

    interface IMainTitleView extends IView {
        void setTitle(String title);
    }


    interface IMainTitlePresenter extends IPresenter<IMainTitleView> {
        void back();

        void search();
    }
}
