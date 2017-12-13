package com.example.littletreemusic.activity;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.fragment.BodyFragment0;
import com.example.littletreemusic.fragment.BodyFragment1;
import com.example.littletreemusic.fragment.BottomFragment0;
import com.example.littletreemusic.fragment.TitleFragment0;
import com.example.littletreemusic.fragment.TitleFragment1;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.table.Song;
import com.example.littletreemusic.util.SearchSongs;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;


/**
 * 主界面。
 */


public class MainActivity extends FragmentActivity {

    private ImageView imageView;
    private Drawable bp;
    private TextView textView;
    static FragmentManager fm;
    FragmentTransaction ft;
    GridView gridView;
    public RelativeLayout mtitletemp;
    private IntentFilter intentFilter;
    Intent serviceIntent;
//    private MusicService.MusicBinder musicBinder;

    ContentResolver contentResolver;
    String bpIdstr, bpPathstr;
    Fragment title0,title1,title2,body0,body1,body2,bottom0;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    List<Song> songs;




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

        setContentView(R.layout.activity_main);

        LitePal.getDatabase();
        contentResolver = getContentResolver();

//        暂时先放这
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            SearchSongs.searchSongs(contentResolver);
        }

        Connector.getDatabase();

        navigationView=(NavigationView)findViewById(R.id.nav_view);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout) ;
        imageView = (ImageView) findViewById(R.id.background_picture_main);
//设背景
        initBP();

//        SharedPreferences sp=getSharedPreferences("sp0",MODE_PRIVATE);
//        String bpIdstr=sp.getString("bp_default","no_bpId");
//        if (bpIdstr.equals("no_bpId")){
//            imageView = (ImageView) findViewById(R.id.background_picture_main);
//            bp = ContextCompat.getDrawable(this, R.drawable.bp_0);
//
//        }else{
//            int bpId = getResources().getIdentifier(bpIdstr, "drawable", "com.example.littletree");
//            bp = ContextCompat.getDrawable(this,bpId);
//
//        }
//        imageView.setImageDrawable(bp);

//加入三个碎片
        fm=getSupportFragmentManager();
        initFragment();



        navigationView.setCheckedItem(R.id.nav_changebp);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_changebp:

                        Intent bpIntent=new Intent(MainActivity.this,ChangebpActivity.class);
                        startActivityForResult(bpIntent,0);
                        break;
                    case R.id.nav_about:

                        break;
                    case R.id.nav_quit:
                        System.exit(0);
                        break;

                    default:break;
                }
                return true;
            }
        });


    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        Intent serviceIntent=new Intent(this, MusicService.class);
        stopService(serviceIntent);
    }

//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        //阻止activity保存fragment的状态
//        super.onSaveInstanceState(outState);
//    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 0:
                if (data != null){
                    if (data.getAction().equals("changeBP")){
                        initBP();
                    }
                }

                break;


            default:break;
        }

    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, String[] permissions,
                                             int[] grantResults){
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SearchSongs.searchSongs(contentResolver);
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public void initBP(){
        SharedPreferences sp=getSharedPreferences("sp0",MODE_PRIVATE);

        bpIdstr=sp.getString("bp_default","no_bpId");
        if (! bpIdstr.equals("no_bpId")){
            int bpId = getResources().getIdentifier(bpIdstr, "drawable", "com.example.littletreemusic");
            bp = ContextCompat.getDrawable(this,bpId);
            imageView.setImageDrawable(bp);
        }else if (bpIdstr.equals("no_bpId")){
            bpPathstr=sp.getString("bp_album","no_bpId");
            Bitmap bitmap = BitmapFactory.decodeFile(bpPathstr);
            imageView.setImageBitmap(bitmap);
        }

        if (bpIdstr.equals("no_bpId") && bpPathstr.equals("no_bpId")){
            bp = ContextCompat.getDrawable(this, R.drawable.bp_0);
            imageView.setImageDrawable(bp);
        }

    }



    public void initFragment(){
        title0 = new TitleFragment0();
        body0 = new BodyFragment0();
        bottom0 = new BottomFragment0();
        fm.beginTransaction().add(R.id.title_temp, title0)
                .add(R.id.body_temp, body0).add(R.id.bottom_temp,bottom0).commit();
    }
    public void toMymusic(){

        title1=new TitleFragment1();
        body1=new BodyFragment1();
        Bundle bundle=new Bundle();
        bundle.putInt("mode",0);
        body1.setArguments(bundle);
        title1.setArguments(bundle);
        fm.beginTransaction().add(R.id.title_temp,title1).add(R.id.body_temp,body1)
                .hide(title0).hide(body0).commit();
    }
    public void toMyfavourite(){
        title1=new TitleFragment1();
        body1=new BodyFragment1();
        Bundle bundle=new Bundle();
        bundle.putInt("mode",1);
        body1.setArguments(bundle);
        title1.setArguments(bundle);
        fm.beginTransaction().add(R.id.title_temp,title1).add(R.id.body_temp,body1)
                .hide(title0).hide(body0).commit();
    }
    public void back(){
        fm.beginTransaction().remove(title1).remove(body1).show(title0).show(body0).commit();
    }

    @Override
    public void onBackPressed() {
        //方式一：将此任务转向后台
        moveTaskToBack(false);
        super.onBackPressed();
        //方式二：返回手机的主屏幕
    /*Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addCategory(Intent.CATEGORY_HOME);
    startActivity(intent);*/
    }



    public DrawerLayout getDrawerLayout(){
        return drawerLayout;
    }
//    private ServiceConnection connection = new ServiceConnection() {
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//
//        }
//    };


//    private ServiceConnection serviceConnection=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    }








}
