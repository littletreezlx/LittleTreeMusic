package com.example.littletreemusic.di.Module;


import com.example.littletreemusic.di.scopes.PerActivity;
import com.example.littletreemusic.presenter.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 春风亭lx小树 on 2018/2/19.
 */

@Module
public class LoginModule {

    LoginContract.ILoginView mILoginView;
    public LoginModule(LoginContract.ILoginView iLoginView){
        mILoginView=iLoginView;
    }


    @Provides
    @PerActivity
    public  LoginContract.ILoginView provideILoginView(){
        return mILoginView;
    }

}
