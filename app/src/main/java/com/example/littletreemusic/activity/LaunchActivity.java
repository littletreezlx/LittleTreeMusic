package com.example.littletreemusic.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.main.MainActivity;


/**
 * 启动画面，3秒后跳入主界面。
 */


public class LaunchActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);

        }
        setContentView(R.layout.launch_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);


    }


    @Override
    protected void onDestroy(){
        super.onDestroy();


    }



}
