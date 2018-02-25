package com.example.littletreemusic.presenter.navigation;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface NavSettingContract {

    interface INavSettingView extends IView {
    }

    interface INavSettingPresenter extends IPresenter<INavSettingView> {

    }
}
