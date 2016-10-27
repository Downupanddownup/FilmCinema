package com.example.hopjs.filmcinema.UI.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.FilmList;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Adapter.FilmListAdapter;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class FilmListFragment extends Fragment {
    private final int FIRST_LOAD = 1;
    private final int LOAD_MORE = 2;

    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvList;
    private FilmListAdapter filmListAdapter;
    private ArrayList<FilmList> filmLists;
    private Handler handler;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private int type = FilmListAdapter.TYPE_NOWSHOWING;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_film0list,container,false);
        
        srlRefresh = (SwipeRefreshLayout)view.findViewById(R.id.srl_fragment_film0list_refresh);
        rvList = (RecyclerView)view.findViewById(R.id.rv_fragment_film0list_list);

        srlRefresh.setRefreshing(true);
        srlRefresh.setOnRefreshListener(refreshListener);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvList.setOnScrollListener(scrollListener);
        rvList.setOnTouchListener(new View.OnTouchListener() {
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
                    case FIRST_LOAD:
                        setFilmListDatas();
                        break;
                    case LOAD_MORE:
                        setMore();
                        break;
                }
                srlRefresh.setRefreshing(false);
            }
        };

        Bundle bundle = getArguments();
        if(bundle != null) {
            type = Integer.parseInt((String) bundle.get("type"));
        }

        loadFilmListDatas();
        
        return view;
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_SETTLING &&
                    lastVisibleItem +1 == filmListAdapter.getItemCount()){
                loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadFilmListDatas();
        }
    };

    private void loadMore(){
        new Thread(){
            @Override
            public void run() {
                filmLists = new ArrayList<>();
                for(int i=0; i<10;++i){
                    FilmList filmList = new FilmList();
                    filmList.setId(i+"");
                    filmList.setName("电影"+i);
                    filmList.setScord(i*8%10+5+"");
                    /*filmList.setPoster(getPoster(i));*/
                    filmList.setPosterResourceId(Test.getPicture(i));
                    filmList.setType("类型："+i);
                    filmList.setDate("2016年10月"+i+"日");
                    filmList.setCinemaNum(i*55+i*10+i+"");
                    filmList.setShowingTimes(12*(66*i)+"");
                    filmLists.add(filmList);
                }

                Message message = new Message();
                message.arg1 = LOAD_MORE;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void setMore(){
        for(FilmList filmList:filmLists){
            filmListAdapter.add(filmList);
        }
    }

    private void loadFilmListDatas(){
        new Thread(){
            @Override
            public void run() {
                filmLists = new ArrayList<>();
                for(int i=0; i<10;++i){
                    FilmList filmList = new FilmList();
                    filmList.setId(i+"");
                    filmList.setName("电影"+i);
                    filmList.setScord(i*8%10+5+"");
                    /*filmList.setPoster(getPoster(i));*/
                    filmList.setPosterResourceId(Test.getPicture(i));
                    filmList.setType("类型："+i);
                    filmList.setDate("2016年10月"+i+"日");
                    filmList.setCinemaNum(i*55+i*10+i+"");
                    filmList.setShowingTimes(12*(66*i)+"");
                    filmLists.add(filmList);
                }

                Message message = new Message();
                message.arg1 = FIRST_LOAD;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void setFilmListDatas(){
        filmListAdapter = new FilmListAdapter(getActivity(),filmLists,listener,
                type);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setAdapter(filmListAdapter);
        rvList.setItemAnimator(new DefaultItemAnimator());
    }

    private FilmListAdapter.myOnItemClickListener listener = new FilmListAdapter.myOnItemClickListener() {
        @Override
        public void onItemClick(View view, String id) {
            Transform.toFilmDetail(getActivity(),id);
        }
    };

    private Bitmap getPoster(int index){
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

        return posters.get(index%4);
    }


}
