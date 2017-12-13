package com.example.littletreemusic.table;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZLX Vincent on 2017/9/26.
 */

public class Label  extends DataSupport {
    private String lablename;
    private List<Song> songList=new ArrayList<>();

    public String getLablename() {
        return lablename;
    }

    public void setLablename(String lablename) {
        this.lablename = lablename;
    }


    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

}
