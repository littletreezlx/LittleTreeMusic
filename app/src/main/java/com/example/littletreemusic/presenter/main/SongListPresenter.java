package com.example.littletreemusic.presenter.main;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.littletreemusic.pojo.Song;
import com.example.littletreemusic.util.CharacterParser;
import com.example.littletreemusic.util.PinyinComparator;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by 春风亭lx小树 on 2018/2/20.
 */

public class SongListPresenter implements SongListContract.ISongListPresenter {


    @Inject
    SharedPreferences sp;
    @Inject
    CharacterParser characterParser;
    @Inject
    PinyinComparator pinyinComparator;
    @Inject
    SongListContract.ISongListView mISongListView;

    private List<Song> songList=new ArrayList<>();
    private Song playingSong;

//    @Override
//    public void init(Context context) {
//        MainActivity mainActivity=(MainActivity)context;
//        DaggerMainSongListComponent.builder()
//                .mainActivityComponent(mainActivity.getMainActivityComponent())
//                .build()
//                .inject(this);
//    }

//    @Override
//    public void bindView(SongListContract.ISongListIview view) {
//        mISongListIview=view;
//    }

    @Override
    public List<Song> findSongListByTagPosition(int position){

        songList.clear();
        switch (position) {
            case -2:
                songList = DataSupport.findAll(Song.class);
                break;
            case -1:
                songList = DataSupport.where("isFavourite = ?", "1").find(Song.class);
                break;
            default:
                List<String> tagList = new ArrayList<>();
                Set tagSet = sp.getStringSet("TagSet", null);
                tagList.addAll(tagSet);
                String tagName = tagList.get(position);

                List<Song> allSongList = DataSupport.findAll(Song.class);

                for (Song song : allSongList) {
                    if (song.getTagList().contains(tagName)) {
                        songList.add(song);
                    }
                }
                break;
        }
//         根据a-z进行排序源数据
        for (int i = 0; i < songList.size(); i++) {
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(songList.get(i).getTitle());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                songList.get(i).setSortLetter(sortString.toUpperCase());
            } else {
                songList.get(i).setSortLetter("#");
            }
        }
        Collections.sort(songList, pinyinComparator);
        return songList;
    }

    @Override
    public List<Song> filterData(String filterStr) {
        List<Song> filterDateList = new ArrayList<Song>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = songList ;
        } else {
            filterDateList.clear();
            for (Song song : songList) {
                String name = song.getArtist();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(song);
                }
            }
        }
//         根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        return filterDateList;
    }

//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(Integer playingPosition) {
//        iSongListIview.showPlayingSong(playingPosition);
//    }

}

