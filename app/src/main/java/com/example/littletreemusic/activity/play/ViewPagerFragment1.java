package com.example.littletreemusic.activity.play;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.presenter.PicturePresenter;
import com.example.littletreemusic.service.MusicService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class ViewPagerFragment1 extends Fragment {


    @BindView(R.id.viewpager_album)
    ImageView iv_album;
    Unbinder unbinder;

    @Inject
    PicturePresenter picturePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_play_viewpager1, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        updateAlbum();
        return view;
    }

    public void updateAlbum() {
        List<Song> playingList = DataSupport.where("uri=?", MusicService.playingUriStr).find(Song.class);
        if (playingList != null && playingList.size() != 0) {
            Song playingSong = playingList.get(0);
            String uriStr = playingSong.getUri();
            Bitmap bitmap = picturePresenter.findBitmapByFilePath(uriStr);
            if (bitmap != null){
                iv_album.setImageBitmap(bitmap);
            }
        }
    }

    @Subscribe
    public void onEvent(Song song){
        Bitmap bitmap = picturePresenter.findBitmapByFilePath(song.getUri());
        if (bitmap != null){
            iv_album.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

}
