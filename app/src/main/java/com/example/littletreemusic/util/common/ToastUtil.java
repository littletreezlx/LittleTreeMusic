package com.example.littletreemusic.util.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 春风亭lx小树 on 2018/3/5.
 */

public class ToastUtil {

    Context context;

    public ToastUtil(Context context) {
        this.context=context;
    }

    public void showShort(CharSequence message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showLong(CharSequence message)
    {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void show(CharSequence message, int duration)
    {

        Toast.makeText(context, message, duration).show();
    }



}
