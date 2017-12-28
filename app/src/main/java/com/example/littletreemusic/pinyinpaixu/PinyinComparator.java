package com.example.littletreemusic.pinyinpaixu;

import com.example.littletreemusic.model.Song;

import java.util.Comparator;

/**
 * Created by 春风亭lx小树 on 2017/12/28.
 */

public class PinyinComparator implements Comparator<Song> {

    public int compare(Song s1, Song s2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (s2.getSortLetters().equals("#")) {
            return -1;
        } else if (s1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return s1.getSortLetters().compareTo(s2.getSortLetters());
        }
    }
}