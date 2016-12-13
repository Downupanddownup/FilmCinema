package com.example.hopjs.filmcinema.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.example.hopjs.filmcinema.Data.Cinema;
import com.example.hopjs.filmcinema.MainActivity;
import com.example.hopjs.filmcinema.UI.About;
import com.example.hopjs.filmcinema.UI.CinemaChoose;
import com.example.hopjs.filmcinema.UI.CinemaDetail;
import com.example.hopjs.filmcinema.UI.Collection;
import com.example.hopjs.filmcinema.UI.Confirm;
import com.example.hopjs.filmcinema.UI.Critic;
import com.example.hopjs.filmcinema.UI.FilmDetail;
import com.example.hopjs.filmcinema.UI.Help;
import com.example.hopjs.filmcinema.UI.MyCritic;
import com.example.hopjs.filmcinema.UI.Question;
import com.example.hopjs.filmcinema.UI.Register;
import com.example.hopjs.filmcinema.UI.Search;
import com.example.hopjs.filmcinema.UI.SeatChoose;
import com.example.hopjs.filmcinema.UI.TicketRecord;

/**
 * Created by Hopjs on 2016/10/19.
 */

public class Transform {
    public static void toFilmDetail(Activity activity,String filmId){
        Intent intent = new Intent(activity, FilmDetail.class);
        intent.putExtra("filmId",filmId);
        activity.startActivity(intent);
    }
    public static void toCinemaDetail(Activity activity,String cinemaId){
        Intent intent = new Intent(activity, CinemaDetail.class);
        intent.putExtra("id",cinemaId);
        activity.startActivity(intent);
    }
    public static void toSeatChoose(Activity activity,String cinemaId,String filmId,String sessionId){
        Intent intent = new Intent(activity, SeatChoose.class);
        intent.putExtra("cinemaId",cinemaId);
        intent.putExtra("filmId",filmId);
        intent.putExtra("sessionId",sessionId);
        activity.startActivity(intent);
    }
    public static void toRegister(Activity activity){
        Intent intent = new Intent(activity, Register.class);
        activity.startActivity(intent);
    }
    public static void toCollection(Activity activity){
        Intent intent = new Intent(activity, Collection.class);
        activity.startActivity(intent);
    }
    public static void toMyCritic(Activity activity){
        Intent intent = new Intent(activity, MyCritic.class);
        activity.startActivity(intent);
    }
    public static void toTicketRecord(Activity activity){
        Intent intent = new Intent(activity, TicketRecord.class);
        activity.startActivity(intent);
    }
    public static void toAbout(Activity activity){
        Intent intent = new Intent(activity, About.class);
        activity.startActivity(intent);
    }
    public static void toHelp(Activity activity){
        Intent intent = new Intent(activity, Help.class);
        activity.startActivity(intent);
    }
    public static void toQuestion(Activity activity){
        Intent intent = new Intent(activity, Question.class);
        activity.startActivity(intent);
    }
    public static void toSearch(Activity activity){
        Intent intent = new Intent(activity, Search.class);
        activity.startActivity(intent);
    }
    public static void toPcenter(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("location",MainActivity.PCENTER);
        activity.startActivity(intent);
    }
    public static void toHomePage(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("location",MainActivity.HOMEPAGE);
        activity.startActivity(intent);
    }
    public static void toFilm(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("location",MainActivity.FILM);
        activity.startActivity(intent);
    }
    public static void toCinema(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("location",MainActivity.CINEMA);
        activity.startActivity(intent);
    }
    public static void toCinemaChoose(Activity activity,String filmId){
        Intent intent = new Intent(activity, CinemaChoose.class);
        intent.putExtra("filmId",filmId);
        activity.startActivity(intent);
    }
    public static void toCritic(Activity activity,String filmId){
        Intent intent = new Intent(activity, Critic.class);
        intent.putExtra("filmId",filmId);
        activity.startActivity(intent);
    }
    public static void toConfirm(Activity activity){
        Intent intent = new Intent(activity, Confirm.class);
        activity.startActivity(intent);
    }
}
