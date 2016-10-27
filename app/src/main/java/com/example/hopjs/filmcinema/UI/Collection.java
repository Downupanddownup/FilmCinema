package com.example.hopjs.filmcinema.UI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Adapter.CollectionAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/18.
 */

public class Collection extends AppCompatActivity {
    private final int MESSAGE_REFRESH = 0;
    private final int MESSAGE_MORE = 1;
    private TextView tvTitle;
    private ImageView ivReturn,ivSearch;
    private RecyclerView rvCollection;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private CollectionAdapter collectionAdapter;
    private ArrayList<CollectionAdapter.Collection> collections;
    private int lastVisibleItem;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        tvTitle = (TextView)findViewById(R.id.tv_header_title);
        ivSearch = (ImageView)findViewById(R.id.iv_header_search);
        ivReturn = (ImageView)findViewById(R.id.iv_header_return);
        rvCollection = (RecyclerView)findViewById(R.id.rv_collection_c);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_collection_srl);

        rvCollection.setOnTouchListener(touchListener);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        rvCollection.setOnScrollListener(scrollListener);
        linearLayoutManager = new LinearLayoutManager(this);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                if(msg.arg1 == MESSAGE_MORE){
                    collectionAdapter.add(collections);
                }else {
                    setCollections();
                }
            }
        };
        tvTitle.setText("收 藏");
        ivReturn.setOnClickListener(clickListener);
        ivSearch.setOnClickListener(clickListener);
        loadCollections();
    }

    private RecyclerView.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(swipeRefreshLayout.isRefreshing()) {
                return true;
            }else {
                return false;
            }
        }
    };
    private void loadCollections(){
        swipeRefreshLayout.setRefreshing(true);
        new Thread(){
            @Override
            public void run() {
                collections = Test.getCollections(10);
                handler.sendMessage(new Message());
            }
        }.start();
    }
    private void loadMore(){
    //    swipeRefreshLayout.setRefreshing(true);
        new Thread(){
            @Override
            public void run() {
                collections = Test.getCollections(lastVisibleItem);
                Message message = new Message();
                message.arg1 = MESSAGE_MORE;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void setCollections(){
        collectionAdapter = new CollectionAdapter
                (Collection.this,collections,itemClickListener);
        rvCollection.setLayoutManager(linearLayoutManager);
        rvCollection.setAdapter(collectionAdapter);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_header_search:
                    Test.showToast(Collection.this,"你点击了搜索按钮");
                    break;
                case R.id.iv_header_return:
                    Test.showToast(Collection.this,"你点击了返回按钮");
                    break;
            }
        }
    };
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadCollections();
        }
    };
    private CollectionAdapter.OnItemClickListener itemClickListener = new CollectionAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String id) {
            Transform.toFilmDetail(Collection.this,id);
        }
    };
    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_SETTLING &&
                    lastVisibleItem == linearLayoutManager.getItemCount()-1){
                loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        }
    };

}
