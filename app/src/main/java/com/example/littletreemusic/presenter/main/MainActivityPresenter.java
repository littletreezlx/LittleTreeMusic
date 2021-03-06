package com.example.littletreemusic.presenter.main;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.littletreemusic.pojo.LoginInfo;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.util.CommunityRetrofitRequest;

import org.litepal.crud.DataSupport;

import java.util.List;

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

public class MainActivityPresenter implements MainActivityContract.IMainActivityPresenter {

    @Inject
    SharedPreferences sp;
    @Inject
    Retrofit retrofit;
    @Inject
    MainActivityContract.IMainActivityView mainActivityView;


    private Uri externalContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private Uri internalContentUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
    private String sortOrder = MediaStore.Audio.Media.TITLE;

    private ContentResolver contentResolver;
    private List<Song> songList;
    private Song playingSong;
//    MainActivityContract.ISearchSongIview mISearchSongIview;
    private String where = "is_music > 0";

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


//    @Override
//    public void bindView(MainActivityContract.ISearchSongIview view) {
//        mISearchSongIview=view;
//    }

    public MainActivityPresenter(Context context) {
        contentResolver=context.getContentResolver();
    }

    @Override
    public void autoLogin() {

        String account=sp.getString("account","");
        String password=sp.getString("password","");
        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setAccount(account);
        loginInfo.setPassword(password);
        CommunityRetrofitRequest communityRR = retrofit.create(CommunityRetrofitRequest.class);
        communityRR.login(loginInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(Response<ResponseBody> body) throws Exception {
//                        Log.d("a",body);
                        String token=body.body().string();
                        if (!token.isEmpty()){
                            mainActivityView.autoLoginSucceed();
                            sp.edit().putString("Authorization",token).apply();
                        }else {
                            mainActivityView.autoLoginFaided();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mainActivityView.autoLoginFaided();
                    }
                });

    }

    @Override
    public void searchSongs() {
        mainActivityView.showWaitingView();
        //        LitePal.getDatabase();
        //利用ContentResolver的query函数来查询数据，然后将得到的结果放到Song对象中，最后放到数组中
        Cursor cursor = contentResolver.query(externalContentUri, null, where, null, sortOrder);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = contentResolver.query(externalContentUri, null, where, null, sortOrder);
                List<Song> songs = DataSupport.findAll(Song.class);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            if (songs.size() != 0) {
                                boolean hasSameSong =false;
//                       如果songs包含指针得到的音乐localId，则说明是已存在的音乐,从songs中移去.
//                       最后剩下的songs则是曾经添加过，但现在不存在于手机存储中的歌曲。 需要在数据库中删除数据。
                                long localId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));//音乐本地id
                                for (int i = 0; i < songs.size(); i++) {
                                    hasSameSong = false;
                                    if (songs.get(i).getLocalId() == localId) {
//                                虽然文件的localId是唯一值，但不排除该文件删除后，
//                                又产生同一localId的文件。所以在加一重title判断比较保险。
                                        String title = cursor.getString((cursor
                                                .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题
                                        if (songs.get(i).getTitle().equals(title)){
                                            hasSameSong = true;
                                            songs.remove(i);
                                            break;
                                        }
                                    }
                                }
//                        添加不存在于数据库中的歌曲
                                if (!hasSameSong){
                                    saveSongInfoToDB(cursor);
                                }
//                        表示数据库无歌曲信息，第一次添加歌曲，全部注入
                            }else {
                                saveSongInfoToDB(cursor);
                            }
                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                    mainActivityView.cancelWaitingView();
                }

//        List<Song> ss=songs;
//        最后删除现在不存在于手机存储中的歌曲
                for (Song lostSong:songs){
//            if (lostSong.isSaved()){
//                lostSong.delete();
//            }
                    DataSupport.delete(Song.class,lostSong.getId());
                }
            }
        }).run();
    }

    private void saveSongInfoToDB(Cursor cursor) {
        String title = cursor.getString((cursor
                .getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题

        String artist = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家

        long localId = cursor.getLong(cursor
                .getColumnIndex(MediaStore.Audio.Media._ID));//音乐本地id

        long duration = cursor.getLong(cursor
                .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长

        long size = cursor.getLong(cursor
                .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小

        String uri = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.DATA));  //文件路径

        String album = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片

//        long album_id = cursor.getLong(cursor
//                .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID

        int isMusic = cursor.getInt(cursor
                .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐

        String displayName = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

        String contentType = cursor.getString(cursor
                .getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));

        if (isMusic != 0 && duration / (1000 * 60) >= 1) {//只把1分钟以上的音乐添加到集合当中
            Song song = new Song();
            song.setLocalId(localId);
            song.setTitle(title);
            song.setArtist(artist);
            song.setDuration(duration);
            song.setSize(size);
            song.setUri(uri);
            song.setAlbum(album);
            song.setDisplayName(displayName);
            song.setContentType(contentType);
            boolean i=song.save();
            if (!i){
                Log.d("i", "wrong!");
            }

        }
    }

}
