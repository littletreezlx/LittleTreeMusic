package com.example.littletreemusic.util.common;

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
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean checkIsConnected(){
        networkInfo=mConnectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED)
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkIsWifi(){
        networkInfo=mConnectivityManager.getActiveNetworkInfo();
        if (checkIsConnected()){
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

}