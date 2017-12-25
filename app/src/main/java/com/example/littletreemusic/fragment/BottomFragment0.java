package com.example.littletreemusic.fragment;

import android.content.BroadcastReceiver;
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

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.MainActivity;
import com.example.littletreemusic.activity.PlayActivity;
import com.example.littletreemusic.service.MusicService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ZLX Vincent on 2017/8/29.
 */

public class BottomFragment0 extends Fragment implements View.OnClickListener{

    protected ImageView imgnote,imgstart,imgpause,imgnext,imgmenu;
    private TextView text0,text1;
    RelativeLayout mbottomtemp;
    private Receiver receiver;
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
        imgnote=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgnote);
        imgstart=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgstart);
        imgpause=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgpause);
        imgnext=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgnext);
        imgmenu=(ImageView)view.findViewById(R.id.fragment_main_bottom_imgmenu);
        text0=(TextView)view.findViewById(R.id.fragment_main_bottom_text0);
        text1=(TextView)view.findViewById(R.id.fragment_main_bottom_text1);

        imgnote.setOnClickListener(this);
        imgstart.setOnClickListener(this);
        imgpause.setOnClickListener(this);
        imgnext.setOnClickListener(this);
        imgmenu.setOnClickListener(this);

        updateTAA();
//广播
//        localBroadcastManager=LocalBroadcastManager.getInstance(getContext());

        intentFilter=new IntentFilter();
        intentFilter.addAction("startTOpause");
        intentFilter.addAction("pauseTOstart");
        intentFilter.addAction("titleANDartist");
        receiver=new Receiver();
        getActivity().registerReceiver(receiver,intentFilter);

//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setBackgroundColor();
//        navigationView.setItemTextColor();
//        Resources resource=(Resources)getContext().getResources();
//        ColorStateList csl=(ColorStateList)resource.getColor(R.color.navigation_color);
//        navigationView.setItemTextColor(csl);
//        navigationView.getMenu().getItem(0).setChecked(true);



        serviceIntent=new Intent(getContext(), MusicService.class);
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService=((MusicService.MusicBinder)service).getService();
                musicService.setOnSongChangedListener(new MusicService.OnSongChangedListener() {
                    @Override
                    public void OnSongChangedListener1(String title, String artist) {
                        updateTAA();
                    }
                });
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        getActivity().bindService(serviceIntent,serviceConnection,Context.BIND_AUTO_CREATE);

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
                }
                break;

            case R.id.fragment_main_bottom_imgpause:
                if (musicService != null){
                    musicService.pause();
                }

                break;

            case R.id.fragment_main_bottom_imgnext:
                if (musicService != null){
                    musicService.toNext();
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


    class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){

            if (intent.getAction().equals("startTOpause")){
                imgstart.setVisibility(View.GONE);
                imgpause.setVisibility(View.VISIBLE);}
            else if (intent.getAction().equals("pauseTOstart")){
                imgstart.setVisibility(View.VISIBLE);
                imgpause.setVisibility(View.GONE);}

        }
    }


    //更新歌名，歌手
    private void updateTAA(){
        SharedPreferences sp=getActivity().getSharedPreferences("sp0",MODE_PRIVATE);
        playingTitle=sp.getString("playing_title","noTitle");
        if (!playingTitle.equals("noTitle")){
            text0.setText(playingTitle);
        }
        playingArtist=sp.getString("playing_artist","noArtist");
        if (!playingArtist.equals("noArtist")){
            text1.setText(playingArtist);
        }
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
        getActivity().unbindService(serviceConnection);
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