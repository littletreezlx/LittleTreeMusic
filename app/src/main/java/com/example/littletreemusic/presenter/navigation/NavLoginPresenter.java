package com.example.littletreemusic.presenter.navigation;

import android.content.SharedPreferences;

import com.example.littletreemusic.pojo.LoginInfo;
import com.example.littletreemusic.util.CommunityRetrofitRequest;
import com.example.littletreemusic.util.NetworkUtil;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class NavLoginPresenter implements NavLoginContract.INavLoginPresenter {


    @Inject
    NavLoginContract.INavLoginView mINavLoginView;
    @Inject
    SharedPreferences sp;
    @Inject
    Retrofit retrofit;
    @Inject
    Gson gson;
    @Inject
    NetworkUtil networkUtil;

    String mAccount,mPassword;
    boolean mIsWrong,mIsChecked;

    @Override
    public void login(final String account, final String password, final Boolean isChecked) {
//        验证账号密码是否符合规则
        mAccount=account;
        mPassword=password;
        mIsChecked=isChecked;
        mIsWrong=false;
        if (!mIsWrong && mAccount.length()!=11){
            mINavLoginView.showLoginFailedReason("注意账号格式");
            mIsWrong=true;
        }
        if (!mIsWrong && mPassword.length() < 6){
            mINavLoginView.showLoginFailedReason("注意密码格式");
            mIsWrong=true;
        }
        if (!mIsWrong && networkUtil.checkIsConnected()){
            mINavLoginView.showLoginFailedReason("网络未连接");
        }
//        账号密码没错而且网络连接存在才进行传输。
        if (!mIsWrong){
            pushLoginToServer();
        }

    }

    private void pushLoginToServer(){
        CommunityRetrofitRequest communityRR = retrofit.create(CommunityRetrofitRequest.class);
        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setAccount(mAccount);
        loginInfo.setPassword(mPassword);
//        String loginInfoJson = gson.toJson(loginInfo);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), loginInfoJson);
        communityRR.login(loginInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(Response<ResponseBody> body) throws Exception {
//                        Log.d("a",body);
                        String uuid=body.body().string();
                        if (uuid.length()==16){
                            mINavLoginView.showLoginSucceed();
                            saveToSP(uuid);
                        }else {
                            mINavLoginView.showLoginFailedReason("账号密码错误");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mINavLoginView.showLoginFailedReason("网络连接错误");
                    }
                });
    }

    private void saveToSP(String uuid){
        if (mIsChecked){
            sp.edit().putString("uuid",uuid)
                    .putString("account",mAccount)
                    .putString("password",mPassword)
                    .putInt("ischecked",1)
                    .apply();
        }else {
            sp.edit().putString("uuid",uuid)
                    .remove("account")
                    .remove("password")
                    .remove("ischecked")
                    .apply();
        }


    }

}

