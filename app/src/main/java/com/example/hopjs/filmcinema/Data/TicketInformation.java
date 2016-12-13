package com.example.hopjs.filmcinema.Data;

import com.example.hopjs.filmcinema.UI.SeatChoose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hopjs on 2016/12/5.
 */

public class TicketInformation {
    private String filmName;
    private String filmId;
    private String cinemaName;
    private String cinemaId;
    private String videoHallNum;
    private String time;
    private String date;
    private List<SeatChoose.seat> tickets;
    private String price;

    public TicketInformation() {
        tickets = new ArrayList<>();
    }

    public String getFilmId() {
        return filmId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public List<SeatChoose.seat> getTickets() {
        return tickets;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public String getVideoHallNum() {
        return videoHallNum;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTickets(List<SeatChoose.seat> tickets) {
        this.tickets = tickets;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setVideoHallNum(String videoHallNum) {
        this.videoHallNum = videoHallNum;
    }
    public void clearTickets(){
        int length = tickets.size();
        for(int i=0; i<length;++i){
            tickets.remove(0);
        }
    }
    public void addTickets(SeatChoose.seat seat){
        tickets.add(seat);
    }
    public int getTicketSum(){
        return tickets.size();
    }

}
