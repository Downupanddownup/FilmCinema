package com.example.hopjs.filmcinema.UI.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Adapter.SessionAdapter;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.TestCell;
import com.example.hopjs.filmcinema.UI.CinemaDetail;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/20.
 */

public class SessionFragment extends Fragment {
    private LinearLayout llTabs;
    private RecyclerView rvSessions;
    private ProgressBar progressBar;
    private Handler handler;
    private SessionAdapter sessionAdapter;
    private ArrayList<CinemaDetail.Session> sessions;
    private ArrayList<CinemaDetail.Session> thisSessions;
    private ArrayList<Date> dates;
    private String filmId;
    private String cinemaId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cdetail_session,container,false);
        rvSessions = (RecyclerView)view.findViewById(R.id.rv_cdetail_session_s);
        llTabs = (LinearLayout)view.findViewById(R.id.ll_cdetail_session_tab);
        progressBar = (ProgressBar)view.findViewById(R.id.pb_cdetail_pb);

        progressBar.setMax(100);




        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                setSessions();
                progressBar.setProgress(100);
                progressBar.setVisibility(View.INVISIBLE);
            }
        };

  //      loadSessions();

        return view;
    }

    public void setCinemaId(String cinemaId){
        this.cinemaId = cinemaId;
    }
    public void changeFilm(String filmId){
        progressBar.setVisibility(View.VISIBLE);
        this.filmId = filmId;
        loadSessions();
    }

    private void createTabs(){
        llTabs.removeAllViews();
        if(dates==null)return;
        if(dates.size()==0)return;
        for(int i=0;i<dates.size();++i) {
            final TextView textView = new TextView(getActivity());
            WindowManager manager = getActivity().getWindowManager();
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    manager.getDefaultDisplay().getWidth()/3,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setText(dates.get(i).getShowDate());
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i<llTabs.getChildCount();++i){
                        TextView textView = (TextView)llTabs.getChildAt(i);
                        if(textView!= v){
                            textView.setBackgroundColor(Color.TRANSPARENT);
                        }else {
                            textView.setBackgroundColor(getResources().getColor(R.color.ButtomGuidBar));

                            thisSessions = getThisSessions(dates.get(i).getDate());
                            sessionAdapter.replaceSessions(thisSessions,dates.get(i).getDate());
                            rvSessions.setAdapter(sessionAdapter);
                        }
                    }
                }
            });
            llTabs.addView(textView);
        }
    }

    private void loadSessions(){
        new Thread(){
            @Override
            public void run() {
                // sessions = Test.getSessions(filmId,cinemaId);
                sessions = Connect.getSession_CinemaDetail(cinemaId,filmId);

                handler.sendMessage(new Message());
            }
        }.start();
    }

    private void setSessions(){
        dates = getDates();
        createTabs();
        rvSessions.setLayoutManager(new LinearLayoutManager(getActivity()));
        thisSessions = getThisSessions(dates.get(0).getDate());
        llTabs.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.ButtomGuidBar));
        sessionAdapter = new SessionAdapter(getActivity(),thisSessions,cinemaId,dates.get(0).getDate());
        rvSessions.setAdapter(sessionAdapter);
        rvSessions.setItemAnimator(new DefaultItemAnimator());
    }
    @Nullable
    private ArrayList<Date> getDates(){
        ArrayList<Date> dates = new ArrayList<>();
        if(sessions == null){
            return  null;
        }
        if (sessions.size()<1)return null;
        String date = sessions.get(0).getDate();
        dates.add(new Date(date));
        for(int i=1;i<sessions.size();++i){
            date = sessions.get(i).getDate();
            if(!exist(dates,date)){
                dates.add(new Date(date));
            }
        }
        return  dates;
    }
    private boolean exist(ArrayList<Date> dates,String date){
        for(int i=0; i<dates.size(); ++i){
            if(date.equals(dates.get(i).getDate())){
                return true;
            }
        }
        return false;
    }
    private ArrayList<CinemaDetail.Session> getThisSessions(String date){
        ArrayList<CinemaDetail.Session> thisSessions = new ArrayList<>();
        for(int i=0; i<sessions.size();++i){
            if(date.equals(sessions.get(i).getDate())){
                thisSessions.add(sessions.get(i));
            }
        }
        return  thisSessions;
    }
    private class Date{
        private String date;
        public Date(String date) {
            this.date = date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }
        public String getShowDate() {
            String date = "";
            for(int i=0;i<this.date.length();++i){
                if(i>3){
                    if(i==6)date+=".";
                    date += this.date.charAt(i);
                }
            }
            return date;
        }
    }
}
