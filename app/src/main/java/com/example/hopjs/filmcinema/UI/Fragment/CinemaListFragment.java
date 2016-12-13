package com.example.hopjs.filmcinema.UI.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.hopjs.filmcinema.Adapter.CinemasAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.Cinema;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class CinemaListFragment extends Fragment {
    public static final int TYPE_ALLCITY = 1;
    public static final int TYPE_NEARBY = 2;
    private final int REFRESH_FINISHED = 1;
    private final int LOAD_MORE_FINISHED = 2;
    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvCinemas;
    private ArrayList<Cinema> cinemas;
    private CinemasAdapter cinemasAdapter;
    private Handler handler;
    private LinearLayoutManager linearLayoutManager;
    private int start,lastVisibleItem;
    private int type;
    private boolean noSpecial;
    private String filmId;
    private String cityId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cinemas,container,false);

        srlRefresh = (SwipeRefreshLayout)view.findViewById(R.id.srl_fragment_cinemas_refresh);
        rvCinemas = (RecyclerView)view.findViewById(R.id.rv_fragment_cinemas_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        rvCinemas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(srlRefresh.isRefreshing()) {
                    return true;
                }else {
                    return false;
                }
            }
        });
        rvCinemas.setOnScrollListener(onScrollListener);
        srlRefresh.setRefreshing(true);
        srlRefresh.setOnRefreshListener(onRefreshListener);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == REFRESH_FINISHED){
                    setCinemas();
                }else {
                    setMore();
                }
                srlRefresh.setRefreshing(false);
            }
        };

        type = TYPE_ALLCITY;
        Bundle bundle = getArguments();
        if(bundle != null && bundle.get("type")!= null){
            type = Integer.parseInt(bundle.get("type").toString());
        }

        noSpecial = true;
        if(bundle != null && bundle.get("filmId")!= null){
            noSpecial = false;
            filmId = bundle.get("filmId").toString();
        }
        start = 0;
        cityId = ((MyApplication)getActivity().getApplicationContext()).cityId+"";
        loadCinemas();
        return view;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_SETTLING &&
                    lastVisibleItem+1 == linearLayoutManager.getItemCount()){
                loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        }
    };

    private void setMore(){
        for(Cinema cinema:cinemas){
            cinemasAdapter.add(cinema);
        }
    }
    private void loadMore(){
        new Thread(){
            @Override
            public void run() {
                //cinemas = Test.getCinemas(start,10);
                if(noSpecial) {
                    if (type == TYPE_ALLCITY) {
                        cinemas = Connect.getAllCity_CinemaList_NoSpecial(cityId,start+"");
                    } else {
                        cinemas = Connect.getNearby_CinemaList_NoSpecial(cityId,"jing", "wei",start+"" );
                    }
                }else {
                    if (type == TYPE_ALLCITY) {
                        cinemas = Connect.getAllCity_CinemaList_Special(filmId,cityId,start+"");
                    } else {
                        cinemas = Connect.getNearby_CinemaList_Special(filmId,cityId, "jing", "wei",start+"");
                    }
                }
                Message message = new Message();
                message.arg1 = LOAD_MORE_FINISHED;
                handler.sendMessage(message);
                start += 10;
            }
        }.start();
    }

    private void loadCinemas(){
        new Thread(){
            @Override
            public void run() {
               // cinemas = Test.getCinemas(start,10);
                if(noSpecial) {
                    if (type == TYPE_ALLCITY) {
                        cinemas = Connect.getAllCity_CinemaList_NoSpecial(cityId,start+"");
                    } else {
                        cinemas = Connect.getNearby_CinemaList_NoSpecial(cityId,"jing", "wei",start+"" );
                    }
                }else {
                    if (type == TYPE_ALLCITY) {
                        cinemas = Connect.getAllCity_CinemaList_Special(filmId,cityId,start+"");
                    } else {
                        cinemas = Connect.getNearby_CinemaList_Special(filmId,cityId, "jing", "wei",start+"");
                    }
                }

                start += 10;
                Message message = new Message();
                message.arg1 = REFRESH_FINISHED;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void setCinemas(){
        rvCinemas.setLayoutManager(linearLayoutManager);
        cinemasAdapter = new CinemasAdapter(getActivity(),cinemas,listener);
        rvCinemas.setAdapter(cinemasAdapter);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            start = 0;
            loadCinemas();
        }
    };

    private CinemasAdapter.OnItemClickListener listener = new CinemasAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String id) {
            Transform.toCinemaDetail(getActivity(),id);
        }
    };
}
