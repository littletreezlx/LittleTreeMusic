package com.example.littletreemusic.presenter.play;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface SeekBarContract {

    interface ISeekBarView extends IView {
    }


    interface ISeekBarPresenter extends IPresenter<ISeekBarView> {

    }
}
