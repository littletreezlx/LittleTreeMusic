package com.example.littletreemusic.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.littletreemusic.R;

/**
 * Created by 春风亭lx小树 on 2018/2/13.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;
    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = true;
    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = true;
//    /** 当前Activity渲染的视图View **/
//    private View mContextView = null;
    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isSetStatusBar) {
            steepStatusBar();
        }
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
//        hideBottomUIMenu();
//      判断手机是否有物理按钮，然后计算底部的NavigationBar高度，最后设置试图边距。
        int navigationBarHeight=getNavigationBarHeight();
        if (navigationBarHeight!=0){
            getWindow().getDecorView().setPadding(0,0,0,navigationBarHeight);
        }
        Log.d(TAG, "BaseActivity-->onCreate()");
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
        setView();
        setGlobalBackGround();
        onCreateBusiness();

    }


    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * [初始化参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);

    /**
     * [绑定视图]
     *
     * @return
     */
    public abstract void setView();

    /**
     * 设置全局背景
     *
     */
    public void setGlobalBackGround(){
        //getDecorView 获得window最顶层的View
        View view = this.getWindow().getDecorView();
        Drawable bp;
        SharedPreferences sp=getSharedPreferences("sp",MODE_PRIVATE);
        //先找默认背景图片id
        String bpIdstr=sp.getString("bp_default","no_bp");
        if (! bpIdstr.equals("no_bp"))
        {
            int bpId = getResources().getIdentifier(bpIdstr, "drawable", "com.example.littletreemusic");
            bp = ContextCompat.getDrawable(this,bpId);
        }
        //如果没有就找相册图片地址
        else{
            String bpPathstr=sp.getString("bp_album","no_bp");
            if (!bpPathstr.equals("no_bp"))
            {
                bp =new BitmapDrawable(getResources(),bpPathstr);
            }
            //还是没有就用默认图片bp_0
            else {
                bp = ContextCompat.getDrawable(this, R.drawable.bp_0);
            }
        }
        view.setBackground(bp);
    }


    /**
     * [业务操作]
     *
     */
    public abstract void onCreateBusiness();


    /**
     * [页面跳转]
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        startActivity(new Intent(BaseActivity.this,clazz));
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

//    官方做法，只能隐藏一次！！！
    private void hideVirtualKey(){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public int getNavigationBarHeight() {

        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            Resources resources = getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        }
        else{
            return 0;
        }
    }

    /**
     * [简化Toast]
     * @param msg
     */
    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }


    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }



}
