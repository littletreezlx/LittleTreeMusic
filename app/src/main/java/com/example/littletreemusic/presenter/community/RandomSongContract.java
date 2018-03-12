package com.example.littletreemusic.presenter.community;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface RandomSongContract {

    interface IRandomSongView extends IView {
        void fillAdapter();
    }

    interface IRandomSongPresenter extends IPresenter<IRandomSongView> {
        void getRandomSongs();
    }
}
