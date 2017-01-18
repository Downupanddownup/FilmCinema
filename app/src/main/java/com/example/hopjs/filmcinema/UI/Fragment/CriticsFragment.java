package com.example.hopjs.filmcinema.UI.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hopjs.filmcinema.Adapter.CriticAdapter;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.FilmDetail;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/16.
 */

public class CriticsFragment extends Fragment {
    private final int MESSAGE_FIRST = 0;
    private final int MESSAGE_MORE = 1;
    private String filmId;

    private RecyclerView rvCritics;
    private LinearLayoutManager linearLayoutManager;
    public CriticAdapter criticAdapter;
    private ArrayList<CriticAdapter.Critic> critics;
    private Handler handler;
    private int start;
    private boolean isAll=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.film0detail_critic,container,false);
        rvCritics = (RecyclerView)view.findViewById(R.id.rv_film0detail_critic_c);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case MESSAGE_FIRST:
                        setCritics();
                        break;
                    case MESSAGE_MORE:
                        criticAdapter.setIsAll(isAll);
                        criticAdapter.add(critics);
                        break;
                }
            }
        };
        filmId = ((MyApplication)getActivity().getApplicationContext()).filmId;
        loadCritics();
        start = 0;
        return view;
    }


    private void loadCritics(){
        new Thread(){
            @Override
            public void run() {
                //critics = Test.getCritics(5);
            //    while (!getFilmId);
                Log.e("ooooooooooooooo","CriticsFragment:loadCritics,filmId"+filmId);
                critics = Connect.getCritic_FilmDeatil(filmId,start+"");
                if(critics.size()<5)isAll=true;
                start += critics.size();
                Message message = new Message();
                message.arg1 = MESSAGE_FIRST;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void setCritics(){

        criticAdapter = new CriticAdapter(getActivity().getApplicationContext(),
                critics, itemClickListener,lastItemClickListener);
        criticAdapter.setIsAll(isAll);
        rvCritics.setLayoutManager(linearLayoutManager);
        rvCritics.setAdapter(criticAdapter);
    }
    private CriticAdapter.OnLastItemClickListener lastItemClickListener = new CriticAdapter.OnLastItemClickListener() {
        @Override
        public void onLastItemClick(View view) {
            new Thread(){
                @Override
                public void run() {
                   // critics = Test.getCritics(10);
              //      while (!getFilmId);
                    if(isAll)return;
                    critics = Connect.getCritic_FilmDeatil(filmId,start+"");
                    start += critics.size();
                    if(critics.size()<5)isAll=true;
                    Message message = new Message();
                    message.arg1 = MESSAGE_MORE;
                    handler.sendMessage(message);
                }
            }.start();
        }
    };
    private CriticAdapter.OnItemClickListener itemClickListener = new CriticAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String id) {
            Test.showToast(getActivity().getApplicationContext(),"你点击了用户"+id+"的头像");
        }
    };
}
