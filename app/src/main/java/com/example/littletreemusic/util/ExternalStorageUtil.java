package com.example.littletreemusic.util;

import android.os.Environment;

import javax.inject.Inject;

/**
 * Created by 春风亭lx小树 on 2018/3/2.
 */

public class ExternalStorageUtil {


    @Inject
    public ExternalStorageUtil() {
        super();
    }

    // 读/写检查
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    // 只读检查
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
