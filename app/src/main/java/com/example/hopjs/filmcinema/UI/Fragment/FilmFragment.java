package com.example.hopjs.filmcinema.UI.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Adapter.FilmListAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.TestCell;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class FilmFragment extends Fragment {

    public static final int NOWSHOWING = 1;
    public static final int UPCOMGING = 2;

    private ImageView ivReturn,ivSearch;
    private ViewPager vpFilmList;
    private ImageView ivBar;
    private TextView tvNowShowing;
    private TextView tvUpcoming;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private boolean first = true;
    private int currentS = NOWSHOWING;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.film,container,false);
        vpFilmList = (ViewPager)view.findViewById(R.id.vp_film_film);
        ivBar = (ImageView)view.findViewById(R.id.iv_background);
        tvNowShowing = (TextView)view.findViewById(R.id.tv_now0showing);
        tvUpcoming = (TextView)view.findViewById(R.id.tv_upcoming);
        ivReturn = (ImageView)view.findViewById(R.id.iv_film_header_return);
        ivSearch = (ImageView)view.findViewById(R.id.iv_film_header_search);

        ivReturn.setOnClickListener(returnListener);
        ivSearch.setOnClickListener(searchListener);

        tvNowShowing.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        FilmListFragment nowShowing = new FilmListFragment();
        FilmListFragment upcoming = new FilmListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", FilmListAdapter.TYPE_UPCOMING);
        upcoming.setArguments(bundle);

        fragmentPagerAdapter = new FilmListPagerAdapter
                (getFragmentManager(),nowShowing,upcoming);

        vpFilmList.setAdapter(fragmentPagerAdapter);
        vpFilmList.setOnPageChangeListener(pageChangeListener);

        tvUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first){
                    ViewGroup.LayoutParams params = ivBar.getLayoutParams();
                    params.width= tvNowShowing.getMeasuredWidth();
                    ivBar.setLayoutParams(params);

                    tvNowShowing.setBackgroundColor(Color.TRANSPARENT);

                    setAnimation(NOWSHOWING);
                    first = false;
                    currentS = UPCOMGING;
                    vpFilmList.setCurrentItem(1);
                }
                else {
                    if(currentS == NOWSHOWING) {
                        setAnimation(NOWSHOWING);
                        currentS = UPCOMGING;
                        vpFilmList.setCurrentItem(1);
                    }
                }
            }
        });

        tvNowShowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentS == UPCOMGING){
                    setAnimation(UPCOMGING);
                    currentS = NOWSHOWING;
                    vpFilmList.setCurrentItem(0);
                }
            }
        });

        return view;
    }

    private View.OnClickListener returnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Test.showToast(getActivity().getApplicationContext(),"你点击了返回按钮");
        }
    };

    private View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Transform.toSearch(getActivity());
        }
    };


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position == 0){
                setAnimation(UPCOMGING);
                currentS = NOWSHOWING;
            }else {
                if(first){
                    ViewGroup.LayoutParams params = ivBar.getLayoutParams();
                    params.width= tvNowShowing.getMeasuredWidth();
                    ivBar.setLayoutParams(params);

                    tvNowShowing.setBackgroundColor(Color.TRANSPARENT);

                    first = false;
                }
                setAnimation(NOWSHOWING);
                currentS = UPCOMGING;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setAnimation(int currentPager){
        Animation animation;
        float to,from;
        if(currentPager == NOWSHOWING){
            from = 0;
            to =(float) (tvNowShowing.getMeasuredWidth() + 0.3);
        }else {
            from =(float) (tvNowShowing.getMeasuredWidth() + 0.3);
            to = 0;
        }
        //from 与to均为与view的x坐标的偏移量
        animation = new TranslateAnimation(from, to, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(150);
        ivBar.startAnimation(animation);
    }

    public class FilmListPagerAdapter extends FragmentPagerAdapter{
        private FilmListFragment nowShowing,upcoming;
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return nowShowing;
            }else {
                return upcoming;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public FilmListPagerAdapter(FragmentManager fm, FilmListFragment nowShowing, FilmListFragment upcoming) {
            super(fm);
            this.nowShowing = nowShowing;
            this.upcoming = upcoming;
        }
    }
}
