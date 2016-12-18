package com.example.hopjs.filmcinema.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Adapter.FilmListAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.UI.Fragment.CinemaFragment;
import com.example.hopjs.filmcinema.UI.Fragment.CinemaListFragment;
import com.example.hopjs.filmcinema.UI.Fragment.FilmListFragment;

/**
 * Created by Hopjs on 2016/10/19.
 */

public class Search extends AppCompatActivity {
    private final int FILM = 1;
    private final int CINEMA = 2;

    private boolean firstClick = true;
    private boolean first = true;
    private int currentS = FILM;
    
    private ImageView ivReturn,ivChoose;
    private TextView tvTitle,tvCity;
    private ImageView ivBackground;
    private TextView tvFilm,tvCinema;
    private ImageView ivSearch;
    private EditText etContent;
    private ViewPager vpList;
    private FilmListFragment filmListFragment;
    private CinemaListFragment cinemaListFragment;
    private SearchPagerAdapter searchPagerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        ivReturn = (ImageView)findViewById(R.id.iv_search_return);
        ivChoose = (ImageView)findViewById(R.id.iv_search_choose);
        ivBackground = (ImageView)findViewById(R.id.iv_background);
        tvTitle = (TextView)findViewById(R.id.tv_search_title);
        tvCity = (TextView)findViewById(R.id.tv_search_city);
        tvFilm = (TextView)findViewById(R.id.tv_now0showing);
        tvCinema = (TextView)findViewById(R.id.tv_upcoming);
        ivSearch = (ImageView)findViewById(R.id.iv_search_s);
        etContent = (EditText)findViewById(R.id.et_search_content); 
        vpList = (ViewPager)findViewById(R.id.vp_search_list);
        
        tvTitle.setText("搜 索");
        tvCity.setText("杭州");
        tvFilm.setText(" 电影 ");
        tvCinema.setText(" 影院 ");
        
        
        ivReturn.setOnClickListener(listener);
        ivChoose.setOnClickListener(listener);
        etContent.setOnClickListener(listener);
        ivSearch.setOnClickListener(listener);

        filmListFragment = new FilmListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", FilmListAdapter.TYPE_SEARCH);
        filmListFragment.setArguments(bundle);
        cinemaListFragment = new CinemaListFragment();
        bundle.clear();
        bundle.putInt("type",CinemaListFragment.TYPE_SEARCH);
        cinemaListFragment.setArguments(bundle);

        searchPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager(),
                filmListFragment,cinemaListFragment);
        vpList.setAdapter(searchPagerAdapter);
        vpList.setOnPageChangeListener(pageChangeListener);

        tvFilm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tvCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first){
                    ViewGroup.LayoutParams params = ivBackground.getLayoutParams();
                    params.width= tvFilm.getMeasuredWidth();
                    ivBackground.setLayoutParams(params);

                    tvFilm.setBackgroundColor(Color.TRANSPARENT);

                    setAnimation(FILM);
                    first = false;
                    currentS = CINEMA;
                    vpList.setCurrentItem(1);
                }
                else {
                    if(currentS == FILM) {
                        setAnimation(FILM);
                        currentS = CINEMA;
                        vpList.setCurrentItem(1);
                    }
                }
            }
        });

        tvFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentS == CINEMA){
                    setAnimation(CINEMA);
                    currentS = FILM;
                    vpList.setCurrentItem(0);
                }
            }
        });
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvCity.setText(((MyApplication)getApplicationContext()).city.getName());
    }

    private void setAnimation(int currentPager){
        Animation animation;
        float to,from;
        if(currentPager == FILM){
            from = 0;
            to =(float) (tvFilm.getMeasuredWidth() + 0.3);
        }else {
            from =(float) (tvFilm.getMeasuredWidth() + 0.3);
            to = 0;
        }
        //from 与to均为与view的x坐标的偏移量
        animation = new TranslateAnimation(from, to, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(150);
        ivBackground.startAnimation(animation);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == 0){
                setAnimation(CINEMA);
                currentS = FILM;
            }else {
                if(first){
                    ViewGroup.LayoutParams params = ivBackground.getLayoutParams();
                    params.width= tvFilm.getMeasuredWidth();
                    ivBackground.setLayoutParams(params);

                    tvFilm.setBackgroundColor(Color.TRANSPARENT);

                    first = false;
                }
                setAnimation(FILM);
                currentS = CINEMA;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_search_return:
                    finish();
                    break;
                case R.id.iv_search_choose:
                    Transform.toCityChoose(Search.this);
                    break;
                case R.id.iv_search_s:
                    if(currentS == FILM){
                        filmListFragment.setFilmNameLike(etContent.getText().toString());
                    }else {
                        cinemaListFragment.setCinemaNameLike(etContent.getText().toString());
                    }
                    break;
                case R.id.et_search_content:
                    if(firstClick){
                        etContent.setText("");
                        firstClick = false;
                        etContent.setTextColor(getResources().getColor(R.color.colorDark));
                    }
                    break;
            }
        }
    };

    public class SearchPagerAdapter extends FragmentPagerAdapter {
        private CinemaListFragment cinema;
        private FilmListFragment film;
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return film;
            }else {
                return cinema;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public SearchPagerAdapter(FragmentManager fm, FilmListFragment film, CinemaListFragment cinema) {
            super(fm);
            this.film = film;
            this.cinema = cinema;
        }
    }
}
