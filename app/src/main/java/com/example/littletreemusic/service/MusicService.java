package com.example.littletreemusic.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.example.littletreemusic.R;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.pojo.StringEvent;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;


public class MusicService extends Service implements MediaPlayer.OnPreparedListener,AudioManager.OnAudioFocusChangeListener{


    @Inject
    MusicServicePresenter musicServicePresenter;

    private MusicBinder musicBinder=new MusicBinder();
    private MediaPlayer mediaPlayer;
    public static String playingUriStr;
    public static boolean isPlaying;
    private String uriNextStr;
    private int playingPosition=0,currentTime,totalTime,lastSongList;
    boolean isPaused=false,isFirst=true;
    private Uri uri;
    String uriStr;
    Song playingSong;

    NotificationManager notifyManager;
    Notification notification;
    List<Song> songs,songs_all;
    Song nextSong;
    RemoteViews remoteViews;
    SharedPreferences sp;
    int playMode=0;
    final int PLAYMODE_SEQUENCE=0,PLAYMODE_SINGE=1,PLAYMODE_RANDOM=2;

    @Override
    public void onCreate(){
        super.onCreate();
        sp=getSharedPreferences("sp",MODE_PRIVATE);
//        musicServicePresenter=new MusicServicePresenter();
        isPlaying=false;
        isPaused=false;
        isFirst=true;
        mediaPlayer=new MediaPlayer();
//        唤醒锁
//        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
//        默认顺序
        playMode=PLAYMODE_SEQUENCE;
//        寻找上次的播放歌单和歌曲，并准备
        initLastSongListAndSong();
//        播放结束后调用toNext
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                switch (playMode){
                    case PLAYMODE_SEQUENCE:
                        toNextSong(1);
                        break;
                    case PLAYMODE_SINGE:
                        break;
                    case PLAYMODE_RANDOM:
                        break;
                    default:break;
                }
            }
        });

//        添加通知栏
        addNotification();
    }


    @Override
    public int onStartCommand(Intent intent,int flag,int startId){

        switch (intent.getStringExtra("mode")){
            case "app":
                break;
            case "list":
                int listPosition = intent.getIntExtra("position", -1);
                String uristr = intent.getStringExtra("uri");
                if (uristr !=null){
                    isFirst=false;
                    uri = Uri.parse(uristr);
                }
//        列表点击的歌曲不是当前播放的歌曲才会执行
                if (listPosition != playingPosition){
                    try {
                        mediaPlayer.stop();
                        mediaPlayer=new MediaPlayer();
                        Log.d("service",mediaPlayer.toString());
                        mediaPlayer.setDataSource(getApplicationContext(),uri);
                        mediaPlayer.setOnPreparedListener(this);
                        mediaPlayer.prepare();
                        playingUriStr=uristr;
                        playingPosition=listPosition;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "start":
                start();
                break;

            case "pause":
                pause();
                break;

            case "previous":
                toNextSong(0);
                break;

            case "next":
                toNextSong(1);
                break;

            default:
                break;

        }
        return  START_STICKY;
    }

    public void toNextSong(int toNext){
        switch (toNext){
            case 0:
                if (playingPosition >1){
                    playingPosition--;
                }
                break;
            case 1:
                if (songs != null){
                   if ( playingPosition < songs.size()-1){
                        playingPosition++;
                    }else{
                        playingPosition = 0;
                    }
                }
                break;

            default:
                break;
        }

        if (playingPosition<songs.size()){
            nextSong=songs.get(playingPosition);
            uriNextStr=nextSong.getUri();
            Uri urinext = Uri.parse(uriNextStr);
            mediaPlayer.stop();
            try {
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setDataSource(getApplicationContext(),urinext);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.prepare();
//                mediaPlayer.setOnPreparedListener(this);
//                start();
                playingUriStr=uriNextStr;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        isFirst=false;
        mediaPlayer.start();
        isPlaying=true;
        isPaused=false;
        updateRemoteViews();
        StringEvent stringEvent=new StringEvent("start");
        EventBus.getDefault().postSticky(stringEvent);
    }

    public void pause(){
        if (isPlaying){
            mediaPlayer.pause();
            isPlaying=false;
            isPaused=true;
            updateRemoteViews();
            StringEvent stringEvent=new StringEvent("pause");
            EventBus.getDefault().postSticky(stringEvent);
        }
    }

    /**
     * mediaPlayer准备完毕，相当于每次播放下一首歌曲时的状态。
     * 在这里通知BottomFragment,PlayActivity。
     * 用SharedPreferences记录待播放歌曲的playingUriStr和playingPosition。
     * 并且重置Notification中RemoteViews的状态。
     * @param mediaPlayer
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer){
        if (!isFirst){
            start();
        }
        isFirst=false;
        updateRemoteViews();
        EventBus.getDefault().postSticky(songs.get(playingPosition));
    }

    @Override
    public IBinder onBind(Intent intent){
        return musicBinder;
    }

    public class MusicBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public void onDestroy() {
        if (playingUriStr!=null) {
            SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
            editor.putString("playing_Uri", playingUriStr);
            editor.putString("playing_Title", songs.get(playingPosition).getTitle());
            editor.putString("playing_Artist", songs.get(playingPosition).getArtist());
            editor.putInt("playing_Position", playingPosition);
            editor.apply();
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        }
        stopForeground(true);
        super.onDestroy();
    }

    public interface OnSongChangedListener0{
        void OnSongChanged0();
    }

    public interface OnSongChangedListener1{
        void OnSongChanged1();
    }


    @Override
    public void onAudioFocusChange(int focusChange) {
        // 音频焦点变化 需要做的事
    }

//
//    懒汉式单例类.在第一次调用的时候实例化自己
//    public class MediaPlayer {
//
//        private MediaPlayer() {}
//        private MediaPlayer mediaPlayer=null;
//        //静态工厂方法
//        public MediaPlayer getMediaPlayer() {
//            if (mediaPlayer == null) {
//                mediaPlayer = new MediaPlayer();
//            }
//            return mediaPlayer;
//        }
//    }

    @TargetApi(Build.VERSION_CODES.N)
    public void addNotification(){
//        初始化RemoteViews。
        Intent intent = new Intent(this,MusicService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentPlay = new Intent(this,MusicService.class);
        intentPlay.putExtra("mode","start");
        PendingIntent pendingIntentPlay = PendingIntent.getService(this,
                1,intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentPause = new Intent(this,MusicService.class);
        intentPause.putExtra("mode","pause");
        PendingIntent pendingIntentPause = PendingIntent.getService(this,
                2,intentPause,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentPrevious = new Intent(this,MusicService.class);
        intentPrevious.putExtra("mode","previous");
        PendingIntent pendingIntentPrevious = PendingIntent.getService(this,
                3,intentPrevious,PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentNext = new Intent(this,MusicService.class);
        intentNext.putExtra("mode","next");
        PendingIntent pendingIntentNext = PendingIntent.getService(this,
                4,intentNext,PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews=new RemoteViews(getPackageName(),R.layout.notification);
//        if (songs.size() != 0){
//            remoteViews.setTextViewText(R.id.notification_tv_title,songs.get(playingPosition).getTitle());
//            remoteViews.setTextViewText(R.id.notification_tv_artist,songs.get(playingPosition).getArtist());
//        }
        remoteViews.setOnClickPendingIntent(R.id.notification_btn_play,pendingIntentPlay);
        remoteViews.setOnClickPendingIntent(R.id.notification_btn_pause,pendingIntentPause);
        remoteViews.setOnClickPendingIntent(R.id.notification_btn_previous,pendingIntentPrevious);
        remoteViews.setOnClickPendingIntent(R.id.notification_btn_next,pendingIntentNext);

        //新建Builer对象
        notification = new Notification.Builder(this)
                .setTicker("欢迎使用小树音乐")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .build();
        notification.contentView = remoteViews;
        notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        updateRemoteViews();
        startForeground(1, notification);

    }
    public void updateRemoteViews(){
        if (songs.size()!=0){
            remoteViews.setTextViewText(R.id.notification_tv_title,songs.get(playingPosition).getTitle());
            remoteViews.setTextViewText(R.id.notification_tv_artist,songs.get(playingPosition).getArtist());
        }
        if (isPlaying){
            remoteViews.setViewVisibility(R.id.notification_btn_pause, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.notification_btn_play, View.GONE);
        }else {
            remoteViews.setViewVisibility(R.id.notification_btn_play, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.notification_btn_pause, View.GONE);
        }
        if (notifyManager != null){
            notifyManager.notify(1,notification);
        }
    }

    private void initLastSongListAndSong(){
        songs = new ArrayList<>();
        int lastSongList=sp.getInt("LastSongList",-2);
        switch (lastSongList){
            case -2:
                songs = DataSupport.findAll(Song.class);
                break;
            case -1:
                songs=DataSupport.where("isFavourite = ?","1").find(Song.class);
                break;
            default:
                Set tagSet=sp.getStringSet("TagSet",null);
                if (tagSet != null){
                    List<String> tagList = new ArrayList<>();
                    tagList.addAll(tagSet);
                    String tagName = tagList.get(lastSongList);
                    List<Song> songs_all = DataSupport.findAll(Song.class);
                    songs=new ArrayList<>();
                    for (int i=0;i<songs_all.size();i++){
                        if (songs_all.get(i).getTagList().contains(tagName)) {
                            songs.add(songs_all.get(i));
                        }
                    }
                }
                break;
        }
        playingPosition=sp.getInt("playing_Position",0);
        if (playingPosition < songs.size()){
            playingSong=songs.get(playingPosition);
            uriStr=playingSong.getUri();
            uri=Uri.parse(uriStr);
            try {
                mediaPlayer.setDataSource(getApplicationContext(),uri);
                mediaPlayer.prepare();
                playingUriStr=uriStr;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
//            songs为null，不做准备。
        }

    }


    //    以下全部为public开放方法!!!

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public int getTotalTime(){
        if (mediaPlayer!=null){
            totalTime=mediaPlayer.getDuration();
        }
        return totalTime;
    }

    public int getCurrentTime(){
        if (mediaPlayer.isPlaying()){
            currentTime=mediaPlayer.getCurrentPosition();
        }
        return currentTime;
    }

    public void setProgress(int setProgressTime){
        if (mediaPlayer != null){
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    start();
                }
            });
            mediaPlayer.seekTo(setProgressTime);
        }
    }

    public void setSongList(List<Song> songList){
        songs=songList;
    }

    public void postSongInfoByEventBus(){
        EventBus.getDefault().postSticky(songs.get(playingPosition));
        if (isPlaying){
            StringEvent stringEvent=new StringEvent("start");
            EventBus.getDefault().postSticky(stringEvent);
        }else if (isPaused){
            StringEvent stringEvent=new StringEvent("pause");
            EventBus.getDefault().postSticky(stringEvent);
        }
    }







}

