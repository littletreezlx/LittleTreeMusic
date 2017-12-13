package com.example.littletreemusic.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.MainActivity;
import com.example.littletreemusic.table.Song;

import org.litepal.crud.DataSupport;

import java.util.List;


public class MusicService extends Service implements MediaPlayer.OnPreparedListener{


    private MusicBinder musicBinder=new MusicBinder();
    private MediaPlayer mediaPlayer;
    private String playinguristr,urinextstr,uristr,mode,playmode,title,artist;
    private int playingId=-1,currentTime,totalTime;
    boolean isPlaying;
    boolean isPaused;
    private Uri uri,urinext;
    private int position=-1;
    public OnSongChangedListener mListener,onSongChangedListener;


    public void setOnSongChangedListener(OnSongChangedListener onSongChangedListener){
        this.onSongChangedListener=onSongChangedListener;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        isPlaying=false;
        isPaused=false;
        mediaPlayer=new MediaPlayer();
        playmode="bySequence";

        SharedPreferences sp=getSharedPreferences("sp0",MODE_PRIVATE);
        playinguristr=sp.getString("playing_title","noUri");
        try {
            uri=Uri.parse(playinguristr);
//            playingId=position;
            mediaPlayer.setDataSource(getApplicationContext(),uri);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //新建RemoteView对象
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_button);
//        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_button);
//        mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.sing_icon);
//        //API3.0 以上的时候显示按钮，否则消失
//        mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "周杰伦");
//        mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "七里香");
//        //如果版本号低于（3。0），那么不显示按钮
//        if(BaseTools.getSystemVersion() <= 9){
//            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
//        }else{
//            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
//        }
//        //
//        if(isPlay){
//            mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_pause);
//        }else{
//            mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_play);
//        }
//        //点击的事件处理
//        Intent buttonIntent = new Intent(ACTION_BUTTON);
//        /* 上一首按钮 */
//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
//        //这里加了广播，所及INTENT的必须用getBroadcast方法
//        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);
//        /* 播放/暂停  按钮 */
//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
//        PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
//        /* 下一首 按钮  */
//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
//        PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);

        //新建Builer对象
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(artist)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();
        startForeground(1, notification);

    }


    @Override
    public int onStartCommand(Intent intent,int flag,int startId){

        this.mListener=onSongChangedListener;

        mode=intent.getStringExtra("mode");
        this.position = intent.getIntExtra("position", -1);
        uristr = intent.getStringExtra("uri");

        if (intent.getStringExtra("playmode") != null){
            playmode=intent.getStringExtra("playmode");
        }
//        String mode=intent.getStringExtra("mode");
        if (uristr !=null){
            uri = Uri.parse(uristr);
        }

        if (mode.equals("list_op")) {
            if (!isPlaying && !isPaused) {
                try {
                    playinguristr = uristr;
                    playingId=position;
                    mediaPlayer.setDataSource(getApplicationContext(),uri);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(this);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                isPlaying = true;
                isPaused=false;
                Intent Intent=new Intent("startTOpause");
                sendBroadcast(Intent);

            }

            //如果音乐已播放，且选择的歌曲是正在播放的歌曲且未暂停
            else if (isPlaying && playinguristr.equals(uristr) && !isPaused) {

                mediaPlayer.pause();
                isPlaying =false;
                isPaused=true;
                Intent Intent=new Intent("pauseTOstart");
                sendBroadcast(Intent);
            }

            //如果音乐已播放，且选择的歌曲是被暂停的歌曲
            else if (!isPlaying && playinguristr.equals(uristr) && isPaused) {

                mediaPlayer.start();
                isPlaying =true;
                isPaused=false;
                Intent Intent=new Intent("startTOpause");
                sendBroadcast(Intent);
            }

            //如果选择的歌曲！不！是正在播放的歌曲
            else if (!playinguristr.equals(uristr)) {
                mediaPlayer.stop();
                try {
                    mediaPlayer=new MediaPlayer();
                    playinguristr = uristr;
                    playingId=position;
                    mediaPlayer.setDataSource(getApplicationContext(), uri);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(this);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                isPlaying = true;
                isPaused=false;
                Intent Intent=new Intent("startTOpause");
                sendBroadcast(Intent);
            }
        }
        else if (mode.equals("bottom0_op")){

            String op=intent.getStringExtra("op");
            if (op.equals("bottom_start")){
                start();
//                if (isPaused){
//                    mediaPlayer.start();
//                    isPlaying=true;
//                    isPaused=false;
//                    Intent Intent=new Intent("startTOpause");
//                    sendBroadcast(Intent);
//                }
            }
            else if (op.equals("bottom_pause")){
                pause();
//                if (isPlaying){
//                    mediaPlayer.pause();
//                    isPlaying=false;
//                    isPaused=true;
//                    Intent Intent=new Intent("pauseTOstart");
//                    sendBroadcast(Intent);
//                }
            }
            else if (op.equals("bottom_next")){
                toNext();
//                if (playingId != -1){
//                    playingId++;
//                    List<Song> nextSong=DataSupport.limit(1).offset(playingId).find(Song.class);
//
//                    if (nextSong.size() !=0){
//                        urinextstr=nextSong.get(0).getUri();
//                    }
//
//                    if (urinextstr !=null){
//                        urinext = Uri.parse(urinextstr);
//                        mediaPlayer.stop();
//                        try {
//                            mediaPlayer=new MediaPlayer();
//                            mediaPlayer.setDataSource(getApplicationContext(),urinext);
//                            mediaPlayer.prepare();
//                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                @Override
//                                public void onPrepared(MediaPlayer mp) {
//                                    mediaPlayer.start();
//                                }
//                            });
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//                        isPlaying=true;
//                        isPaused=false;
//                        Intent Intent=new Intent("startTOpause");
//                        sendBroadcast(Intent);
//                    }
//                }
            }
        }

        //监听音乐播放完成
        if (mediaPlayer != null){
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if(playmode.equals("bySequence")){
                        toNext();
                    }
                }
            });
        }

//        if (playingId != -1){
//            List<Song> thisSong=DataSupport.limit(1).offset(playingId).find(Song.class);
//            title=thisSong.get(0).getTitle();
//            artist=thisSong.get(0).getArtist();
//            Intent taIntent=new Intent("titleANDartist");
//            taIntent.putExtra("title",title);
//            taIntent.putExtra("artist",artist);
//            sendBroadcast(taIntent);
//        }

        return  START_STICKY;
    }

    public void toNext(){
        if (playingId != -1){
            playingId++;
            List<Song> nextSong=DataSupport.limit(1).offset(playingId).find(Song.class);

            if (nextSong.size() !=0){
                urinextstr=nextSong.get(0).getUri();
            }

            if (urinextstr !=null){
                urinext = Uri.parse(urinextstr);
                mediaPlayer.stop();
                try {
                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setDataSource(getApplicationContext(),urinext);
                    mediaPlayer.prepare();
                    mediaPlayer.setOnPreparedListener(this);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                playinguristr=urinextstr;
                isPlaying=true;
                isPaused=false;
                Intent Intent=new Intent("startTOpause");
                sendBroadcast(Intent);
            }
        }
    }

    public void start(){

            mediaPlayer.start();
            isPlaying=true;
            isPaused=false;
            Intent Intent=new Intent("startTOpause");
            sendBroadcast(Intent);

    }

    public void pause(){
        if (isPlaying){
            mediaPlayer.pause();
            isPlaying=false;
            isPaused=true;
            Intent Intent=new Intent("pauseTOstart");
            sendBroadcast(Intent);
        }
    }

//    public String getTitle(){
//        return title;
//    }
//    public String getArtist(){
//        return artist;
//    }



    @Override
    public void onPrepared(MediaPlayer mediaPlayer){
        this.mListener=onSongChangedListener;
        mediaPlayer.start();
        List<Song> titleANDartist=DataSupport.where("uri=?",playinguristr).find(Song.class);
        title=titleANDartist.get(0).getTitle();
        artist=titleANDartist.get(0).getArtist();
//        playingId=titleANDartist.get(0).getArtist();
        mListener.OnSongChangedListener1(title,artist);
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

        if (playinguristr!=null) {
            SharedPreferences.Editor editor = getSharedPreferences("sp0", MODE_PRIVATE).edit();
            editor.remove("playing_uri");
            editor.putString("playing_uri", playinguristr);
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



    public interface OnSongChangedListener{
        void OnSongChangedListener1(String title,String artist);
    }

//    以下全部为public开放方法
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

}
