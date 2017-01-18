package com.example.hopjs.filmcinema.Data;

import android.graphics.Bitmap;

/**
 * Created by Hopjs on 2016/10/13.
 */

public class UserAccount {
    private String portraitName;
    private String name;
    private String userId;
    private String sex;
    private String bphone;
    private String pwd;
    private boolean login;
    private boolean setportrait;

    public UserAccount() {
        setportrait = false;
        login = false;
        portraitName="";
        name="";
        userId="";
        sex="";
        bphone="";
        pwd="";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortraitName() {
        return portraitName;
    }

    public void setPortraitName(String portraitName) {
        this.portraitName = portraitName;
    }

    public boolean isSetportrait() {
        return setportrait;
    }

    public void setSetportrait(boolean setportrait) {
        this.setportrait = setportrait;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getBphone() {
        return bphone;
    }

    public String getSex() {
        return sex;
    }

    public void setBphone(String bphone) {
        this.bphone = bphone;
    }

    public String getPwd() {
        return pwd;
    }


    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
