package com.example.littletreemusic.presenter.community;

import com.example.littletreemusic.pojo.ServerSong;
import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

import java.util.List;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface RandomSongContract {

    interface IRandomSongView extends IView {
        void fillAdapter(List<ServerSong> serverSongs);

        void showList();
    }

    interface IRandomSongPresenter extends IPresenter<IRandomSongView> {
        void getRandomSongs();
    }
}
