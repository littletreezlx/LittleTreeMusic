package com.example.littletreemusic.activity.main;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.play.PlayActivity;
import com.example.littletreemusic.application.MyApplication;
import com.example.littletreemusic.base.BaseActivity;
import com.example.littletreemusic.di.Component.main.DaggerMainActivityComponent;
import com.example.littletreemusic.di.Component.main.MainActivityComponent;
import com.example.littletreemusic.di.Component.main.MainActivityModule;
import com.example.littletreemusic.pojo.StringEvent;
import com.example.littletreemusic.presenter.community.CommunityFMPresenter;
import com.example.littletreemusic.presenter.main.MainActivityContract;
import com.example.littletreemusic.presenter.main.MainActivityPresenter;
import com.example.littletreemusic.presenter.main.MainFMPresenter;
import com.example.littletreemusic.presenter.navigation.NavFMPresenter;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.view.WaitingCircle;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 主界面。
 */


public class MainActivity extends BaseActivity implements MainActivityContract.IMainActivityView {

    @BindView(R.id.main_naviationview)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @BindView(R.id.main_activity_layout)
    PercentRelativeLayout mainLayout;

    ImageView img_header;
    Button btn_login;

    @Inject
    NavFMPresenter navFMPresenter;
    @Inject
    MainFMPresenter mainFMPresenter;
    @Inject
    CommunityFMPresenter communityFMPresenter;
    @Inject
    MainActivityPresenter mainActivityPresenter;
    @Inject
    MusicService musicService;

    MainActivityComponent mainActivityComponent;
    ServiceConnection serviceConnection;
    AnimatorSet animatorSet;


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
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .mainActivityModule(new MainActivityModule(this, this))
                .build();
        mainActivityComponent.inject(this);
        Log.d(TAG, musicService.toString());
        mainActivityComponent.inject(mainFMPresenter);
        mainActivityComponent.inject(navFMPresenter);
        mainActivityComponent.inject(communityFMPresenter);
        mainActivityComponent.inject(mainActivityPresenter);

        EventBus.getDefault().register(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mainActivityPresenter.searchSongs();
//                    等待的view显示出来！
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /** 触摸时按下的点 **/
        PointF downP = new PointF();
//        /** 触摸时当前的点 **/
//        PointF curP = new PointF();

//        curP.x = event.getX();
        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN:

                downP.x = event.getX();
                downP.y = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() - downP.x < -200) {
                    Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                    startActivity(intent);
                }
                if (event.getY() - downP.y > 200) {
                    mainActivityPresenter.searchSongs();
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }

        return true;
    }

    public void turnLoginToSign() {
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

        WaitingCircle waitingCircle=new WaitingCircle(this,300,300);
//        Path interpolatorPath = new Path();
//        interpolatorPath.lineTo(0.25f, 0.25f);
//        interpolatorPath.moveTo(0.25f, 1.5f);
//        interpolatorPath.lineTo(1, 1);
//        waitingCircle.animate().translationXBy(200)
//                .translationX(-400)
//                .alpha(0.5f)
//                .setInterpolator(new PathInterpolator(interpolatorPath))
//                .setDuration(3000);

        ObjectAnimator anim0 = ObjectAnimator.ofFloat(waitingCircle, "alpha", 1f, 0.5f);
//        anim0.setDuration(3000);
//                anim0.setInterpolator(new AnticipateOvershootInterpolator());

        Keyframe keyframe0 = Keyframe.ofFloat(0, 1f);
        Keyframe keyframe1 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress", keyframe0, keyframe1);
        ObjectAnimator anim1 = ObjectAnimator.ofPropertyValuesHolder(waitingCircle, holder);

        ObjectAnimator anim2 = ObjectAnimator.ofArgb(waitingCircle, "color", 0xffff0000, 0xff00ff00);
//                ObjectAnimator anim1 = ObjectAnimator.ofFloat(circle, "Progress", 0,3,1,5);
//        anim1.setDuration(3000);
//        anim1.setInterpolator(new AnticipateOvershootInterpolator());

        animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animatorSet.play(anim0).with(anim1).with(anim2);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    public void cancelWaitingView() {
        animatorSet.cancel();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onEvent(StringEvent stringEvent) {
        switch (stringEvent.getStr()) {
            case "changebp":
                setGlobalBackGround();
                break;
            default:
                break;
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
        } else if (communityFMPresenter.nowLocation != 0) {
            communityFMPresenter.backPressed();
        } else if (!mainFMPresenter.isHomePage) {
            mainFMPresenter.backPressed();
        } else {
//            将此任务转向后台
            moveTaskToBack(false);
        }


    }

    public void getDrawerLayout() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    public MainActivityComponent getMainActivityComponent() {
        return mainActivityComponent;
    }

}
