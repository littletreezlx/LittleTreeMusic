package com.example.littletreemusic.activity.main;


import android.Manifest;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.application.MyApplication;
import com.example.littletreemusic.base.BaseActivity;
import com.example.littletreemusic.di.Component.main.DaggerMainActivityComponent;
import com.example.littletreemusic.di.Component.main.MainActivityComponent;
import com.example.littletreemusic.di.Component.main.MainActivityModule;
import com.example.littletreemusic.pojo.StringEvent;
import com.example.littletreemusic.presenter.main.MainActivityContract;
import com.example.littletreemusic.presenter.main.MainFMPresenter;
import com.example.littletreemusic.presenter.main.MainActivityPresenter;
import com.example.littletreemusic.presenter.navigation.NavFMPresenter;
import com.example.littletreemusic.service.MusicService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 主界面。
 */


public class MainActivity extends BaseActivity implements MainActivityContract.IMainActivityView{

    @BindView(R.id.main_naviationview)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    ImageView img_header;
    Button btn_login;

    @Inject
    NavFMPresenter navFMPresenter;
    @Inject
    MainFMPresenter mainFMPresenter;
    @Inject
    MainActivityPresenter mainActivityPresenter;
    @Inject
    MusicService musicService;

    MainActivityComponent mainActivityComponent;
    ServiceConnection serviceConnection;


    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void setView() {
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void onCreateBusiness() {
        mainActivityComponent=DaggerMainActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .mainActivityModule(new MainActivityModule(this,this))
                .build();
        mainActivityComponent.inject(this);
        Log.d(TAG, musicService.toString());
        mainActivityComponent.inject(mainFMPresenter);
        mainActivityComponent.inject(navFMPresenter);
        mainActivityComponent.inject(mainActivityPresenter);
        EventBus.getDefault().register(this);
        mainFMPresenter.init();
        navFMPresenter.init();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mainActivityPresenter.searchSongs();
//                    等待的view显示出来！
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

//        开启应用自动登陆
        mainActivityPresenter.autoLogin();

        navigationView.setCheckedItem(R.id.nav_changebp);
        View headerView = navigationView.getHeaderView(0);
        img_header = (ImageView) headerView.findViewById(R.id.nav_header_img);
        btn_login = (Button) headerView.findViewById(R.id.nav_header_btnlogin);
        img_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navFMPresenter.toNavHeadshots();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navFMPresenter.toNavLogin();
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_personalInfo:
                        navFMPresenter.toNavPersonInfo();
                        break;
                    case R.id.nav_changebp:
                        navFMPresenter.toNavChangeBP();
                        break;
                    case R.id.nav_sleep:

                        break;
                    case R.id.nav_about:
                        navFMPresenter.toNavAbout();
                        break;
                    case R.id.nav_setting:
                        navFMPresenter.toNavSetting();
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

    public void turnLoginToSign(){
        btn_login.setText("签到");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void autoLoginSucceed() {
        turnLoginToSign();
    }

    @Override
    public void autoLoginFaided() {

    }

    @Override
    public void showWaitingView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEvent(StringEvent stringEvent) {
        switch (stringEvent.getStr()){
            case "changebp":
                setGlobalBackGround();
                break;
            default:break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 0:
//                if (data != null && data.getAction().equals("changeBP")) {
//                    setGlobalBackGround();
//                }
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mainActivityPresenter.searchSongs();
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
        } else if (navFMPresenter.nowLocation != 0) {
            navFMPresenter.backPressed();
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
