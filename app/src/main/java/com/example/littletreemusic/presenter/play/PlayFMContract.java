package com.example.littletreemusic.presenter.play;

import android.content.Context;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

public interface PlayFMContract {


    interface IPlayFMView extends IView {
    }



    interface IPlayFMPresenter extends IPresenter<IPlayFMView> {

        void init(Context context);
    }

}
