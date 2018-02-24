package com.example.littletreemusic.presenter.main;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface SearchSongContract {

    interface ISearchSongView extends IView {

    }


    interface ISearchSongPresenter extends IPresenter<ISearchSongView> {

        void searchSongs();
        
    }
}
