package com.example.littletreemusic.util.common;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.littletreemusic.pojo.ServerSong;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 春风亭lx小树 on 2018/3/11.
 */

public class FileUtil {

    private String appPath,cachePath;
    private String sdPath;
    public int nowProgress;

    public FileUtil(Context context) {
        appPath=context.getFilesDir().getPath()+File.separator;
        cachePath=context.getCacheDir().getAbsolutePath()+File.separator;
        sdPath=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
    }


    public void saveResponseBody(Response response) {

        ResponseBody body=response.body();
        String fileName=response.headers().get("filename");
        String filePath=appPath+fileName;
        try {
            File file = new File(filePath);
//            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + "Future Studio Icon.png");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[2048];
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
                    nowProgress=(int) (fileSizeDownloaded*100/fileSize);
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
//        保存路径到数据库
        Long id = Long.valueOf(response.headers().get("id"));
        ServerSong serverSong = DataSupport.where("id=?",id.toString()).findFirst(ServerSong.class);
        if (serverSong != null){
            serverSong.setFirstpushHeadShotsUri(filePath);
            serverSong.save();
        }

    }








}
