package com.example.littletreemusic.presenter;

import android.graphics.Bitmap;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public interface PictureContract {

    interface IPictureView extends IView {
    }


    interface IPicturePresenter extends IPresenter<IPictureView> {

        Bitmap findBitmapByFilePath(String filePath);

    }
}
