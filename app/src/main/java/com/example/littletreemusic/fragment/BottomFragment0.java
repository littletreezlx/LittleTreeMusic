package com.example.littletreemusic.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.MainActivity;
import com.example.littletreemusic.activity.PlayActivity;
import com.example.littletreemusic.model.Song;
import com.example.littletreemusic.service.MusicService;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ZLX Vincent on 2017/8/29.
 */

public class BottomFragment0 extends Fragment implements View.OnClickListener{

    protected ImageView imgnote,imgstart,imgpause,imgnext,imgmenu;
    private TextView text0,text1;
    RelativeLayout mbottomtemp,bottom;
//    private Receiver receiver;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    String title,artist,playingTitle,playingArtist;
    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MusicService musicService;
    Intent serviceIntent;
    ServiceConnection serviceConnection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view= inflater.inflate(R.layout.fragment_main_bottom,container,false);

        mbottomtemp=(RelativeLayout)view.findViewById(R.id.bottom_temp);
//        bottom=(RelativeLayout)view.findViewById(R.id.fragment_main_bottom);
        imgnote=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgnote);
        imgstart=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgstart);
        imgpause=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgpause);
        imgnext=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgnext);
        imgmenu=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgmenu);
        text0=(TextView)view.findViewById(R.id.fragment_main_bottom_text0);
        text1=(TextView)view.findViewById(R.id.fragment_main_bottom_text1);
//
//        bottom.bringToFront();
//
        imgnote.setOnClickListener(this);
        imgstart.setOnClickListener(this);
        imgpause.setOnClickListener(this);
        imgnext.setOnClickListener(this);
        imgmenu.setOnClickListener(this);


//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setBackgroundColor();
//        navigationView.setItemTextColor();
//        Resources resource=(Resources)getContext().getResources();
//        ColorStateList csl=(ColorStateList)resource.getColor(R.color.navigation_color);
//        navigationView.setItemTextColor(csl);
//        navigationView.getMenu().getItem(0).setChecked(true);

        bindMusicService();
        updateTAB();
//
        return view;
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.fragment_main_bottom_imgnote:
                imgnote.setSelected(true);
                Intent it_playactivity=new Intent(getContext(), PlayActivity.class);
                startActivity(it_playactivity);
                break;

            case R.id.fragment_main_bottom_imgstart:
//                imgstart.setSelected(true);
//                Intent it_play = new Intent(getActivity(), MusicService.class);
//                it_play.putExtra("mode","bottom0_op");
//                it_play.putExtra("op","bottom_start");
//                getActivity().startService(it_play);

                if (musicService != null){
                    musicService.start();
                    updateTAB();
//                    imgstart.setVisibility(View.GONE);
//                    imgpause.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.fragment_main_bottom_imgpause:
                if (musicService != null){
                    musicService.pause();
                    updateTAB();
//                    imgstart.setVisibility(View.VISIBLE);
//                    imgpause.setVisibility(View.GONE);
                }

                break;

            case R.id.fragment_main_bottom_imgnext:
                if (musicService != null){
                    musicService.toNextSong(1);
                    updateTAB();
//                    imgstart.setVisibility(View.VISIBLE);
//                    imgpause.setVisibility(View.GONE);
                }
                break;


            case R.id.fragment_main_bottom_imgmenu:
                MainActivity mainActivity=(MainActivity)getActivity();
                drawerLayout=mainActivity.getDrawerLayout();

                drawerLayout.openDrawer(GravityCompat.END);
                break;

            default:break;
        }
    }


//    class Receiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent){
//
//            if (intent.getAction().equals("startTOpause")){
//                imgstart.setVisibility(View.GONE);
//                imgpause.setVisibility(View.VISIBLE);}
//            else if (intent.getAction().equals("pauseTOstart")){
//                imgstart.setVisibility(View.VISIBLE);
//                imgpause.setVisibility(View.GONE);}
//
//        }
//    }

    public void bindMusicService(){
        serviceIntent=new Intent(getContext(), MusicService.class);
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService=((MusicService.MusicBinder)service).getService();
                musicService.setOnSongChangedListener(new MusicService.OnSongChangedListener() {
                    @Override
                    public void OnSongChanged() {
                        updateTAB();
                    }
                });
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {


            }
        };
        getActivity().bindService(serviceIntent,serviceConnection,Context.BIND_AUTO_CREATE);
    }


    //更新歌名，歌手
    public void updateTAB(){
//        SharedPreferences sp=getActivity().getSharedPreferences("sp0",MODE_PRIVATE);
//        playingTitle=sp.getString("playing_title","noTitle");
//        if (!playingTitle.equals("noTitle")){
//            text0.setText(playingTitle);
//        }
//        playingArtist=sp.getString("playing_artist","noArtist");
//        if (!playingArtist.equals("noArtist")){
//            text1.setText(playingArtist);
//        }
        String uristr=MusicService.playinguristr;
        if (uristr == null){
            SharedPreferences sp0 = getActivity().getSharedPreferences("sp0", Context.MODE_PRIVATE);
           uristr=sp0.getString("playing_uri","no_uri");
        }
        if (!uristr.equals("no_uri")){
            List<Song> list0= DataSupport.where("uri=?",uristr).find(Song.class);
            if (list0 != null && list0.size() !=0){
                playingTitle=list0.get(0).getTitle();
                playingArtist=list0.get(0).getArtist();
                text0.setText(playingTitle);
                text1.setText(playingArtist);
            }
        }

        if (MusicService.isPlaying){
            imgstart.setVisibility(View.GONE);
            imgpause.setVisibility(View.VISIBLE);
        }else {
            imgstart.setVisibility(View.VISIBLE);
            imgpause.setVisibility(View.GONE);
        }
    }



//切换页面时更新播放状态
    @Override
    public void onResume(){
        super.onResume();

        updateTAB();
        bindMusicService();
//        Toast.makeText(getActivity(),"asdasdadasd",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(getActivity(),"asdasdadasd",Toast.LENGTH_LONG).show();
//        getActivity().unregisterReceiver(receiver);
//        getActivity().unbindService(serviceConnection);
    }




//    @Override
//    public void onSaveInstanceState(Bundle outState){
//
//        text0.getText();
//        text1.getText();
//
//
//    }

}