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

import com.example.hopjs.filmcinema.Adapter.WorkersAdapter;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.FilmDetail;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/16.
 */

public class WorkersFragment extends Fragment {
    private final int MESSAGE_DANDA = 1;
    private final int MESSAGE_MORE = 2;

    private String filmId;
    private WorkersAdapter.Director director;
    private ArrayList<WorkersAdapter.Actors> actors;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisableItem;
    private WorkersAdapter workersAdapter;
    private Handler handler;
    private ExpandableTextView expandableTextView;
    private int start;
    private String plot;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.film0detail_minfor,container,false);
        expandableTextView = (ExpandableTextView)view.findViewById
                (R.id.etv_film0detail_minfor_plot);
        recyclerView = (RecyclerView)view.findViewById
                (R.id.rv_film0detail_minfor_workers);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case MESSAGE_DANDA:
                        setDandA();
                        break;
                    case MESSAGE_MORE:
                        workersAdapter.add(actors);
                        break;
                }
            }
        };

      //  filmId = ((FilmDetail)getActivity()).filmId;
        filmId = ((MyApplication)getActivity().getApplicationContext()).filmId;
        loadDAandPlot();
        start = 0;
        return  view;
    }



    private WorkersAdapter.OnItemClickListener listener = new WorkersAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String id) {
            Test.showToast(getActivity().getApplicationContext(),"该工作人员id为:"+id);
        }
    };

    private void setDandA(){
        expandableTextView.setText(plot);
        workersAdapter = new WorkersAdapter(getActivity().getApplicationContext(),director,actors,listener);
        linearLayoutManager = new LinearLayoutManager
                (getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workersAdapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_SETTLING &&
                        lastVisableItem == linearLayoutManager.getItemCount()-1){
                    loadActors();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisableItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private void loadDAandPlot(){
        new Thread(){
            @Override
            public void run() {
                Message message = new Message();
                message.arg1 = MESSAGE_DANDA;
                Log.e("ooooooooooooooo","WorkersFragment:loadDAandPlot,filmId:"+filmId);
             //   while (!getFilmId);
                //expandableTextView.setText(Test.getPlot());
                plot=Connect.getPlot(filmId);

                // expandableTextView.setText(Test.getNidemingziPlot());
                //director = Test.getDirector();
                director = Connect.getDirector_FilmDeatil(filmId);
                // director = Test.getDirector();
                // actors = Test.getActors();
                actors = Connect.getActors_FilmDeatail(filmId,start+"");
                start += 10;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void loadActors(){
        new Thread(){
            @Override
            public void run() {
                Message message = new Message();
                message.arg1 = MESSAGE_MORE;
            //    while (!getFilmId);
                //actors = Test.getActors();
                actors = Connect.getActors_FilmDeatail(filmId,start+"");
                start += 10;
                handler.sendMessage(message);
            }
        }.start();
    }
}
