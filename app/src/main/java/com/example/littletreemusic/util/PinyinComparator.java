package com.example.littletreemusic.util;

import com.example.littletreemusic.pojo.Song;

import java.util.Comparator;

import javax.inject.Inject;

/**
 * Created by 春风亭lx小树 on 2017/12/28.
 */

public class PinyinComparator implements Comparator<Song> {


    @Inject
    public PinyinComparator(){

    }

    public int compare(Song s1, Song s2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (s2.getSortLetter().equals("#")) {
            return -1;
        } else if (s1.getSortLetter().equals("#")) {
            return 1;
        } else {
            return s1.getSortLetter().compareTo(s2.getSortLetter());
        }
    }
}