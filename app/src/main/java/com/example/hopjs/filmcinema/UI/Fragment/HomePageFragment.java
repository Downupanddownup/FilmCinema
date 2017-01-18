package com.example.hopjs.filmcinema.UI.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.hopjs.filmcinema.Adapter.HomePageFilmAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.HomePageFilm;
import com.example.hopjs.filmcinema.MainActivity;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hopjs on 2016/10/10.
 */

public class HomePageFragment extends Fragment {
    public static final int MESSAGE_NOWSHOWING = 1;
    public static final int MESSAGE_UPCOMGING = 2;
    public static final int MESSAGE_BIGPOSTERS = 3;

    private View noTouch;
    private RecyclerView nowShowing,upcoming;
    private ImageView ivSearch,ivNMore,ivUMore;
    private EditText etContent;
    private ArrayList<HomePageFilm> nowShowingDatas;
    private ArrayList<HomePageFilm> upcomingDatas;
    private Handler handler;
    private SwipeRefreshLayout srlRefresh;
    private PosterFragment pfBigPosters;
    private ViewPager viewPager;
    private int loadFinishedNum = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home0page,container,false);
        inite(view);
        srlRefresh.setRefreshing(true);
        pfBigPosters.loadBigPosters(handler);
        loadNowShowingData();
        loadUpcomingData();
        return view;
    }

    private void inite(View view){
        viewPager = (ViewPager)getActivity().findViewById(R.id.vp_view);
        noTouch = view.findViewById(R.id.v_home0page_no0touch);
        pfBigPosters = (PosterFragment)getChildFragmentManager().findFragmentById(R.id.f_home0page_big0poster);
        srlRefresh = (SwipeRefreshLayout)view.findViewById(R.id.srl_home0page_refresh);
        ivSearch = (ImageView)view.findViewById(R.id.iv_home0page_search_switch);
        ivNMore = (ImageView)view.findViewById(R.id.iv_home0page_now0showing_more);
        ivUMore = (ImageView)view.findViewById(R.id.iv_home0page_upcoming_more);
        etContent = (EditText)view.findViewById(R.id.et_home0page_search_content);

        upcoming = (RecyclerView)view.findViewById(R.id.rv_home0page_upcoming_list);
        nowShowing = (RecyclerView)view.findViewById(R.id.rv_home0page_now0showing_list);

        ivSearch.setOnClickListener(switchTo);
        etContent.setOnClickListener(switchTo);
        ivNMore.setOnClickListener(switchTo);
        ivUMore.setOnClickListener(switchTo);

        srlRefresh.setOnRefreshListener(refreshListener);

        noTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(srlRefresh.isRefreshing()){
                    return true;
                }else {
                    return false;
                }
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case MESSAGE_NOWSHOWING:
                        setNowShowingData();
                        Log.e("HOMEPAGE","nowShowing");
                        setRefresh();
                        break;
                    case MESSAGE_UPCOMGING:
                        setUpcomingData();
                        Log.e("HOMEPAGE","upcoming");
                        setRefresh();
                        break;
                    case MESSAGE_BIGPOSTERS:
                        Log.e("HOMEPAGE","bigPosters");
                        setRefresh();
                        break;
                }
            }
        };
    }

    private void setRefresh(){
        loadFinishedNum++;
        if(loadFinishedNum >2){
            srlRefresh.setRefreshing(false);
            loadFinishedNum = 0;
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            pfBigPosters.loadBigPosters(handler);
            loadUpcomingData();
            loadNowShowingData();
        }
    };

    private HomePageFilmAdapter.myItemClickListener nowShowingListener = new HomePageFilmAdapter.myItemClickListener() {
        @Override
        public void onClick(View v, String id) {
            Transform.toFilmDetail(getActivity(),id);
        }
    };

    private HomePageFilmAdapter.myItemClickListener upcomingListener = new HomePageFilmAdapter.myItemClickListener() {
        @Override
        public void onClick(View v, String id) {
            Transform.toFilmDetail(getActivity(),id);
        }
    };
    private View.OnClickListener switchTo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_home0page_search_switch:
                    Transform.toSearch(getActivity());
                    break;
                case R.id.iv_home0page_now0showing_more:
                    viewPager.setCurrentItem(MainActivity.FILM);
                    break;
                case R.id.iv_home0page_upcoming_more:
                    viewPager.setCurrentItem(MainActivity.FILM);
                    break;
                case R.id.et_home0page_search_content:
                    Transform.toSearch(getActivity());
                    break;
            }
        }
    };

    private ArrayList<Integer> getPosterResourceIds(int count){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0;i<count;++i){
            list.add(Test.getPicture(i));
        }
        return list;
    }

    private ArrayList<Bitmap> getBitmaps(){
        ArrayList<Bitmap> posters = new ArrayList<>();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        Context context = getActivity();
        ImageView iv = new ImageView(context);
        iv.setImageResource(R.drawable.cinema);
        ImageView iv2 = new ImageView(context);
        iv2.setImageResource(R.drawable.film);
        ImageView iv3 = new ImageView(context);
        iv3.setImageResource(R.drawable.person_center);
        ImageView iv4 = new ImageView(context);
        iv4.setImageResource(R.drawable.home_page);
        imageViews.add(iv);
        imageViews.add(iv2);
        imageViews.add(iv3);
        imageViews.add(iv4);
        for(ImageView imageView:imageViews){
            Bitmap bitmap = ((BitmapDrawable) (imageView)
                    .getDrawable()).getBitmap();
            posters.add(bitmap);
        }
        return posters;
    }
    private void loadNowShowingData(){
        new Thread(){
            @Override
            public void run() {
                // nowShowingDatas = getDatas();
                //nowShowingDatas = Test.getNowShowing();
                nowShowingDatas = Connect.getNowShowingData_HomepageFilm();
                Message msg = new Message();
                msg.arg1 = MESSAGE_NOWSHOWING;
                handler.sendMessage(msg);
            }
        }.start();
    }
    private void loadUpcomingData(){
        new Thread(){
            @Override
            public void run() {
                // upcomingDatas = getDatas();
                //upcomingDatas=Test.getUpcoming();
                upcomingDatas = Connect.getUpcomingData_HomepageFilm();
                Message msg = new Message();
                msg.arg1 = MESSAGE_UPCOMGING;
                handler.sendMessage(msg);
            }
        }.start();
    }
    private ArrayList<HomePageFilm> getDatas(){
        ArrayList<HomePageFilm> homePageFilms = new ArrayList<>();
        for(Integer integer:getPosterResourceIds(5)){
            HomePageFilm tem = new HomePageFilm();
            tem.setId("1");
            /*tem.setPoster(bitmap);*/
          //  tem.setPosterResourceId(integer);
            tem.setName("电影22");
            tem.setScord("评分11");
            tem.setDate("10月10日");
            homePageFilms.add(tem);
        }
        return homePageFilms;
    }
    private void setNowShowingData(){
        HomePageFilmAdapter homePageFilmAdapter = new HomePageFilmAdapter(getActivity(),
                nowShowingDatas, HomePageFilmAdapter.NOWSHOWING,nowShowingListener);
        nowShowing.setLayoutManager(new StaggeredGridLayoutManager
                (1,StaggeredGridLayoutManager.HORIZONTAL));
        nowShowing.setAdapter(homePageFilmAdapter);
    }
    private void setUpcomingData(){
        HomePageFilmAdapter homePageFilmAdapter2 = new HomePageFilmAdapter
                (getActivity(),upcomingDatas, HomePageFilmAdapter.UPCOMING,upcomingListener);
        upcoming.setLayoutManager(new StaggeredGridLayoutManager
                (1,StaggeredGridLayoutManager.HORIZONTAL));
        upcoming.setAdapter(homePageFilmAdapter2);
    }
}
