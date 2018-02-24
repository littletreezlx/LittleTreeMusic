package com.example.littletreemusic.presenter.main;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

import java.util.List;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface TagListContract {

    interface ITagListView extends IView {


    }


    interface ITagListPresenter extends IPresenter<ITagListView> {

//        void init();

        List<String> getTagList();


    }
}
