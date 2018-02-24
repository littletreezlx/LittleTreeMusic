package com.example.littletreemusic.activity.main;


import android.Manifest;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.littletreemusic.application.MyApplication;
import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.changebp.ChangebpActivity;
import com.example.littletreemusic.base.BaseActivity;
import com.example.littletreemusic.di.Component.main.DaggerMainActivityComponent;
import com.example.littletreemusic.di.Component.main.MainActivityComponent;
import com.example.littletreemusic.di.Component.main.MainActivityModule;
import com.example.littletreemusic.presenter.main.MainFMPresenter;
import com.example.littletreemusic.presenter.main.SearchSongPresenter;
import com.example.littletreemusic.service.MusicService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 主界面。
 */


public class MainActivity extends BaseActivity {

    @BindView(R.id.main_naviationview)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @Inject
    MainFMPresenter mainFMPresenter;
    @Inject
    SearchSongPresenter searchSongPresenter;
    @Inject
    MusicService musicService;

    MainActivityComponent mainActivityComponent;
    ServiceConnection serviceConnection;


    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void setView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onCreateBusiness() {
        mainActivityComponent=DaggerMainActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
        mainActivityComponent.inject(this);
        Log.d(TAG, musicService.toString());
        mainActivityComponent.inject(mainFMPresenter);
        mainActivityComponent.inject(searchSongPresenter);
        mainFMPresenter.init(this);
        searchSongPresenter.init(getContentResolver());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            searchSongPresenter.searchSongs();
//                    等待的view显示出来！
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

//        serviceConnection=new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                musicService = ((MusicService.MusicBinder) service).getService();
//            }
//
//            @Override
//            public void onServiceDisconnected (ComponentName name){
//            }
//        };
//        Intent serviceIntent = new Intent(this,MusicService.class);
//        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        navigationView.setCheckedItem(R.id.nav_changebp);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_changebp:
                        Intent bpIntent = new Intent(MainActivity.this, ChangebpActivity.class);
                        startActivityForResult(bpIntent, 0);
                        break;
                    case R.id.nav_about:

                        break;
                    case R.id.nav_quit:
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent serviceIntent = new Intent(this, MusicService.class);
        unbindService(serviceConnection);
        stopService(serviceIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null && data.getAction().equals("changeBP")) {
                    setGlobalBackGround();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    searchSongPresenter.searchSongs();
//                    等待的view显示出来！
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else if (!mainFMPresenter.isHomePage) {
            mainFMPresenter.backPressed();
        } else {
//            将此任务转向后台
            moveTaskToBack(false);
        }
    }

    public void getDrawerLayout(){
        drawerLayout.openDrawer(GravityCompat.END);
    }

    public MainActivityComponent getMainActivityComponent(){
        return mainActivityComponent;
    }

}
