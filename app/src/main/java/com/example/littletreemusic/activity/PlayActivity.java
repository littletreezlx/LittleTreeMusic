package com.example.littletreemusic.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.adapter.ListViewAdapter0;
import com.example.littletreemusic.adapter.ViewPagerAdapter0;
import com.example.littletreemusic.animate.DepthPageTransformer;
import com.example.littletreemusic.model.Song;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.view.ViewPager_view0;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    TextView text0,text1,text2,text3,text4;
    ListView listView;
    Button backbtn,playbtn,pausebtn,nextbtn,previousbtn,tagbtn,modebtn;
    SeekBar seekBar;
    MusicService musicService;
    int currentTimeInt,totalTimeInt,currentProgress;
    String playingTitle,playingArtist,a,b,c,d,aa,bb,bpIdstr,bpPathstr;;
    Handler handler;
    Bundle bundle0,bundle1;
    ServiceConnection serviceConnection;
    Visualizer mVisualizer;
    Set<String> tagSet;
    ListViewAdapter0 adapter;
    List<String> playingTags,tagList,checkedTags;
    LayoutInflater inflater;
    View view0,view1;
    ViewPager_view0 viewPager_view0;
    ViewPager viewPager;
    ImageView imageView;
    Drawable bp;
    byte[] mBytes;

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
//        initBroadCast();
        updateTATB();

        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService=((MusicService.MusicBinder)service).getService();

                musicService.getTotalTime();
                totalTimeInt=musicService.getTotalTime()/1024;
                seekBar.setMax(totalTimeInt);
                c=String.valueOf(totalTimeInt/60);
                d=String.valueOf(totalTimeInt%60);
                text3.setText(c+":"+d);

                musicService.setOnSongChangedListener(new MusicService.OnSongChangedListener() {
                    @Override
                    public void OnSongChanged() {
                        updateTATB();
                        musicService.getTotalTime();
                        totalTimeInt=musicService.getTotalTime()/1024;
                        seekBar.setMax(totalTimeInt);
                        c=String.valueOf(totalTimeInt/60);
                        d=String.valueOf(totalTimeInt%60);
                        text3.setText(c+":"+d);
                        seekBar.setProgress(0);

                    }
                });

//                mVisualizer = new Visualizer(musicService.getMediaPlayer().getAudioSessionId());
//                mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
//                mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
//
//                    //这个回调采集的是波形数据
//                    @Override
//                    public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform,
//                                                      int samplingRate) {
////                        mBytes=waveform;
//                        viewPager_view0.updateVisualizer(waveform);
//                    }
//
//                    @Override
//                    public void onFftDataCapture(Visualizer visualizer, byte[] fft,
//                                                 int samplingRate) {
////                        Toast.makeText(PlayActivity.this,"2",Toast.LENGTH_SHORT).show();
////                        byte[] model = new byte[fft.length / 2 + 1];
////                        model[0] = (byte) Math.abs(fft[1]);
////                        int j = 1;
////
////                        for (int i = 2; i < 18;) {
////                            model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
////                            i += 2;
////                            j++;
////                        }
////
////                        viewPager_view0.updateVisualizer(model);
//                    }
//                }, Visualizer.getMaxCaptureRate() / 2, true, false);
//
//                mVisualizer.setEnabled(true);
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
                        a = String.valueOf(currentTimeInt / 60);
                        b = String.valueOf(currentTimeInt % 60);
                        Message message = handler.obtainMessage();
                        bundle0 = new Bundle();
                        bundle0.putString("a", a);
                        bundle0.putString("b", b);
                        bundle0.putInt("currentInt",currentTimeInt);
                        message.what=0x111;
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


//
//        inflater=getLayoutInflater();
//        view0 = inflater.inflate(R.layout.fragment_play_viewpager0,viewPager,true);
//        view1 = inflater.inflate(R.layout.fragment_play_viewpager1,viewPager,true);

        view0 = View.inflate(this,R.layout.fragment_play_viewpager0, null);
        view1 = View.inflate(this,R.layout.fragment_play_viewpager1, null);
        List<View> viewList = new ArrayList<>();
        viewList.add(view0);
        viewList.add(view1);
        viewPager.setAdapter(new ViewPagerAdapter0(viewList));
        viewPager.setPageTransformer(true, new DepthPageTransformer());
//        viewPager_view0=(ViewPager_view0) view0.findViewById(R.id.view0);
//        viewPager_view0.setWillNotDraw(false);

//        seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int manualProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                seekBar.setProgress(progress);
                manualProgress=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                musicService.pause();
//                拖动歌词，待添加
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int setProgressTime=manualProgress*1024;
                musicService.setProgress(setProgressTime);
                musicService.start();
            }
        });

          handler = new PlayHandler();

//          new Thread(new R1()).start();

    }

//    class R1 implements Runnable {
//
//        @Override
//        public void run() {
//            do{
//                try{
//                    handler.sendEmptyMessage(0x222);
//                    Thread.sleep(1000);
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }while(true);
//        }
//    }





    class PlayHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x111:
                    bundle1=msg.getData();
                    aa=bundle1.getString("a","0");
                    bb=bundle1.getString("b","0");
                    currentProgress=bundle1.getInt("currentInt",0);
                    seekBar.setProgress(currentProgress);
                    text2.setText(aa + ":" + bb);
                    break;

//                case 0x222:
//                    viewPager_view0.updateVisualizer(mBytes);
//                    break;

                    default:break;

            }
        }
    }

//    class Receiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent){
//
//            if (intent.getAction().equals("startTOpause")){
//                playbtn.setVisibility(View.GONE);
//                pausebtn.setVisibility(View.VISIBLE);}
//            else if (intent.getAction().equals("pauseTOstart")){
//                playbtn.setVisibility(View.VISIBLE);
//                pausebtn.setVisibility(View.GONE);}
//        }
//    }

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
                    musicService.toNextSong(0);
                }
                break;

            case R.id.play_next:
                if (musicService != null){
                musicService.toNextSong(1);
            }
                break;

            case R.id.play_play:
                if (musicService != null){
                    musicService.start();
                }
                playbtn.setVisibility(View.GONE);
                pausebtn.setVisibility(View.VISIBLE);
                break;

            case R.id.play_pause:
                if (musicService != null){
                    musicService.pause();
                }
                playbtn.setVisibility(View.VISIBLE);
                pausebtn.setVisibility(View.GONE);
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
        text4 = (TextView) findViewById(R.id.play_tags);
        seekBar = (SeekBar) findViewById(R.id.play_seekbar);
        backbtn = (Button) findViewById(R.id.play_back);
        playbtn = (Button) findViewById(R.id.play_play);
        pausebtn = (Button) findViewById(R.id.play_pause);
        nextbtn = (Button) findViewById(R.id.play_next);
        previousbtn = (Button) findViewById(R.id.play_previous);
        tagbtn = (Button) findViewById(R.id.play_tag);
        modebtn = (Button) findViewById(R.id.play_mode);
        viewPager = (ViewPager) findViewById(R.id.play_viewpager);
        imageView = (ImageView) findViewById(R.id.background_picture_play);

        backbtn.setOnClickListener(this);
        playbtn.setOnClickListener(this);
        pausebtn.setOnClickListener(this);
        nextbtn.setOnClickListener(this);
        previousbtn.setOnClickListener(this);
        tagbtn.setOnClickListener(this);
        modebtn.setOnClickListener(this);

        SharedPreferences sp=getSharedPreferences("sp0",MODE_PRIVATE);
        bpIdstr=sp.getString("bp_default","no_bpId");
        if (! bpIdstr.equals("no_bpId")){
            int bpId = getResources().getIdentifier(bpIdstr, "drawable", "com.example.littletreemusic");
            bp = ContextCompat.getDrawable(this,bpId);
            imageView.setImageDrawable(bp);
        }else if (bpIdstr.equals("no_bpId")){
            bpPathstr=sp.getString("bp_album","no_bpal");
            if (bpPathstr !="no_bpal"){
                Bitmap bitmap = BitmapFactory.decodeFile(bpPathstr);
                if (bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
        if (bpIdstr.equals("no_bpId") && bpPathstr.equals("no_bpal")){
            bp = ContextCompat.getDrawable(this, R.drawable.bp_0);
            imageView.setImageDrawable(bp);
        }

//        inflater = getLayoutInflater();
//        View page0 = inflater.inflate(R.layout.fragment_play_viewpager1, null);
//        view0=(ViewPager_view0) page0.findViewById(R.id.view0);
    }

//    private void initBroadCast(){
//        intentFilter=new IntentFilter();
//        intentFilter.addAction("startTOpause");
//        intentFilter.addAction("pauseTOstart");
//        intentFilter.addAction("titleANDartist");
//        receiver=new Receiver();
//        registerReceiver(receiver,intentFilter);
//    }

//    更新歌名，标题和标签
    private void updateTATB(){

        String uristr=MusicService.playinguristr;
        if (uristr == null){
            SharedPreferences sp0 = getSharedPreferences("sp0", Context.MODE_PRIVATE);
            uristr=sp0.getString("playing_uri","no_uri");
        }
        if (!uristr.equals("no_uri")){
            List<Song> list0=DataSupport.where("uri=?",uristr).find(Song.class);
            if (list0 != null && list0.size() != 0){
                playingTitle=list0.get(0).getTitle();
                playingArtist=list0.get(0).getArtist();
                playingTags=list0.get(0).getTagList();
                text0.setText(playingTitle);
                text1.setText(playingArtist);
                text4.setText("");
                for (String t :playingTags){
                    text4.append("  "+t);
                }
                if (MusicService.isPlaying){
                    playbtn.setVisibility(View.GONE);
                    pausebtn.setVisibility(View.VISIBLE);
                }else {
                    playbtn.setVisibility(View.VISIBLE);
                    pausebtn.setVisibility(View.GONE);
                }
            }

        }

    }

    public void updataTagText() {
        String uristr = MusicService.playinguristr;
        if (uristr == null) {
            SharedPreferences sp0 = getSharedPreferences("sp0", Context.MODE_PRIVATE);
            uristr = sp0.getString("playing_uri", "no_uri");
        }
        if (!uristr.equals("no_uri")) {
            List<Song> list0 = DataSupport.where("uri=?", uristr).find(Song.class);
            if (list0 != null && list0.size() != 0) {
                playingTags = list0.get(0).getTagList();
                text4.setText("");
                for (String t : playingTags) {
                    text4.append("  " + t);
                }
            }
        }
    }




//    显示添加、应用标签项和标签栏
    private void actionAlertDialog(){

        ListViewAdapter0.adcheckedTags.clear();

        AlertDialog.Builder builder0;
        AlertDialog alertDialog;
        SharedPreferences sp_tag=getSharedPreferences("sp_tag",MODE_PRIVATE);
        tagSet=sp_tag.getStringSet("TagSet",null);
        tagList=new ArrayList<> ();
        if (tagSet != null){
            tagList.addAll(tagSet);
        }
        LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout=inflater.inflate(R.layout.listview_tag,(ViewGroup)findViewById(R.id.listview_tag));
        Button addTagBtn=(Button)layout.findViewById(R.id.add_tag) ;
        Button applyTagBtn=(Button)layout.findViewById(R.id.apply_tag) ;
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


        listView=(ListView)layout.findViewById(R.id.mylistview);
        adapter=new ListViewAdapter0(this,tagList);
        listView.setAdapter(adapter);

        builder0=new AlertDialog.Builder(this);
        builder0.setView(layout);
        alertDialog=builder0.create();
        alertDialog.show();

    }


//    显示添加新标签的对话框
    private void showInputTagDialog(){
        AlertDialog.Builder builder1=new AlertDialog.Builder(this);
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
                    Set<String> tagSet2=new HashSet<>();
                    if (tagSet != null){
                        tagSet2.addAll(tagSet);
                    }
                    tagSet2.add(name);
                    SharedPreferences.Editor editor=getSharedPreferences("sp_tag",MODE_PRIVATE).edit();
                    editor.remove("TagSet");
                    editor.putStringSet("TagSet",tagSet2);
                    editor.apply();
                        tagList.clear();
                        tagList.addAll(tagSet2);
                    adapter.notifyDataSetChanged();



//                    updateTagList();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder1.create().show();
    }

    //    显示确定应用新标签的对话框
    private void  showConfirmApplyDialog(){
        AlertDialog.Builder builder3=new AlertDialog.Builder(this);
        builder3.setTitle("标签");
        builder3.setMessage("确认添加选中标签吗？");
        builder3.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Song playingSong= DataSupport.where("uri=?", MusicService.playinguristr).find(Song.class).get(0);
//                tagList=playingSong.getTagList();
                int updateId=playingSong.getId();
                if (tagList == null){
                    tagList=new ArrayList<>();
                }
//                adapter.notifyDataSetChanged();
                Song updateSong = new Song();
                updateSong.setTagList(ListViewAdapter0.adcheckedTags);
                updateSong.update(updateId);

                updataTagText();

            }
        });
        builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder3.create().show();
    }

//
//    private void panduan(){
//        int itemsNumber=listView.getCount();
//        for (int i=1;i<itemsNumber;i++){
//            listView.getCheckedItemPosition()
//        }
//    }


    //切换页面时更新播放状态
    @Override
    public void onResume(){
        super.onResume();
        updateTATB();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
//        unregisterReceiver(receiver);
        unbindService(serviceConnection);
    }
}

