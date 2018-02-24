package com.example.littletreemusic.presenter.main;

import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

import java.util.List;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface SongListContract {

    interface ISongListView extends IView {

        void showPlayingSong(int position);

    }


    interface ISongListPresenter extends IPresenter<ISongListView> {

        List<Song> findSongListByTagPosition(int position);

        List<Song> filterData(String filterStr);



    }
}
