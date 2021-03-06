package com.example.hopjs.filmcinema.UI;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopjs.filmcinema.Adapter.CinemaFilmAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.TicketInformation;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.TestCell;
import com.example.hopjs.filmcinema.UI.Fragment.SessionFragment;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/19.
 */

public class CinemaDetail extends AppCompatActivity {
    /*
   * 场次次数不同导致跳转时页面跳动，非常影响体验*/
    private ImageView ivReturn,ivSearch;
    private TextView tvTitle;
    public TextView tvCinemaName,tvPhone,tvAddress;
    private RecyclerView rvFilms;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    public TextView tvFilmName;
    private RatingBar rbScord;
    private RelativeLayout relativeLayout;
    private SessionFragment sfSessions;
    private String cinemaId;
    private CinemaDetail.Cinema cinema;
    private ArrayList<Film> films;
    private CinemaFilmAdapter cinemaFilmAdapter;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cdetail);

        ivReturn = (ImageView)findViewById(R.id.iv_header_return);
        ivSearch = (ImageView)findViewById(R.id.iv_header_search);
        tvTitle = (TextView) findViewById(R.id.tv_header_title);
        tvCinemaName = (TextView) findViewById(R.id.tv_cdetail_finfor_name);
        tvPhone = (TextView) findViewById(R.id.tv_cdetail_finfor_phone);
        tvAddress = (TextView) findViewById(R.id.tv_cdetail_finfor_address);
        tvFilmName = (TextView) findViewById(R.id.tv_cdetail_film_name);
        rvFilms = (RecyclerView) findViewById(R.id.rv_cdetail_film_f);
        tvFilmName = (TextView) findViewById(R.id.tv_cdetail_film_name);
        rbScord = (RatingBar)findViewById(R.id.rb_cdetail_film_scord);
        relativeLayout=(RelativeLayout)findViewById(R.id.rl_header_body);
        sfSessions = (SessionFragment)getSupportFragmentManager().
                findFragmentById(R.id.f_cdetail_sessions);

        LayerDrawable ldStars = (LayerDrawable)rbScord.getProgressDrawable();
      //  ldStars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);


        tvTitle.setText("影 院 详 情");
        cinemaId = getIntent().getStringExtra("id");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
               // Toast.makeText(CinemaDetail.this,"3",Toast.LENGTH_SHORT).show();
                setData();
            }
        };



        ivSearch.setOnClickListener(listener);
        ivReturn.setOnClickListener(listener);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        relativeLayout.getBackground().setAlpha(180);
        loadData();

    }
    private void loadData(){
        new Thread(){
            @Override
            public void run() {
                 // films = Test.getFilms(cinemaId);
                films = Connect.getFilm_CinemaDetail(cinemaId);
                //cinema = Test.getCinema(cinemaId);
                cinema = Connect.getFinfor_CinemaDetail(cinemaId);
                 handler.sendMessage(new Message());
            }
        }.start();
    }
    private void setData(){

        tvCinemaName.setText(cinema.getName());
        tvAddress.setText(cinema.getAddress());
        tvPhone.setText(cinema.getPhone());

        TicketInformation ticketInformation=((MyApplication)getApplicationContext()).ticketInformation;
        ticketInformation.setCinemaId(cinemaId);
        ticketInformation.setCinemaName(cinema.getName());

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        rvFilms.setLayoutManager(staggeredGridLayoutManager);
        cinemaFilmAdapter = new CinemaFilmAdapter(this,films,itemClickListener);
        rvFilms.setAdapter(cinemaFilmAdapter);

        if(films.size()>0) {
            ticketInformation.setFilmName(films.get(0).getName());
            tvFilmName.setText(films.get(0).getName());
            rbScord.setRating(films.get(0).getScord()/2f);
            sfSessions.setCinemaId(cinemaId);
            sfSessions.changeFilm(films.get(0).getId());

        }

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_header_return:
                    finish();
                    break;
                case R.id.iv_header_search:
                    Transform.toSearch(CinemaDetail.this);
                    break;
            }
        }
    };
    private CinemaFilmAdapter.OnItemClickListener itemClickListener = new CinemaFilmAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String id,int index) {
            CinemaDetail.Film film = getFilm(id);
            if(film != null){
                TicketInformation ticketInformation=((MyApplication)getApplicationContext()).ticketInformation;
                ticketInformation.setFilmName(film.getName());
                ticketInformation.setFilmId(film.getId());
                tvFilmName.setText(film.getName());
                rbScord.setRating(film.getScord()/2f);
                sfSessions.changeFilm(id);
            }

        }
    };
    @Nullable
    private CinemaDetail.Film getFilm(String id){
        for(int i=0; i<films.size(); ++i){
            if(id.equals(films.get(i).getId())){
                return films.get(i);
            }
        }
        return null;
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
    public static class Film{
        private String id;
        private String name;
        private float Scord;
        private String posterName;

        public String getPosterName() {
            return posterName;
        }

        public float getScord() {
            return Scord;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPosterName(String posterName) {
            this.posterName = posterName;
        }

        public void setScord(float scord) {
            Scord = scord;
        }
    }
    public static class Cinema{
        private String id;
        private String name;
        private String phone;
        private String address;

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getId() {
            return id;
        }

        public String getPhone() {
            return phone;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
