package com.example.littletreemusic.pojo;

/**
 * Created by 春风亭lx小树 on 2018/1/2.
 */

public class Lyric {
    private String lrc;
    private long start;
    private long end;

    public Lyric() {
    }

    public Lyric(String text, long start, long end) {
        this.lrc = text;
        this.start = start;
        this.end = end;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
