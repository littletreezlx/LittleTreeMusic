package com.example.littletreemusic.presenter.community;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.littletreemusic.pojo.ServerSong;
import com.example.littletreemusic.util.CommunityRetrofitRequest;
import com.example.littletreemusic.util.common.FileUtil;
import com.example.littletreemusic.util.common.NetworkUtil;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class RandomSongPresenter implements RandomSongContract.IRandomSongPresenter{

    @Inject
    RandomSongContract.IRandomSongView iRandomSongView;
    @Inject
    SharedPreferences sp;
    @Inject
    Retrofit retrofit;
    @Inject
    Gson gson;
    @Inject
    NetworkUtil networkUtil;
    @Inject
    FileUtil fileUtil;

    @Override
    public void getRandomSongs() {

        final CommunityRetrofitRequest communityRR = retrofit.create(CommunityRetrofitRequest.class);
//       先请求推荐歌单
        communityRR.getRandomSongIdList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<ServerSong>>() {
                    @Override
                    public void accept(List<ServerSong> serverSongs) throws Exception {
                        iRandomSongView.fillAdapter(serverSongs);
                        for (ServerSong serverSong : serverSongs){
                            List<ServerSong> songs = DataSupport.select("id").find(ServerSong.class);
                            for (ServerSong exist:songs){
                                if (exist.getId()== serverSong.getId()){
                                    break;
                                }
                                serverSong.save();
                            }
                        }
                    }
                })
//                将返回歌单flatmap处理，依此请求getHeadShots
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<ServerSong>, ObservableSource<ServerSong>>() {
                    @Override
                    public ObservableSource<ServerSong> apply(List<ServerSong> serverSongs) throws Exception {

                        return  io.reactivex.Observable.fromIterable(serverSongs);
                    }
                })
//                请求
                .flatMap(new Function<ServerSong, ObservableSource<okhttp3.Response>>() {
                    @Override
                    public ObservableSource<okhttp3.Response> apply(ServerSong serverSong) throws Exception {
                        return communityRR.getHeadShots(serverSong.getFirstpushUserid());
                    }
                })
//                处理getHeadShots
                .subscribe(new Observer<okhttp3.Response>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(okhttp3.Response response) {
                        fileUtil.saveResponseBody(response);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("randomsong", "onComplete");
                        iRandomSongView.showList();
                    }
                });
    }





}

