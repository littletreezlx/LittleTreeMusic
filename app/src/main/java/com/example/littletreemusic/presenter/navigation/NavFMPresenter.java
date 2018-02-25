package com.example.littletreemusic.presenter.navigation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.example.littletreemusic.R;
import com.example.littletreemusic.activity.main.MainActivity;
import com.example.littletreemusic.activity.navigation.NavAboutFragment;
import com.example.littletreemusic.activity.navigation.NavAlterPDFragment;
import com.example.littletreemusic.activity.navigation.NavChangeBPFragment;
import com.example.littletreemusic.activity.navigation.NavHeadshotsFragment;
import com.example.littletreemusic.activity.navigation.NavLoginFragment;
import com.example.littletreemusic.activity.navigation.NavPersonalInfoFragment;
import com.example.littletreemusic.activity.navigation.NavRegisterFragment;
import com.example.littletreemusic.activity.navigation.NavSettingFragment;

import javax.inject.Inject;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class NavFMPresenter implements NavFMContract.INavFMPresenter {

    @Inject
    MainActivity mainActivity;

    NavHeadshotsFragment headshots;
    NavPersonalInfoFragment personInfo;
    NavChangeBPFragment changeBP;
    NavAboutFragment about;
    NavSettingFragment setting;
    NavLoginFragment login;
    NavRegisterFragment register;
    NavAlterPDFragment alterPD;

    public boolean isHomePage=true,isNavigation=false;
    DrawerLayout mDrawerLayout;
    FragmentManager fm;
    FragmentTransaction transaction;
    public int nowLocation=0;
    private static final int LOCATION_HOME=0,LOCATION_HEADER=1,LOCATION_PERSONALINFO=2,
            LOCATION_CHANGEBP=3,LOCATION_ABOUT=4,LOCATION_SETTING=5,LOCATION_LOGIN=6,
            LOCATION_REGISTER=7,LOCATION_ALTERPD=8;


    @Override
    public void init() {
        fm=mainActivity.getFragmentManager();
        mDrawerLayout=mainActivity.drawerLayout;
        nowLocation=LOCATION_HOME;
    }

    @Override
    public void toNavHeadshots() {
        headshots=new NavHeadshotsFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_activity_layout,headshots);
        goOutHome();
        nowLocation=LOCATION_HEADER;
    }

    @Override
    public void fromNavHeadshots() {
        transaction=fm.beginTransaction();
        transaction.remove(headshots);
        headshots=null;
        goBackHome();
    }


    @Override
    public void toNavPersonInfo() {
        personInfo=new NavPersonalInfoFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_activity_layout,personInfo);
        goOutHome();
        nowLocation=LOCATION_PERSONALINFO;
    }

    @Override
    public void fromNavPersonInfo() {
        transaction=fm.beginTransaction();
        transaction.remove(personInfo);
        personInfo=null;
        goBackHome();
    }

    @Override
    public void toNavChangeBP() {
        changeBP=new NavChangeBPFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_activity_layout,changeBP);
        goOutHome();
        nowLocation=LOCATION_CHANGEBP;

    }

    @Override
    public void fromNavChangeBP() {
        transaction=fm.beginTransaction();
        transaction.remove(changeBP);
        changeBP=null;
        goBackHome();
    }

    @Override
    public void toNavAbout() {
        about=new NavAboutFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_activity_layout,about);
        goOutHome();
        nowLocation=LOCATION_ABOUT;
    }

    @Override
    public void fromNavAbout() {
        transaction=fm.beginTransaction();
        transaction.remove(about);
        about=null;
        goBackHome();
    }

    @Override
    public void toNavSetting() {
        setting=new NavSettingFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_activity_layout,setting);
        goOutHome();
        nowLocation=LOCATION_SETTING;
    }

    @Override
    public void fromNavSetting() {
        transaction=fm.beginTransaction();
        transaction.remove(setting);
        setting=null;
        goBackHome();
    }

    @Override
    public void toNavLogin() {
        login=new NavLoginFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_activity_layout,login);
        goOutHome();
        nowLocation=LOCATION_LOGIN;
    }

    @Override
    public void FromNavLogin() {
        transaction=fm.beginTransaction();
        transaction.remove(login);
        login=null;
        goBackHome();
    }

    @Override
    public void toNavRegister() {
        register=new NavRegisterFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_activity_layout,register)
                .hide(login).show(register);
        goOutHome();
        nowLocation=LOCATION_REGISTER;
    }

    @Override
    public void FromNavRegister() {
        transaction=fm.beginTransaction();
        transaction.remove(register).show(login);
        register=null;
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
        nowLocation=LOCATION_LOGIN;
    }

    @Override
    public void toNavAlterPD() {
        alterPD=new NavAlterPDFragment();
        transaction=fm.beginTransaction();
        transaction.add(R.id.main_activity_layout,alterPD)
                .hide(login).show(alterPD);
        goOutHome();
        nowLocation=LOCATION_ALTERPD;
    }

    @Override
    public void FromNavAlterPD() {
        transaction=fm.beginTransaction();
        transaction.remove(alterPD).show(login);
        alterPD=null;
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
        nowLocation=LOCATION_LOGIN;
    }

    @Override
    public void backPressed(){
        switch (nowLocation){
            case LOCATION_HOME:
                break;
            case LOCATION_HEADER:
                fromNavHeadshots();
                break;
            case LOCATION_PERSONALINFO:
                fromNavPersonInfo();
                break;
            case LOCATION_CHANGEBP:
                fromNavChangeBP();
                break;
            case LOCATION_ABOUT:
                fromNavAbout();
                break;
            case LOCATION_SETTING:
                fromNavSetting();
                break;
            case LOCATION_LOGIN:
                FromNavLogin();
                break;
            case LOCATION_REGISTER:
                FromNavRegister();
                break;
            case LOCATION_ALTERPD:
                FromNavAlterPD();
                break;
            default:break;
        }
    }

    private void goOutHome(){
        mDrawerLayout.closeDrawer(GravityCompat.END);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
    private void goBackHome(){
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
        nowLocation=LOCATION_HOME;
    }


}

