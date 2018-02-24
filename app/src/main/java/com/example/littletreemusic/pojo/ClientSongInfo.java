package com.example.littletreemusic.pojo;

/**
 * Created by ZLX Vincent on 2017/8/31.
 */


public class ClientSongInfo {

    private int id,isFavourite;
    private long localId,duration,size;
    private String artist,title,uri,lyricUri;
    private String sortLetters;  //显示数据拼音的首字母
    public String getLyricUri() {
        return lyricUri;
    }

    public void setLyricUri(String lyricUri) {
        this.lyricUri = lyricUri;
    }

    public long getLocalId() {
        return localId;
    }
    public void setLocalId(long localId) {
        this.localId = localId;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

}