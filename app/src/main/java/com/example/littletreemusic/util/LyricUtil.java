package com.example.littletreemusic.util;

import com.example.littletreemusic.pojo.Lyric;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 春风亭lx小树 on 2018/1/2.
 */

public class LyricUtil {
//
//    private static String lyricPath;
//
//
    /**
     * 传入的参数为标准歌词字符串
     */
    public static List<Lyric> parseStr2List(String lrcStr) {
        List<Lyric> list = new ArrayList<>();
        String lrcText = lrcStr.replaceAll("&#58;", ":")
                .replaceAll("&#10;", "\n")
                .replaceAll("&#46;", ".")
                .replaceAll("&#32;", " ")
                .replaceAll("&#45;", "-")
                .replaceAll("&#13;", "\r").replaceAll("&#39;", "'");
        String[] split = lrcText.split("\n");
        for (int i = 0; i < split.length; i++) {
            String lrc = split[i];
            if (lrc.contains(".")) {
                String min = lrc.substring(lrc.indexOf("[") + 1, lrc.indexOf("[") + 3);
                String seconds = lrc.substring(lrc.indexOf(":") + 1, lrc.indexOf(":") + 3);
                String mills = lrc.substring(lrc.indexOf(".") + 1, lrc.indexOf(".") + 3);
                long startTime = Long.valueOf(min) * 60 * 1000 + Long.valueOf(seconds) * 1000 + Long.valueOf(mills) * 10;
                String text = lrc.substring(lrc.indexOf("]") + 1);
//                if (text == null || "".equals(text)) {
//                    text = "music";
//                }
                Lyric Lyric = new Lyric();
                Lyric.setStart(startTime);
                Lyric.setLrc(text);
                list.add(Lyric);
                if (list.size() > 1) {
                    list.get(list.size() - 2).setEnd(startTime);
                }
                if (i == split.length - 1) {
                    list.get(list.size() - 1).setEnd(startTime + 100000);
                }
            }
        }
        return list;
    }
//
//
////   检查Lyric文件夹是否存在，没有则创建
//    private static  void checkDirs(){
//        lyricPath= Environment.getExternalStorageDirectory().getPath()
//                + File.separator + "Music"+ File.separator + "lyric";
//        File file = new File(lyricPath);
//        if(!file.exists()){
//            try {
//                boolean sucess = file.mkdirs();
//                //file is create
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /** 将歌词保存到本地，写入外存中 */
//    private static void saveLyric(Context context,String content) {
//        checkDirs();
//        Song playingSong = DataSupport.where("uri=?", MusicService.playingUriStr).findFirst(Song.class);
//        if (playingSong != null) {
//            String Title=playingSong.getTitle();
//            String Artist=playingSong.getArtist();
//            String savePath = lyricPath+File.separator+Title+" - "+Artist+".lrc";
//            File file = new File(savePath);
//            try {
//                OutputStream outstream = new FileOutputStream(file);
//                OutputStreamWriter out = new OutputStreamWriter(outstream);
//                out.write(content);
//                out.close();
//            } catch (java.io.IOException e) {
//                e.printStackTrace();
//                Log.i("AAA", "很遗憾，将歌词写入外存时发生了IO错误");
//            }
//            Log.i("AAA", "歌词保存成功");
//
////      歌词保存成功后将歌词地址uri存入数据库
//            String lyricUri = file.toURI().toString();
//            int updateId=playingSong.getId();
//            Song updateSong = new Song();
//            updateSong.setLyricUri(lyricUri);
//            updateSong.update(updateId);
//        }
//    }
//
//    /** 从本地读取歌词文件 */
//    private static void loadLyric() {
//        checkDirs();
//        String savePath = lyricPath+File.separator+Title+" - "+Artist+".lrc";
//        File file = new File(savePath);
//        try {
//            InputStream inputStream = new FileInputStream(file);
//            InputStreamReader in = new InputStreamReader(inputStream);
//            String in.read();
//            out.close();
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//            Log.i("AAA", "很遗憾，将歌词写入外存时发生了IO错误");
//        }
//        Log.i("AAA", "歌词保存成功");





}