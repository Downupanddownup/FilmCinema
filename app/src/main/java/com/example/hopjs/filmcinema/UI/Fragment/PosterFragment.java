package com.example.hopjs.filmcinema.UI.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hopjs.filmcinema.Adapter.BigPosterAdatper;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.FilmList;
import com.example.hopjs.filmcinema.Data.HomePageFilm;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hopjs on 2016/10/9.
 */

public class PosterFragment extends Fragment {

    private final int MESSAGE_SWITCH = 1;
    private final int MESSAGE_LOADFINISHED = 2;

    public ViewPager bigPoster;
    public ImageView bar1,bar2,bar3,bar4;
    private ArrayList<FilmList> posters;
    private Handler handler;
    private BigPosterAdatper bigPosterAdatper;
    private SwitchRunnable thread;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thread = new SwitchRunnable();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == MESSAGE_SWITCH) {
                    int currentItem = bigPoster.getCurrentItem();
                    if (currentItem < 3) {
                        bigPoster.setCurrentItem(currentItem + 1);
                    } else {
                        bigPoster.setCurrentItem(0);
                    }
                }
                if(msg.arg1 == MESSAGE_LOADFINISHED){
                    setBigPoster();
                    Message message = new Message();
                    message.arg1 = HomePageFragment.MESSAGE_BIGPOSTERS;
                    ((Handler)msg.obj).sendMessage(message);

                    if(thread.getState() == SwitchRunnable.STATE_NONE){
                        thread.start();
                    }else {
                        thread.resume();
                    }

                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home0page_big0poster,container,false);
        init(view);
      //  loadBigPosters();
        return view;
    }

    private void init(View view){
        bigPoster = (ViewPager)view.findViewById(R.id.vp_home0page_big0poster_poster);
        bar1 = (ImageView)view.findViewById(R.id.iv_home0page_big0poster_rectangle1);
        bar2 = (ImageView)view.findViewById(R.id.iv_home0page_big0poster_rectangle2);
        bar3 = (ImageView)view.findViewById(R.id.iv_home0page_big0poster_rectangle3);
        bar4 = (ImageView)view.findViewById(R.id.iv_home0page_big0poster_rectangle4);
        bigPoster.addOnPageChangeListener(switchBar);
        bigPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filmId = posters.get(bigPoster.getCurrentItem()).getId();
                Transform.toFilmDetail(getActivity(),filmId);
            }
        });
    }


    private ArrayList<FilmList> getDatas(){
        ArrayList<FilmList> filmLists = new ArrayList<>();
        for(int i=0; i<4; ++i){
            FilmList filmList = new FilmList();
          //  filmList.setPosterResourceId(Test.getPicture(i));
            filmList.setId(i+"");
            filmLists.add(filmList);
        }
        return filmLists;
    }

    private ViewPager.OnPageChangeListener switchBar = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(state == ViewPager.SCROLL_STATE_SETTLING){
                setRectagnleColor(bigPoster.getCurrentItem());
            }
        }
    };

    private void setRectagnleColor(int postion){
        bar1.setImageResource(R.color.colorAccent);
        bar2.setImageResource(R.color.colorAccent);
        bar3.setImageResource(R.color.colorAccent);
        bar4.setImageResource(R.color.colorAccent);
        switch (postion){
            case 0: bar1.setImageResource(R.color.colorPrimary);
                break;
            case 1: bar2.setImageResource(R.color.colorPrimary);
                break;
            case 2: bar3.setImageResource(R.color.colorPrimary);
                break;
            case 3: bar4.setImageResource(R.color.colorPrimary);
                break;
        }
    }

    private void setBigPoster(){
        ArrayList<BigPosterAdatper.PagerHolder> films = new ArrayList<>();
        ArrayList<Integer> res = Test.getBigPosters();
        int i=0;
        for(FilmList film:posters){
            ImageView imageView = new ImageView(getActivity());
            /*Bitmap bitmap = ((MyApplication)getContext().getApplicationContext()).bitmapCache.
                    getBitmap(film.getPosterResourceId(),getContext(),0.1);
            imageView.setImageBitmap(bitmap);*/
            /*ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(params);*/
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);


            Connect.TemUrl temUrl = new Connect.TemUrl();
            temUrl.setConnectionType(Connect.NETWORK_FILM_PICTURE);
            temUrl.addHeader("posterName","Posters/"+film.getPosterName());
            Glide.with(this)
                    .load(temUrl.getSurl())
                    .error(R.drawable.white)
                    .override(1000,500)
                    .into(imageView);
            i++;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            BigPosterAdatper.PagerHolder pagerHolder = new BigPosterAdatper.PagerHolder();
            pagerHolder.poster = imageView;
            pagerHolder.id = film.getId();
            films.add(pagerHolder);
        }
        bigPosterAdatper = new BigPosterAdatper(getActivity(),films);
        bigPoster.setAdapter(bigPosterAdatper);
        bigPoster.setCurrentItem(0);
    }

    public void loadBigPosters(Handler handler){
        if(thread.getState() == SwitchRunnable.STATE_RUNNING) {
            thread.suspend();
        }

        CustomThread thread = new CustomThread(handler);
        thread.start();
    }

    private class CustomThread extends Thread{
        private Handler mhandler;
        public CustomThread(Handler handler) {
            super();
            this.mhandler = handler;
        }

        @Override
        public void run() {
            //posters = getDatas();
            posters = Connect.getBigPosters_HomePageFilm();
            Message msg = new Message();
            msg.arg1 = MESSAGE_LOADFINISHED;
            msg.obj = (Object)mhandler;
            handler.sendMessage(msg);
        }
    }

    class SwitchRunnable implements Runnable {
        public static final int STATE_NONE = 1;
        public static final int STATE_RUNNING = 2;
        public static final int STATE_WAITING = 3;
        public Thread t;
        private int state = STATE_NONE;
        boolean suspended = false;

        public void run() {
            try {
                while (true) {
                    Thread.sleep(5000);
                    synchronized (this) {
                        while (suspended) {
                            wait();
                        }
                    }
                    Message msg = new Message();
                    msg.arg1 = MESSAGE_SWITCH;
                    handler.sendMessage(msg);

                }
            } catch (InterruptedException e) {
            }
        }

        public void start ()
        {
            if (t == null)
            {
                t = new Thread (this);
                t.start ();
                state = STATE_RUNNING;
            }
        }
        void suspend() {
            suspended = true;
            state = STATE_WAITING;
        }
        synchronized void resume() {
            suspended = false;
            state = STATE_RUNNING;
            notify();
        }

        public int getState() {
            return state;
        }
    }
}
