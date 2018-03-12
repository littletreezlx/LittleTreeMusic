package com.example.littletreemusic.util.common;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import okhttp3.ResponseBody;

/**
 * Created by 春风亭lx小树 on 2018/3/11.
 */

public class FileUtil {


    @Inject
    public FileUtil() {
    }




    public void saveResponseBody(ResponseBody body,String fileName) {

        try {

            Environment.getDataDirectory()
            String sdcardPath2 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/abc";

            File file = new File(sdcardPath2,"travel.mp3");

//            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
