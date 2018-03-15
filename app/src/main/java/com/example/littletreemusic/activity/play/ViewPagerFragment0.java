package com.example.littletreemusic.activity.play;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.view.BarChart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZLX Vincent on 2017/10/23.
 */

public class ViewPagerFragment0 extends Fragment {

    @BindView(R.id.viewpager_fragment0)
    RelativeLayout layout;
    Unbinder unbinder;

    @Inject
    MusicService musicService;

    Visualizer mVisualizer;
    BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.play_viewpager0, container, false);
        unbinder = ButterKnife.bind(this, view);
//        EventBus.getDefault().register(this);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            barChart=new BarChart(getActivity());
            layout.addView(barChart);
            setVisualizer();

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        return view;
    }

    public void setVisualizer() {
//        if (mVisualizer != null) {
//            mVisualizer = null;
//        }
        mVisualizer = new Visualizer(musicService.getMediaPlayer().getAudioSessionId());
        mVisualizer.setEnabled(false);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {

            //这个回调采集的是波形数据
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform,
                                              int samplingRate) {
                if (barChart != null){
                    barChart.updateVisualizer(waveform);
                }
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft,
                                         int samplingRate) {
//                            byte[] model = new byte[fft.length / 2 + 1];
//                            model[0] = (byte) Math.abs(fft[1]);
//                            int j = 1;
//
//                            for (int i = 2; i < 18; ) {
//                                model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
//                                i += 2;
//                                j++;
//                            }
//
//                            int i=1;
//                            barChart.updateVisualizer(model);
            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);
        mVisualizer.setEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setVisualizer();
                } else {
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        layout.removeView(barChart);
        if (mVisualizer != null) {
            mVisualizer.release();
            mVisualizer = null;
        }
        unbinder.unbind();
//        EventBus.getDefault().unregister(this);
    }

//    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
//    public void onEvent(Song song) {
//        setVisualizer();
//    }

}
