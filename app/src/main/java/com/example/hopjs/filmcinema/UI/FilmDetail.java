package com.example.hopjs.filmcinema.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hopjs.filmcinema.Adapter.CriticAdapter;
import com.example.hopjs.filmcinema.Common.ShowTool;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.Fragment.CriticsFragment;
import com.example.hopjs.filmcinema.UI.Fragment.SidebarFragment;
import com.example.hopjs.filmcinema.UI.Fragment.WorkersFragment;

/**
 * Created by Hopjs on 2016/10/19.
 */

public class FilmDetail extends AppCompatActivity {
    private static final int FINFOR=1;
    public static final int LOGIN=2;
    public static final int REQUEST_CRITIC=3;
    private ImageView ivReturn,ivSearch,ivBackground,ivPoster;
    private RelativeLayout relativeLayout;
    private TextView tvTitle,tvName,tvScord,tvType,tvTime,tvDate;
    private ImageButton ibtCritic,ibtLike,ibtBuy;
    private RelativeLayout rlBody;
    private FinforFilm finforFilm;
    public Handler handler;
    public String filmId;
    private WorkersFragment workersFragment;
    private CriticsFragment criticsFragment;
    private boolean isCollected;
    private SidebarFragment sidebarFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film0detail);

        tvName = (TextView)findViewById(R.id.tv_film0detail_finfor_name);
        tvScord = (TextView)findViewById(R.id.tv_film0detail_finfor_scord);
        tvType = (TextView)findViewById(R.id.tv_film0detail_finfor_type);
        tvTime = (TextView)findViewById(R.id.tv_film0detail_finfor_time);
        tvDate = (TextView)findViewById(R.id.tv_film0detail_finfor_date);
        ivBackground = (ImageView)findViewById(R.id.iv_film0detail_finfor_background);
        ivPoster = (ImageView)findViewById(R.id.iv_film0detail_finfor_poster);
        relativeLayout = (RelativeLayout)findViewById(R.id.rl_film0detail_rl);
        tvTitle = (TextView)findViewById(R.id.tv_header_title);
        ivReturn = (ImageView)findViewById(R.id.iv_header_return);
        ivSearch = (ImageView)findViewById(R.id.iv_header_search);
        ibtBuy = (ImageButton)findViewById(R.id.ibt_film0detail_buy);
        ibtCritic = (ImageButton)findViewById(R.id.ibt_film0detail_critic);
        ibtLike = (ImageButton)findViewById(R.id.ibt_film0detail_collect);
        rlBody =(RelativeLayout)findViewById(R.id.rl_header_body);

        sidebarFragment =(SidebarFragment)getSupportFragmentManager().
                findFragmentById(R.id.f_film0detail_sidebar);
        workersFragment = (WorkersFragment) getSupportFragmentManager()
                .findFragmentById(R.id.f_film0detail_workers);
        criticsFragment = (CriticsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.f_film0detail_critics);
        ivReturn.setOnClickListener(clickListener);
        ivSearch.setOnClickListener(clickListener);
        ibtLike.setOnClickListener(clickListener);
        ibtBuy.setOnClickListener(clickListener);
        ibtCritic.setOnClickListener(clickListener);

        isCollected=false;
        rlBody.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        rlBody.getBackground().setAlpha(0);
        relativeLayout.setFocusable(true);
        relativeLayout.setFocusableInTouchMode(true);
        relativeLayout.requestFocus();

        filmId = getIntent().getStringExtra("filmId");



/*        workersFragment.setFilmId(filmId);
        criticsFragment.setFilmId(filmId);*/



        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case FINFOR:
                        setFinforFilm();
                        break;
                    case LOGIN:
                        sidebarFragment.loadUserInfor();
                        break;
                }
            }
        };
        loadFinforFilm();
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_header_return:
                    finish();
                    break;
                case R.id.iv_header_search:
                    Transform.toSearch(FilmDetail.this);
                    break;
                case R.id.ibt_film0detail_buy:
                    Transform.toCinemaChoose(FilmDetail.this,filmId);
                    break;
                case R.id.ibt_film0detail_critic:
                    UserAccount userAccount = ((MyApplication)getApplicationContext()).userAccount;
                    if(userAccount.isLogin()) {
                        Transform.toCritic(FilmDetail.this, filmId,REQUEST_CRITIC);
                    }else {
                        Login login = new Login(FilmDetail.this,handler,LOGIN);
                        login.show();
                    }
                    break;
                case R.id.ibt_film0detail_collect:
                    userAccount = ((MyApplication)getApplicationContext()).userAccount;
                    if(userAccount.isLogin()) {
                        if (isCollected) {
                            isCollected = false;
                            ibtLike.setImageResource(R.drawable.uncollect);
                        } else {
                            isCollected = true;
                            ibtLike.setImageResource(R.drawable.collected);
                        }
                    }else {
                        Login login = new Login(FilmDetail.this,handler,LOGIN);
                        login.show();
                    }
                    break;
            }
        }
    };

    public void loadFinforFilm(){
        new Thread(){
            @Override
            public void run() {
                finforFilm = Connect.getFinfor_FilmDetail(filmId);
                // finforFilm = Test.getfilmdetail();
                Message message = new Message();
                message.arg1=FINFOR;
                handler.sendMessage(message);
            }
        }.start();
    }

    public void setFinforFilm(){
        String tem=ShowTool.getUpcomingDate(finforFilm.getDate())+"上映";
        tvDate.setText(tem);
        tvTime.setText(finforFilm.getTime()+"分钟");
        tvName.setText(finforFilm.getName());
        tvType.setText(finforFilm.getType());
        tvScord.setText(finforFilm.getScord());
       // tvName.setTextColor(getResources().getColor(R.color.c));
        tvTitle.setText("电 影 详 情");
        /*Glide.with(this)
                .load(R.drawable.film0background)
                .into(ivBackground);*/
        Connect.TemUrl temUrl = new Connect.TemUrl();
        temUrl.setConnectionType(Connect.NETWORK_FILM_PICTURE);
        temUrl.addHeader("posterName","Posters/"+finforFilm.getPosterName());
        Glide.with(this)
                .load(temUrl.getSurl())
                .into(ivPoster);
    }
    public static class FinforFilm{
        private String filmId;
        private String posterName;
        private String name;
        private String type;
        private String scord;
        private String time;
        private String date;

        public String getPosterName() {
            return posterName;
        }

        public void setPosterName(String posterName) {
            this.posterName = posterName;
        }

        public String getFilmId() {
            return filmId;
        }

        public String getName() {
            return name;
        }

        public void setFilmId(String filmId) {
            this.filmId = filmId;
        }

        public String getType() {
            return type;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScord() {
            return scord;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setScord(String scord) {
            this.scord = scord;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CRITIC){
            if(resultCode==RESULT_OK){
                CriticAdapter.Critic critic = new CriticAdapter.Critic();
                critic.setId(data.getStringExtra("criticId"));
                critic.setName(data.getStringExtra("name"));
                critic.setPortraitName(data.getStringExtra("portraitName"));
                critic.setContent(data.getStringExtra("content"));
                critic.setFilmId(data.getStringExtra("filmId"));
                critic.setScord(data.getFloatExtra("scord",5));
                critic.setDate(data.getStringExtra("date"));
                critic.setPraise(data.getStringExtra("praiseNum"));
                criticsFragment.criticAdapter.add(critic);
            }
        }

    }
}
