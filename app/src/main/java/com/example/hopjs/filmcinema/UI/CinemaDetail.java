package com.example.hopjs.filmcinema.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hopjs.filmcinema.R;

/**
 * Created by Hopjs on 2016/10/19.
 */

public class CinemaDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cdetail);
    }
    public static class Session{
        private String filmId;
        private String Date;
        private String time;
        private String price;
        private String videoHallNum;
        private String sessionId;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
            return Date;
        }

        public String getFilmId() {
            return filmId;
        }

        public void setDate(String date) {
            Date = date;
        }

        public void setFilmId(String filmId) {
            this.filmId = filmId;
        }

        public String getPrice() {
            return price;
        }

        public String getVideoHallNum() {
            return videoHallNum;
        }

        public String getTime() {
            return time;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setVideoHallNum(String videoHallNum) {
            this.videoHallNum = videoHallNum;
        }
    }
}
