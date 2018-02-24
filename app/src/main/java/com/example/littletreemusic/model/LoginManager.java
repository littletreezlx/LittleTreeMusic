package com.example.littletreemusic.model;

import javax.inject.Inject;

/**
 * Created by 春风亭lx小树 on 2018/2/17.
 */

public class LoginManager {


    @Inject
    public LoginManager() {
    }

    //登录的监听器
    public static interface LoginListener{
        //登录成功
        void onSuccessLogin(String success);
        //登录失败
        void onFailedLogin(String failed);
    }

    public void login(String account,String password,LoginListener loginListener){


    }



}
