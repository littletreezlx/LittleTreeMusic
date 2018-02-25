package com.example.littletreemusic.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 春风亭lx小树 on 2017/12/28.
 */

public class NetworkUtil {

//    Context mContext;
    ConnectivityManager mConnectivityManager;
    NetworkInfo networkInfo;


    public NetworkUtil(Context context){
        mConnectivityManager=
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean checkIsConnected(){
            networkInfo=mConnectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                return false;
            }else if (networkInfo.isConnected()){
                return true;
            }
            else{
                return false;
            }
    }

    public boolean checkIsWifi(){
        networkInfo=mConnectivityManager.getActiveNetworkInfo();
        if (checkIsConnected()){
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

}