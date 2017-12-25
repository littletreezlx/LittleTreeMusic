package com.example.littletreemusic.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.ListViewAdapter0;
import com.example.littletreemusic.adapter.ViewPagerAdapter0;
import com.example.littletreemusic.animate.DepthPageTransformer;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.table.Song;
import com.example.littletreemusic.view.ViewPager_view0;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    TextView text0,text1,text2,text3;
    Button backbtn,playbtn,pausebtn,nextbtn,previousbtn,tagbtn,modebtn;
    SeekBar seekBar;
    MusicService musicService;
    int current_progress,currentTimeInt,totalTimeInt,cp;
    String playingTitle,playingArtist,currentTime,totalTime,a,b,c,d,aa,bb;
    ViewPager viewPager;
    Handler handler;
    Bundle bundle0,bundle1;
    ServiceConnection serviceConnection;
    Visualizer mVisualizer;
    IntentFilter intentFilter;
    BroadcastReceiver receiver;
    Set<String> tagSet;
    ListViewAdapter0 adapter;

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

        initView();
        initBroadCast();
        updateTAA();

        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService=((MusicService.MusicBinder)service).getService();
                musicService.setOnSongChangedListener(new MusicService.OnSongChangedListener() {
                    @Override
                    public void OnSongChangedListener1(String title, String artist) {
                        updateTAA();
                        text2.setText("00:00");
                        musicService.getTotalTime();
                        totalTimeInt=musicService.getTotalTime()/1024;
                        c=String.valueOf(totalTimeInt/60);
                        d=String.valueOf(totalTimeInt%60);
                        text3.setText(c+":"+d);

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
                    do{
                        try{
                        currentTimeInt = musicService.getCurrentTime() / 1024;
                        totalTimeInt = musicService.getTotalTime() / 1024;
                        current_progress =currentTimeInt*100/totalTimeInt;
                        a = String.valueOf(currentTimeInt / 60);
                        b = String.valueOf(currentTimeInt % 60);
                        Message message = handler.obtainMessage();
                        bundle0 = new Bundle();
                        bundle0.putInt("current_progress",current_progress);
                        bundle0.putString("a", a);
                        bundle0.putString("b", b);
                        message.setData(bundle0);
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }while(true);
                }
        }).start();

//       初始化ViewPager
        View view0 = View.inflate(this,R.layout.fragment_play_viewpager0, null);
        View view1 = View.inflate(this,R.layout.fragment_play_viewpager1, null);
        List<View> viewList = new ArrayList<View>();
        viewList.add(view0);
        viewList.add(view1);
        viewPager.setAdapter(new ViewPagerAdapter0(viewList));
        viewPager.setPageTransformer(true, new DepthPageTransformer());

//        获取音频频谱
        if (musicService != null){
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
                    ViewPager_view0 view0 =(ViewPager_view0) View.inflate(PlayActivity.this,R.layout.fragment_play_viewpager1, null);
                    view0.updateVisualizer(model);
                }
            }, Visualizer.getMaxCaptureRate() / 2, true, false);

            mVisualizer.setEnabled(true);
        }


//        seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

//                拖动歌词，待添加
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int setProgressTime=seekBar.getProgress()*1024;
                musicService.setProgress(setProgressTime);
            }
        });

          handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                bundle1=msg.getData();
                cp=bundle1.getInt("current_progress",0);
                aa=bundle1.getString("a","0");
                bb=bundle1.getString("b","0");
                seekBar.setProgress(cp);
                text2.setText(aa + ":" + bb);
            }
        };
    }

    class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){

            if (intent.getAction().equals("startTOpause")){
                playbtn.setVisibility(View.GONE);
                pausebtn.setVisibility(View.VISIBLE);}
            else if (intent.getAction().equals("pauseTOstart")){
                playbtn.setVisibility(View.VISIBLE);
                pausebtn.setVisibility(View.GONE);}
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.play_back:
                finish();
                break;

            case R.id.play_tag:
                actionAlertDialog();
                break;

            case R.id.play_previous:
                if (musicService != null){
                    musicService.toPrevious();
                }
                break;

            case R.id.play_next:
                if (musicService != null){
                musicService.toNext();
            }
                break;

            case R.id.play_play:
                if (musicService != null){
                    musicService.start();
                }
                break;

            case R.id.play_pause:
                if (musicService != null){
                    musicService.pause();
                }
                break;

            case R.id.play_mode:
                break;

        }

    }



    private void initView(){
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
        tagbtn = (Button) findViewById(R.id.play_tag);
        modebtn = (Button) findViewById(R.id.play_mode);
        viewPager = (ViewPager) findViewById(R.id.play_viewpager);

        backbtn.setOnClickListener(this);
        playbtn.setOnClickListener(this);
        pausebtn.setOnClickListener(this);
        nextbtn.setOnClickListener(this);
        previousbtn.setOnClickListener(this);
        tagbtn.setOnClickListener(this);
        modebtn.setOnClickListener(this);
    }

    private void initBroadCast(){
        intentFilter=new IntentFilter();
        intentFilter.addAction("startTOpause");
        intentFilter.addAction("pauseTOstart");
        intentFilter.addAction("titleANDartist");
        receiver=new Receiver();
        registerReceiver(receiver,intentFilter);
    }

    private void updateTAA(){
        SharedPreferences sp0=getSharedPreferences("sp0",MODE_PRIVATE);
        playingTitle=sp0.getString("playing_title","noTitle");
        if (!playingTitle.equals("noTitle")){
            text0.setText(playingTitle);
        }
        playingArtist=sp0.getString("playing_artist","noArtist");
        if (!playingArtist.equals("noArtist")){
            text1.setText(playingArtist);
        }
    }

//    显示添加、应用、删除标签项和标签栏
    private void actionAlertDialog(){
        AlertDialog.Builder builder0;
        AlertDialog alertDialog;
        SharedPreferences sp_tag=getSharedPreferences("sp_tag",MODE_PRIVATE);
        tagSet=sp_tag.getStringSet("TagSet",null);
        ArrayList<String> list=new ArrayList<> ();
        if (tagSet != null){
            list.addAll(tagSet);
        }
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.listview_tag,(ViewGroup)findViewById(R.id.listview_tag));
        Button addTagBtn=(Button)layout.findViewById(R.id.add_tag) ;
        Button applyTagBtn=(Button)layout.findViewById(R.id.apply_tag) ;
        Button deleteTagBtn=(Button)layout.findViewById(R.id.delete_tag) ;
        Button removeTagBtn=(Button)layout.findViewById(R.id.remove_tag) ;
        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputTagDialog();
            }
        });
        applyTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmApplyDialog();
            }
        });
        deleteTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDeleteDialog();
            }
        });
        removeTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmRemoveDialog();
            }
        });


        ListView listView=(ListView)layout.findViewById(R.id.mylistview);
        adapter=new ListViewAdapter0(this,list);
        listView.setAdapter(adapter);
        builder0=new AlertDialog.Builder(this);
        builder0.setView(layout);
        alertDialog=builder0.create();
        alertDialog.show();
    }



//    显示添加新标签的对话框
    private void showInputTagDialog(){
        AlertDialog.Builder builder1;
        builder1=new AlertDialog.Builder(this);
        builder1.setTitle("添加新标签");
        builder1.setMessage("请添加新标签");
        final EditText et=new EditText(this);
        et.setSingleLine();
        et.setHint("标签");
        builder1.setView(et);
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name=et.getText().toString().trim();
                if ("".equals(name)) {
                    Toast.makeText(getApplicationContext(), "不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences sp_tag=getSharedPreferences("sp_tag",MODE_PRIVATE);
                    tagSet=sp_tag.getStringSet("TagSet",null);
                    if (tagSet == null){
                        tagSet=new HashSet<String>();
                    }
                    tagSet.add(name);
                    SharedPreferences.Editor editor=getSharedPreferences("sp0",MODE_PRIVATE).edit();
                    editor.putStringSet("TagSet",tagSet);
                    editor.apply();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder1.create().show();
    }

    //    显示确定删除新标签的对话框
    private void  showConfirmDeleteDialog(){
        android.app.AlertDialog.Builder dialog=new android.app.AlertDialog.Builder(this);
        dialog.setTitle("标签");
        dialog.setMessage("确认删除选中标签吗？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp_tag=getSharedPreferences("sp_tag",MODE_PRIVATE);
                tagSet=sp_tag.getStringSet("TagSet",null);
                tagSet.removeAll(adapter.getCheckedTags());
                SharedPreferences.Editor editor=getSharedPreferences("sp0",MODE_PRIVATE).edit();
                editor.putStringSet("TagSet",tagSet);
                editor.apply();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    //    显示确定应用新标签的对话框
    private void  showConfirmApplyDialog(){
        android.app.AlertDialog.Builder dialog=new android.app.AlertDialog.Builder(this);
        dialog.setTitle("标签");
        dialog.setMessage("确认添加选中标签吗？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Song playingSong= DataSupport.where("uri=?", MusicService.playinguristr).find(Song.class).get(0);
                List<String> pstList=playingSong.getTagList();
                int updateId=playingSong.getId();
                if (pstList == null){
                    pstList=new ArrayList<>();
                }
                ArrayList checkedTags = adapter.getCheckedTags();
                pstList.addAll(checkedTags);
                Song updateSong = new Song();
                updateSong.setTagList(pstList);
                updateSong.update(updateId);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }
    //    显示确定移去新标签的对话框
    private void  showConfirmRemoveDialog(){
        android.app.AlertDialog.Builder dialog=new android.app.AlertDialog.Builder(this);
        dialog.setTitle("标签");
        dialog.setMessage("确认移去选中标签吗？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Song playingSong= DataSupport.where("uri=?", MusicService.playinguristr).find(Song.class).get(0);
                List<String> pstList=playingSong.getTagList();
                int updateId=playingSong.getId();
                if (pstList == null){
                    pstList=new ArrayList<>();
                }
                ArrayList checkedTags = adapter.getCheckedTags();
                pstList.addAll(checkedTags);
                Song updateSong = new Song();
                updateSong.setTagList(pstList);
                updateSong.update(updateId);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
        unbindService(serviceConnection);
    }
}

