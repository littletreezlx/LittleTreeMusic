package com.example.littletreemusic.presenter.navigation;

import android.content.SharedPreferences;

import com.example.littletreemusic.pojo.RegisterMsg;
import com.example.littletreemusic.util.CommunityRetrofitRequest;
import com.example.littletreemusic.util.common.NetworkUtil;
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

public class NavRegisterPresenter implements NavRegisterContract.INavRegisterPresenter {


    @Inject
    NetworkUtil networkUtil;
    @Inject
    NavRegisterContract.INavRegisterView iNavRegisterView;
    @Inject
    SharedPreferences sp;
    @Inject
    Retrofit retrofit;
    @Inject
    Gson gson;

    boolean mIsWrong;

    @Override
    public void register(RegisterMsg registerMsg) {
        mIsWrong=false;
        if (!mIsWrong && registerMsg.getUsername().length()!=11){
            iNavRegisterView.showRegisterFailedReason("注意账号格式");
            mIsWrong=true;
        }
        if (!mIsWrong && registerMsg.getPassword().length() < 6){
            iNavRegisterView.showRegisterFailedReason("注意密码格式");
            mIsWrong=true;
        }


        if (!mIsWrong && networkUtil.checkIsConnected()){
            iNavRegisterView.showRegisterFailedReason("网络未连接");
        }


//        账号密码没错而且网络连接存在才进行传输。
        if (!mIsWrong){
            pushRegisterToServer(registerMsg);
        }
    }

    private void pushRegisterToServer(RegisterMsg registerMsg){
        CommunityRetrofitRequest communityRR = retrofit.create(CommunityRetrofitRequest.class);
        communityRR.register(registerMsg).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(Response<ResponseBody> body) throws Exception {
                        String bodyStr=body.body().string();
                        if ("register succeed".equals(bodyStr)){
                          iNavRegisterView.showRegisterSucceed();
                        }else {
                            iNavRegisterView.showRegisterFailedReason(bodyStr);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iNavRegisterView.showRegisterFailedReason("网络连接错误");
                    }
                });
    }


}

