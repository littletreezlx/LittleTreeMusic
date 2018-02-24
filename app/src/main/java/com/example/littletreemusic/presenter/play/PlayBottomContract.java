package com.example.littletreemusic.presenter.play;

import com.example.littletreemusic.presenter.IPresenter;
import com.example.littletreemusic.presenter.IView;

import java.util.List;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface PlayBottomContract {

    interface IPlayBottomView extends IView {

        void showTaglistDialog(List<String> tagList);

        void showInputDialog();

        void showConfirmDialog();

        void updateTagListDialog(List<String> tagList);
    }


    interface IPlayBottomPresenter extends IPresenter<IPlayBottomView> {

    }

}
