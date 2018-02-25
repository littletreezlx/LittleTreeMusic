package com.example.littletreemusic.activity.play;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.littletreemusic.R;
import com.example.littletreemusic.pojo.QueryLyric;
import com.example.littletreemusic.pojo.QuerySong;
import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.service.MusicService;
import com.example.littletreemusic.util.ShowApiRetrofitRequest;
import com.example.littletreemusic.view.LyricView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 春风亭lx小树 on 2017/12/30.
 */

public class ViewPagerFragment2 extends Fragment {


    @BindView(R.id.viewpager_button_queryLyric)
    Button btn_QueryLyric;
    @BindView(R.id.viewpager_lyricView)
    LyricView lyricView;
    Unbinder unbinder;

    @Inject
    MusicService musicService;

    String lrcStr;
    ShowApiRetrofitRequest showApiRequest;
    String queryTitle;
    Retrofit retrofit;
    Song playingSong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.play_viewpager2, container, false);
//            lrcStr = "[ti&#58;海阔天空&#32;&#40;Edited&#32;Version&#41;]&#10;[ar&#58;BEYOND]&#10;[al&#58;Words&#32;&#38;&#32;Music&#32;Final&#32;Live&#32;With&#32;家驹]&#10;[by&#58;]&#10;[offset&#58;0]&#10;[00&#58;00&#46;92]海阔天空&#32;&#32;&#45;&#32;BEYOND&#10;[00&#58;02&#46;27]词：黄家驹&#10;[00&#58;03&#46;32]曲：黄家驹&#10;[00&#58;04&#46;30]&#10;[00&#58;19&#46;17]今天我&#32;寒夜里看雪飘过&#10;[00&#58;25&#46;75]怀着冷却了的心窝飘远方&#10;[00&#58;30&#46;77]&#10;[00&#58;31&#46;60]风雨里追赶&#32;雾里分不清影踪&#10;[00&#58;37&#46;82]天空海阔你与我&#32;可会变&#10;[00&#58;43&#46;27]&#10;[00&#58;44&#46;14]多少次迎着冷眼与嘲笑&#10;[00&#58;50&#46;55]从没有放弃过心中的理想&#10;[00&#58;56&#46;02]&#10;[00&#58;56&#46;67]一刹那恍惚&#32;若有所失的感觉&#10;[01&#58;02&#46;65]不知不觉已变淡&#32;心里爱&#10;[01&#58;08&#46;64]&#10;[01&#58;09&#46;66]原谅我这一生不羁放纵爱自由&#10;[01&#58;15&#46;56]&#10;[01&#58;16&#46;40]也会怕有一天会跌倒&#10;[01&#58;22&#46;72]背弃了理想谁人都可以&#10;[01&#58;27&#46;84]&#10;[01&#58;28&#46;51]哪会怕有一天只你共我&#10;[01&#58;33&#46;89]&#10;[01&#58;43&#46;41]今天我&#32;寒夜里看雪飘过&#10;[01&#58;49&#46;76]怀着冷却了的心窝飘远方&#10;[01&#58;54&#46;86]&#10;[01&#58;55&#46;60]风雨里追赶&#32;雾里分不清影踪&#10;[02&#58;01&#46;92]天空海阔你与我&#32;可会变&#10;[02&#58;06&#46;61]&#10;[02&#58;08&#46;70]原谅我这一生不羁放纵爱自由&#10;[02&#58;14&#46;86]&#10;[02&#58;15&#46;55]也会怕有一天会跌倒&#10;[02&#58;21&#46;30]&#10;[02&#58;21&#46;83]背弃了理想谁人都可以&#10;[02&#58;27&#46;17]&#10;[02&#58;28&#46;08]哪会怕有一天只你共我&#10;[02&#58;33&#46;08]&#10;[02&#58;38&#46;06]仍然自由自我&#10;[02&#58;40&#46;57]&#10;[02&#58;41&#46;42]永远高唱我歌&#10;[02&#58;44&#46;42]走遍千里&#32;原谅我这一生不羁放纵爱自由&#10;[02&#58;55&#46;20]&#10;[02&#58;56&#46;14]也会怕有一天会跌倒&#10;[03&#58;02&#46;26]背弃了理想&#32;谁人都可以&#10;[03&#58;07&#46;48]&#10;[03&#58;08&#46;67]哪会怕有一天只你共我&#10;[03&#58;13&#46;58]&#10;[03&#58;14&#46;51]原谅我这一生不羁放纵爱自由&#10;[03&#58;21&#46;27]也会怕有一天会跌倒&#10;[03&#58;26&#46;00]&#10;[03&#58;27&#46;38]背弃了理想谁人都可以&#10;[03&#58;31&#46;94]&#10;[03&#58;33&#46;61]哪会怕有一天只你共我";
//            lyricView.setLrc(lrcStr);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @OnClick(R.id.viewpager_button_queryLyric)
    public void onViewClicked() {
        querySongId();
    }

    private void initRetrofit(){
        Gson gson = new GsonBuilder()
                .create();
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://route.showapi.com/")
//                    .client(okHttpClient)
                .build();
        showApiRequest = retrofit.create(ShowApiRetrofitRequest.class);

        List<Song> list0 = DataSupport.where("uri=?", MusicService.playingUriStr).find(Song.class);
        if (list0 != null && list0.size() != 0) {
            playingSong=list0.get(0);
            setLrcView();
        }
    }

    public void setLrcView() {
        lrcStr = playingSong.getLyricUri();
        if (lrcStr != null) {
            lyricView.setPlayer(musicService.getMediaPlayer());
            lyricView.setLrc(lrcStr);
        } else {
            btn_QueryLyric.setVisibility(View.VISIBLE);
            lyricView.setVisibility(View.INVISIBLE);
        }
    }

    public void querySongId() {
    showApiRequest.getSong(queryTitle, "1").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Response<QuerySong>>() {
                @Override
                public void accept(Response<QuerySong> response) throws Exception {
                    try {
                        if (response.body().getShowapi_res_body().getPagebean().getMaxResult()!=0){
                            List<QuerySong.ShowapiResBodyBean.PagebeanBean.ContentlistBean> querySongList =
                                    response.body().getShowapi_res_body().getPagebean().getContentlist();
                            String title = querySongList.get(0).getSongname();
                            String artist = querySongList.get(0).getSingername();
                            int songid = querySongList.get(0).getSongid();
                            queryLyricBysongid(songid);
                        }else
                            showToast();
                        }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    showToast();
                }
            });
    }

    public void queryLyricBysongid(int songid) {
        String songidstr = Integer.toString(songid);
        showApiRequest.getLyric(songidstr).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<QueryLyric>>() {
                    @Override
                    public void accept(Response<QueryLyric> response) throws Exception {
                        String lyricStr = response.body().getShowapi_res_body().getLyric();
                        if (lyricStr != null) {
                            lyricView.setLrc(lyricStr);
                            btn_QueryLyric.setVisibility(View.INVISIBLE);
                            lyricView.setVisibility(View.VISIBLE);
                        }else {
                            showToast();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showToast();
                    }
                });
    }

    public void showToast(){
        Toast.makeText(getActivity(), "获取歌词失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Song song){
        playingSong=song;
       setLrcView();
    }

}
