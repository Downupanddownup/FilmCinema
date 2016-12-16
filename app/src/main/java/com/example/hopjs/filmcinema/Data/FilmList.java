package com.example.hopjs.filmcinema.Data;

import android.graphics.Bitmap;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class FilmList {
    private String id;
    private String posterName;
    private String name;
    private String scord;

    private String type;
    private String date;

    private String cinemaNum;
    private String showingTimes;

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public Bitmap getPoster() {
        return poster;
    }*/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScord() {
        return scord;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public void setPoster(Bitmap poster) {
        this.poster = poster;
    }*/

    public String getCinemaNum() {
        return cinemaNum;
    }

    public String getType() {
        return type;
    }

    public void setScord(String scord) {
        this.scord = scord;
    }

    public String getDate() {
        return date;
    }

    public String getShowingTimes() {
        return showingTimes;
    }

    public void setCinemaNum(String cinemaNum) {
        this.cinemaNum = cinemaNum;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setShowingTimes(String showingTimes) {
        this.showingTimes = showingTimes;
    }
}
