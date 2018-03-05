package com.example.littletreemusic.pojo;

/**
 * Created by 春风亭lx小树 on 2018/2/2.
 */

public class RegisterMsg {


    private String username,password,nickname,age,gender,favouriteStyle;

    public String getFavouriteStyle() {
        return favouriteStyle;
    }

    public void setFavouriteStyle(String favouriteStyle) {
        this.favouriteStyle = favouriteStyle;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getnickname() {
        return nickname;
    }

    public void setnickname(String nickname) {
        this.nickname = nickname;
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
