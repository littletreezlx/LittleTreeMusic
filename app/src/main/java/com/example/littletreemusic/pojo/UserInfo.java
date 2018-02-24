package com.example.littletreemusic.pojo;

import java.util.List;

/**
 * Created by 春风亭lx小树 on 2018/2/2.
 */

public class UserInfo {

    private String nickName,gender,age,city,myWords,headShotsUri;
    private List<String> PreferdTags;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMyWords() {
        return myWords;
    }

    public void setMyWords(String myWords) {
        this.myWords = myWords;
    }

    public String getHeadShotsUri() {
        return headShotsUri;
    }

    public void setHeadShotsUri(String headShotsUri) {
        this.headShotsUri = headShotsUri;
    }

    public List<String> getPreferdTags() {
        return PreferdTags;
    }

    public void setPreferdTags(List<String> preferdTags) {
        PreferdTags = preferdTags;
    }
}
