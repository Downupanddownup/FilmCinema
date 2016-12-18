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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.Fragment.CinemaListFragment;

/**
 * Created by Hopjs on 2016/10/19.
 */

public class CinemaChoose extends AppCompatActivity {
    private final int ALLCITY = 1;
    private final int NEARBY = 2;

    private ImageView ivCiyt,ivSearch;
    private ViewPager vpCinemas;
    private ImageView ivBar;
    private TextView tvCity;
    private TextView tvTabOne;
    private TextView tvTabTwo;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private String filmId;
    private boolean first = true;
    private int currentS = ALLCITY;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema0choose);

        vpCinemas = (ViewPager)findViewById(R.id.vp_cinema0choose_cinemas);
        ivBar = (ImageView)findViewById(R.id.iv_background);
        tvTabOne = (TextView)findViewById(R.id.tv_now0showing);
        tvTabTwo = (TextView)findViewById(R.id.tv_upcoming);
        ivCiyt = (ImageView)findViewById(R.id.iv_cinema_header_choose);
        ivSearch = (ImageView)findViewById(R.id.iv_cinema_header_search);
        tvCity = (TextView)findViewById(R.id.tv_cinema_header_city);

        tvTabOne.setText(" 全城 ");
        tvTabTwo.setText(" 附近 ");

        ivCiyt.setOnClickListener(listener);
        ivSearch.setOnClickListener(searchListener);

        tvTabOne.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        filmId = getIntent().getStringExtra("filmId");
        CinemaListFragment allCity = new CinemaListFragment();
        CinemaListFragment nearby = new CinemaListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("filmId",filmId);
        allCity.setArguments(bundle);
        bundle.putInt("type", CinemaListFragment.TYPE_NEARBY);
        nearby.setArguments(bundle);
        fragmentPagerAdapter = new CinemasPagerAdapter
                (getSupportFragmentManager(),allCity,nearby);

        vpCinemas.setAdapter(fragmentPagerAdapter);
        vpCinemas.setOnPageChangeListener(pageChangeListener);

        tvTabTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first){
                    ViewGroup.LayoutParams params = ivBar.getLayoutParams();
                    params.width= tvTabOne.getMeasuredWidth();
                    ivBar.setLayoutParams(params);

                    tvTabOne.setBackgroundColor(Color.TRANSPARENT);

                    setAnimation(ALLCITY);
                    first = false;
                    currentS = NEARBY;
                    vpCinemas.setCurrentItem(1);
                }
                else {
                    if(currentS == ALLCITY) {
                        setAnimation(ALLCITY);
                        currentS = NEARBY;
                        vpCinemas.setCurrentItem(1);
                    }
                }
            }
        });

        tvTabOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentS == NEARBY){
                    setAnimation(NEARBY);
                    currentS = ALLCITY;
                    vpCinemas.setCurrentItem(0);
                }
            }
        });
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Test.showToast(getApplicationContext(),"你点击了城市按钮");
        }
    };

    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Transform.toSearch(CinemaChoose.this);
        }
    };


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == 0){
                setAnimation(NEARBY);
                currentS = ALLCITY;
            }else {
                if(first){
                    ViewGroup.LayoutParams params = ivBar.getLayoutParams();
                    params.width= tvTabOne.getMeasuredWidth();
                    ivBar.setLayoutParams(params);

                    tvTabOne.setBackgroundColor(Color.TRANSPARENT);

                    first = false;
                }
                setAnimation(ALLCITY);
                currentS = NEARBY;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setAnimation(int currentPager){
        Animation animation;
        float to,from;
        if(currentPager == ALLCITY){
            from = 0;
            to =(float) (tvTabOne.getMeasuredWidth() + 0.3);
        }else {
            from =(float) (tvTabOne.getMeasuredWidth() + 0.3);
            to = 0;
        }
        //from 与to均为与view的x坐标的偏移量
        animation = new TranslateAnimation(from, to, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(150);
        ivBar.startAnimation(animation);
    }

    public class CinemasPagerAdapter extends FragmentPagerAdapter{
        private CinemaListFragment allCity,nearby;
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return allCity;
            }else {
                return nearby;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public CinemasPagerAdapter(FragmentManager fm, CinemaListFragment allCity, CinemaListFragment nearby) {
            super(fm);
            this.allCity = allCity;
            this.nearby = nearby;
        }
    }
}
