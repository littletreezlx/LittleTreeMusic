package com.example.littletreemusic.presenter.community;

import android.content.SharedPreferences;

import com.example.littletreemusic.pojo.ServerSongInfo;
import com.example.littletreemusic.presenter.navigation.NavLoginContract;
import com.example.littletreemusic.util.CommunityRetrofitRequest;
import com.example.littletreemusic.util.common.FileUtil;
import com.example.littletreemusic.util.common.NetworkUtil;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class RandomSongPresenter implements RandomSongContract.IRandomSongPresenter{

    @Inject
    NavLoginContract.INavLoginView mINavLoginView;
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
                .doOnNext(new Consumer<List<ServerSongInfo>>() {
                    @Override
                    public void accept(List<ServerSongInfo> serverSongInfos) throws Exception {

                    }
                })
//                将返回歌单flatmap处理，依此请求getHeadShots
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<ServerSongInfo>, ObservableSource<ServerSongInfo>>() {
                    @Override
                    public ObservableSource<ServerSongInfo> apply(List<ServerSongInfo> serverSongInfos) throws Exception {

                        return  io.reactivex.Observable.fromIterable(serverSongInfos);
                    }
                })
//                请求
                .flatMap(new Function<ServerSongInfo, ObservableSource<Response>>() {
                    @Override
                    public ObservableSource<Response> apply(ServerSongInfo serverSongInfo) throws Exception {
                        return communityRR.getHeadShots(serverSongInfo.getId());
                    }
                })
//                处理getHeadShots
                .subscribe(new Observer<Response>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Response response) {
                        response.headers().get("filename");
                        response.body();
                        fileUtil.

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }





}

