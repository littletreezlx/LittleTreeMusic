package com.example.littletreemusic.activity.play;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.di.Component.play.DaggerSeekBarComponent;
import com.example.littletreemusic.di.Component.play.SeekBarComponent;
import com.example.littletreemusic.di.Component.play.SeekBarModule;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.presenter.play.SeekBarContract;
import com.example.littletreemusic.presenter.play.SeekBarPresenter;
import com.example.littletreemusic.service.MusicService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class SeekBarFragment extends Fragment implements SeekBarContract.ISeekBarView {

    RelativeLayout seekBarTemp;
    @BindView(R.id.current_time)
    TextView tv_CurrentTime;
    @BindView(R.id.total_time)
    TextView tv_TotalTime;
    @BindView(R.id.play_seekbar_seekbar)
    SeekBar seekBar;
    Unbinder unbinder;

    @Inject
    SeekBarPresenter seekBarPresenter;
    @Inject
    MusicService musicService;

    boolean isDestroy=false;
//    boolean isPaused=false;
    SeekBarHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        SeekBarComponent seekBarComponent = DaggerSeekBarComponent.builder()
                .playActivityComponent(((PlayActivity) getActivity()).getPlayActivityComponent())
                .seekBarModule(new SeekBarModule(this))
                .build();
        seekBarComponent.inject(this);
        seekBarComponent.inject(seekBarPresenter);
        View view = inflater.inflate(R.layout.play_seekbar, seekBarTemp, true);
        seekBarTemp = (RelativeLayout) view.findViewById(R.id.play_seekbar);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        setTotalTime();
        handler = new SeekBarHandler();
        SeekBarThread seekBarThread=new SeekBarThread();
        seekBarThread.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int manualProgress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                manualProgress = progress;
//                seekBar.setProgress(manualProgress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                musicService.pause();
//                拖动歌词，待添加
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int setProgressTime = manualProgress * 1024;
                musicService.setProgress(setProgressTime);
                musicService.start();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        isDestroy=true;
    }

    class SeekBarThread extends Thread {

        @Override
        public void run() {
            super.run();
            do {
                try {
                    handler.sendEmptyMessage(0x111);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (!isDestroy);
        }
    }

    class SeekBarHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x111:
                    int currentTimeInt = musicService.getCurrentTime() / 1024;
                    String m = String.valueOf(currentTimeInt / 60);
                    String s = String.valueOf(currentTimeInt % 60);
                    tv_CurrentTime.setText(m + ":" + s);
                    seekBar.setProgress(currentTimeInt);
                    break;
                default:
                    break;
            }
        }
    }

    @Subscribe
    public void onEvent(Song song){
        setTotalTime();
    }

    public void setTotalTime(){
        int totalTimeInt = musicService.getTotalTime() / 1024;
        seekBar.setProgress(0);
        seekBar.setMax(totalTimeInt);
        String m = String.valueOf(totalTimeInt / 60);
        String s = String.valueOf(totalTimeInt % 60);
        tv_TotalTime.setText(m + ":" + s);
    }



}
