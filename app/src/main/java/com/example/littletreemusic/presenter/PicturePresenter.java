package com.example.littletreemusic.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class PicturePresenter implements PictureContract.IPicturePresenter {


//    @Inject
//    public PicturePresenter(){
//    }




//    @Override
//    public void init(Context context) {
//
//    }

    //    根据歌曲路径获得专辑封面
    @Override
    public Bitmap findBitmapByFilePath(String filePath){
        Bitmap bitmap = null;
        //能够获取多媒体文件元数据的类
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath); //设置数据源
            byte[] embedPic = retriever.getEmbeddedPicture(); //得到字节型数据
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); //转换为图片
//            要优化后再加载
//            bitmap= BitmapUtil.decodeBitmapByByteArray(embedPic,80,80);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }







}



////      获取默认专辑图片
//
//    public  Bitmap getDefaultArtwork(Context context,boolean small) {
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inPreferredConfig = Bitmap.Config.RGB_565;
//        if(small){  //返回小图片
//            return BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_note);
//        }
//        return BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_note1);
//    }
//
//
//    //    从文件当中获取专辑封面位图
//private  Bitmap getArtworkFromFile(Context context, long songid, long albumid){
//    Bitmap bm = null;
//    if(albumid < 0 && songid < 0) {
//        throw new IllegalArgumentException("Must specify an album or a song id");
//    }
//    try {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        FileDescriptor fd = null;
//        if(albumid < 0){
//            Uri uri = Uri.parse("content://media/external/audio/media/"
//                    + songid + "/albumart");
//            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
//            if(pfd != null) {
//                fd = pfd.getFileDescriptor();
//            }
//        } else {
//            Uri uri = ContentUris.withAppendedId(albumArtUri, albumid);
//            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
//            if(pfd != null) {
//                fd = pfd.getFileDescriptor();
//            }
//        }
//        options.inSampleSize = 1;
//        // 只进行大小判断
//        options.inJustDecodeBounds = true;
//        // 调用此方法得到options得到图片大小
//        BitmapFactory.decodeFileDescriptor(fd, null, options);
//        // 我们的目标是在800pixel的画面上显示
//        // 所以需要调用computeSampleSize得到图片缩放的比例
//        options.inSampleSize = 100;
//        // 我们得到了缩放的比例，现在开始正式读入Bitmap数据
//        options.inJustDecodeBounds = false;
//        options.inDither = false;
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//
//        //根据options参数，减少所需要的内存
//        bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
//    } catch (FileNotFoundException e) {
//        e.printStackTrace();
//    }
//    return bm;
//}