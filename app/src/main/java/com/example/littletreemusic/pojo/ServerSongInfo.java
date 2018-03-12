package com.example.littletreemusic.pojo;

import java.util.Date;

public class ServerSongInfo {

    private long id;

    private String title;

    private String artist;

    private int duration;

    private int size;

    private Date firstpushTime;

    private int firstpushUserid;

    private String firstpushDecs;

    private int pushCounts;

    private int pullCounts;

    private int commentCounts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getFirstpushTime() {
        return firstpushTime;
    }

    public void setFirstpushTime(Date firstpushTime) {
        this.firstpushTime = firstpushTime;
    }

    public int getFirstpushUserid() {
        return firstpushUserid;
    }

    public void setFirstpushUserid(int firstpushUserid) {
        this.firstpushUserid = firstpushUserid;
    }

    public String getFirstpushDecs() {
        return firstpushDecs;
    }

    public void setFirstpushDecs(String firstpushDecs) {
        this.firstpushDecs = firstpushDecs;
    }

    public int getPushCounts() {
        return pushCounts;
    }

    public void setPushCounts(int pushCounts) {
        this.pushCounts = pushCounts;
    }

    public int getPullCounts() {
        return pullCounts;
    }

    public void setPullCounts(int pullCounts) {
        this.pullCounts = pullCounts;
    }

    public int getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }
}
