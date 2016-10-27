package com.example.hopjs.filmcinema.Data;

import android.graphics.Bitmap;

/**
 * Created by Hopjs on 2016/10/10.
 */

public class HomePageFilm {
    private String id;
    private Bitmap poster;
    private int posterResourceId;
    private String name;
    private String scord;
    private String date;

    public void setPosterResourceId(int posterResourceId) {
        this.posterResourceId = posterResourceId;
    }

    public int getPosterResourceId() {
        return posterResourceId;
    }

    /*public Bitmap getPoster() {
        return poster;
    }*/

    public String getName() {
        return name;
    }

    public String getScord() {
        return scord;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

/*    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }*/

    public void setDate(String date) {
        this.date = date;
    }

    public void setScord(String scord) {
        this.scord = scord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
