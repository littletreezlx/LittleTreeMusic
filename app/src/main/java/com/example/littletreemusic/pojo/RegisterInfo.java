package com.example.littletreemusic.pojo;

import java.util.List;

/**
 * Created by 春风亭lx小树 on 2018/2/2.
 */

public class RegisterInfo {

    private String account,password,nickName,age,gender,favouriteStyle;
    private List<String> PreferdTags;

    public String getFavouriteStyle() {
        return favouriteStyle;
    }

    public void setFavouriteStyle(String favouriteStyle) {
        this.favouriteStyle = favouriteStyle;
    }

    public List<String> getPreferdTags() {
        return PreferdTags;
    }

    public void setPreferdTags(List<String> preferdTags) {
        PreferdTags = preferdTags;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
}
