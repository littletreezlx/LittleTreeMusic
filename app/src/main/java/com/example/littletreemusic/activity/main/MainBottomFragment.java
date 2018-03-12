package com.example.littletreemusic.activity.main;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.play.PlayActivity;
import com.example.littletreemusic.di.Component.main.DaggerMainBottomComponent;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.pojo.StringEvent;
import com.example.littletreemusic.presenter.PicturePresenter;
import com.example.littletreemusic.service.MusicService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/8/29.
 */

public class MainBottomFragment extends Fragment {

    @BindView(R.id.main_bottom_btnAlbum)
    ImageButton btn_Album;
    @BindView(R.id.main_bottom_tvtitle)
    TextView tv_Title;
    @BindView(R.id.main_bottom_tvartist)
    TextView tv_Artist;
    @BindView(R.id.main_bottom_imgpause)
    ImageButton btn_Pause;
    @BindView(R.id.main_bottom_imgstart)
    ImageButton btn_Start;
    @BindView(R.id.main_bottom_imgnext)
    ImageButton btn_Next;
    @BindView(R.id.main_bottom_imgmenu)
    ImageButton btn_Menu;
    Unbinder unbinder;

    @Inject
    SharedPreferences sp;
    @Inject
    PicturePresenter picturePresenter;
    @Inject
    MusicService musicService;
    @Inject
    MainActivity mainActivity;

    RelativeLayout mbottomtemp;
    Song playingSong;
    boolean isVisible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        DaggerMainBottomComponent.builder()
                .mainActivityComponent(((MainActivity) getActivity()).getMainActivityComponent())
                .build().inject(this);
        View view = inflater.inflate(R.layout.main_bottom, container, false);
        mbottomtemp = (RelativeLayout) view.findViewById(R.id.main_bottom);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        updateAll();
        return view;
    }

//    读取上次关闭时的歌
//    public void initLastSongText(){
//        String lastTitle=sp.getString("playing_Title","no");
//        String lastArtist=sp.getString("playing_Artist","no");
//        String lastUri=sp.getString("playing_Uri","no");
//        if(!lastTitle.equals("no")) {
//            tv_Title.setText(lastTitle);
//            tv_Artist.setText(lastArtist);
//            Bitmap bitmap = picturePresenter.findBitmapByFilePath(playingSong.getUri());
//            if (bitmap != null) {
//                btn_Album.setImageBitmap(bitmap);
//            }
//        }
//    }

    //更新歌名，歌手,封面
    public void updateAll() {
        if (playingSong != null) {
            tv_Title.setText(playingSong.getTitle());
            tv_Artist.setText(playingSong.getArtist());
            Bitmap bitmap = picturePresenter.findBitmapByFilePath(playingSong.getUri());
            if (bitmap != null) {
                btn_Album.setImageBitmap(bitmap);
            }
//            updateButton();
        }
    }



//    public void updateButton() {
//        if (MusicService.isPlaying) {
//            btn_Start.setVisibility(View.GONE);
//            btn_Pause.setVisibility(View.VISIBLE);
//        } else {
//            btn_Start.setVisibility(View.VISIBLE);
//            btn_Pause.setVisibility(View.GONE);
//        }
//    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Song song) {
        Bitmap bitmap = picturePresenter.findBitmapByFilePath(song.getUri());
        if (bitmap != null){
            btn_Album.setImageBitmap(bitmap);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StringEvent stringEvent) {
       switch (stringEvent.getStr()){
           case "start":
               btn_Start.setVisibility(View.GONE);
               btn_Pause.setVisibility(View.VISIBLE);
               break;
           case "pause":
               btn_Start.setVisibility(View.VISIBLE);
               btn_Pause.setVisibility(View.GONE);
               break;
               default:break;
       }
    }

    //切换页面时更新播放状态
    @Override
    public void onResume() {
        super.onResume();
//        updateAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.main_bottom_btnAlbum, R.id.main_bottom_imgpause, R.id.main_bottom_imgstart, R.id.main_bottom_imgnext, R.id.main_bottom_imgmenu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_bottom_btnAlbum:
                Intent intent_playactivity = new Intent(getActivity(), PlayActivity.class);
                startActivity(intent_playactivity);
                musicService.postSongInfoByEventBus();
                break;
            case R.id.main_bottom_imgpause:
                musicService.pause();
//                updateButton();
                break;
            case R.id.main_bottom_imgstart:
                musicService.start();
//                updateButton();
                break;
            case R.id.main_bottom_imgnext:
                musicService.toNextSong(1);
                updateAll();
                break;
            case R.id.main_bottom_imgmenu:
                mainActivity.drawerLayout.openDrawer(GravityCompat.END);
                break;
        }
    }
}