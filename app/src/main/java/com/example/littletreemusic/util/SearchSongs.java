package com.example.littletreemusic.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.littletreemusic.table.Song;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 *
 */


public class SearchSongs {

    private static Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private Cursor cursor;

    //projection：选择的列; where：过滤条件; sortOrder：排序。
//    private String[] projection = {
//            MediaStore.Audio.Media._ID,
//            MediaStore.Audio.Media.DISPLAY_NAME,
//            MediaStore.Audio.Media.DATA,
//            MediaStore.Audio.Media.ALBUM,
//            MediaStore.Audio.Media.ARTIST,
//            MediaStore.Audio.Media.DURATION,
//            MediaStore.Audio.Media.SIZE
//    };
    //    private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma')  and is_music > 0 ";
    private static String sortOrder = MediaStore.Audio.Media.DATA;
//
//    public static SearchSongs searchStart(ContentResolver pontentResolver) {
//        if (searchSongs == null) {
//            contentResolver = pontentResolver;
//            searchSongs = new SearchSongs();
//        }
//        return searchSongs;
//    }

    public static void searchSongs(ContentResolver contentResolver) {

//        LitePal.getDatabase();
        //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
        Cursor cursor = contentResolver.query(contentUri, null, null, null, sortOrder);
        List<Song> idanotherSongs = DataSupport.select("idanother").find(Song.class);
        if (cursor.moveToFirst()) {
            if (idanotherSongs.size() == 0) {
                do {
                    long idanother = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media._ID));//音乐id
                    gosearch(cursor,idanother);

                } while (cursor.moveToNext());
            } else if (idanotherSongs.size() != 0) {
                do {
                    long idanother = cursor.getLong(cursor
                            .getColumnIndex(MediaStore.Audio.Media._ID));//音乐id

                    int j = 0;
                    for (int i = 0; i < idanotherSongs.size(); i++) {
                        if (idanother == idanotherSongs.get(i).getIdanother()) {
                            break;
                        }
                        if (i == idanotherSongs.size() - 1) {
                            gosearch(cursor,idanother);
                        }
                    }
                } while (cursor.moveToNext());
            }
        }
    }

    private static void gosearch(Cursor cursor,long idanother) {
        String title = cursor.getString((cursor
                .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题

        String artist = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家

        long duration = cursor.getLong(cursor
                .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长

        long songsize = cursor.getLong(cursor
                .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小

        String uri = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径

//            String album = cursor.getString(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片图片
//
//            long album_id = cursor.getLong(cursor
//                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID

        int isMusic = cursor.getInt(cursor
                .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐

        if (isMusic != 0 && duration / (1000 * 60) >= 1) {//只把1分钟以上的音乐添加到集合当中
            Song song = new Song();
            song.setIdanother(idanother);
            song.setTitle(title);
            song.setArtist(artist);
            song.setDuration(duration);
            song.setSize(songsize);
            song.setUri(uri);
            song.save();
        }
    }

}



