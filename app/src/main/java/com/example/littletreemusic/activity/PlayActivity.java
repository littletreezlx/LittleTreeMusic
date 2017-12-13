package com.example.littletreemusic.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.ViewPagerAdapter0;
import com.example.littletreemusic.animate.DepthPageTransformer;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.view.ViewPager_view0;

import java.util.ArrayList;
import java.util.List;


public class PlayActivity extends AppCompatActivity {

    TextView text0,text1,text2,text3;
    ImageView imageView;
    Button backbtn,playbtn,pausebtn,nextbtn,previousbtn;
    SeekBar seekBar;
    private Drawable bp;
    MusicService musicService;
    int current_progress,currentTimeInt,totalTimeInt,cp;
    String playingTitle,playingArtist,currentTime,totalTime,a,b,c,d,aa,bb;
    ViewPager viewPager;
    private  Handler handler;
    Bundle bundle1,bundle2;
    ServiceConnection serviceConnection;
    Visualizer mVisualizer;
    ViewPager_view0 viewPager0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.acitivity_play);

        //设背景
        RelativeLayout playLayout = (RelativeLayout) findViewById(R.id.layout_play);
        playLayout.setBackgroundResource(R.drawable.bp_0);

//        imageView = (ImageView) findViewById(R.id.play);
//        bp = ContextCompat.getDrawable(this, R.drawable.bp_0);
//        imageView.setImageDrawable(bp);
        text0 = (TextView) findViewById(R.id.play_title);
        text1 = (TextView) findViewById(R.id.play_artist);
        text2 = (TextView) findViewById(R.id.current_time);
        text3 = (TextView) findViewById(R.id.final_time);
        seekBar = (SeekBar) findViewById(R.id.play_seekbar);
        backbtn = (Button) findViewById(R.id.play_back);
        playbtn = (Button) findViewById(R.id.play_play);
        pausebtn = (Button) findViewById(R.id.play_pause);
        nextbtn = (Button) findViewById(R.id.play_next);
        previousbtn = (Button) findViewById(R.id.play_previous);
        viewPager = (ViewPager) findViewById(R.id.play_viewpager);
//        final View viewPager0= (View) findViewById(R.id.view0);
        viewPager0=new ViewPager_view0(this);



        SharedPreferences sp = getSharedPreferences("sp0", MODE_PRIVATE);
        playingTitle = sp.getString("playing_title", "noTitle");
        if (!playingTitle.equals("noTitle")) {
            text0.setText(playingTitle);
        }
        playingArtist = sp.getString("playing_artist", "noArtist");
        if (!playingArtist.equals("noArtist")) {
            text1.setText(playingArtist);
        }


//       初始化ViewPager

        final View view0 = View.inflate(this,R.layout.fragment_play_viewpager0, null);
        View view1 = View.inflate(this,R.layout.fragment_play_viewpager1, null);
        final List<View> viewList = new ArrayList<View>();
        viewList.add(view0);
        viewList.add(view1);
        viewPager.setAdapter(new ViewPagerAdapter0(viewList));
        viewPager.setPageTransformer(true, new DepthPageTransformer());

//        获取音频频谱
        mVisualizer = new Visualizer(musicService.getMediaPlayer().getAudioSessionId());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {

            //这个回调采集的是波形数据
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform,
                                              int samplingRate) {


            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft,
                                         int samplingRate) {
                byte[] model = new byte[fft.length / 2 + 1];
                model[0] = (byte) Math.abs(fft[1]);
                int j = 1;

                for (int i = 2; i < 18;) {
                    model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
                    i += 2;
                    j++;
                }
                viewPager0.updateVisualizer(model);

            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);

        mVisualizer.setEnabled(true);


//        按钮监听
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                bundle1=msg.getData();
                cp=bundle1.getInt("current_progress",0);
                aa=bundle1.getString("a","0");
                bb=bundle1.getString("a","0");

                seekBar.setProgress(current_progress);
                text2.setText(a + ":" + b);
            }
        };

        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService=((MusicService.MusicBinder)service).getService();
                musicService.setOnSongChangedListener(new MusicService.OnSongChangedListener() {
                    @Override
                    public void OnSongChangedListener1(String title, String artist) {
                        SharedPreferences.Editor editor=getSharedPreferences("sp0",MODE_PRIVATE).edit();
                        editor.remove("playing_title");
                        editor.remove("playing_artist");
                        editor.putString("playing_title",title);
                        editor.putString("playing_artist",artist);
                        editor.apply();

                        text2.setText("00:00");
                        musicService.getTotalTime();
                        totalTimeInt=musicService.getTotalTime()/1024;
                        c=String.valueOf(totalTimeInt/60);
                        d=String.valueOf(totalTimeInt%60);
                        text2.setText(c+":"+d);
                        text3.setText(currentTime);
                        text0.setText(title);
                        text1.setText(artist);
                    }
                });
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent serviceIntent = new Intent(this, MusicService.class);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    do{
                        currentTimeInt = musicService.getCurrentTime() / 1024;
                        totalTimeInt = musicService.getTotalTime() / 1024;
                        current_progress = currentTimeInt / totalTimeInt * 100;
                        a = String.valueOf(currentTimeInt / 60);
                        b = String.valueOf(currentTimeInt % 60);

                        Message message = handler.obtainMessage();
                        Bundle bundle0 = new Bundle();
                        bundle0.putInt("current_progress",current_progress);
                        bundle0.putString("a", a);
                        bundle0.putString("b", b);
                        message.setData(bundle0);
                        handler.sendMessage(message);
                        Thread.sleep(1000);

                    }while (true);
                }
                     catch (Exception e) {
                        e.printStackTrace();
                    }


            }
        }).start();


        //




    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unbindService(serviceConnection);
    }

}

