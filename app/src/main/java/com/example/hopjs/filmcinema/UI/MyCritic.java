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

import com.example.hopjs.filmcinema.Adapter.MyCriticAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/18.
 */

public class MyCritic extends AppCompatActivity {

    private final int MESSAGE_REFRESH = 0;
    private final int MESSAGE_MORE = 1;
    private TextView tvTitle;
    private ImageView ivReturn,ivSearch;
    private RecyclerView rvCritic;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private MyCriticAdapter myCriticAdapter;
    private ArrayList<MyCriticAdapter.Critic> critics;
    private String userName;
    private int portraitId;
    private int lastVisibleItem;
    private Handler handler;
    private int start;
    private String userId;
    private boolean isAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycritic);
        tvTitle = (TextView)findViewById(R.id.tv_header_title);
        ivSearch = (ImageView)findViewById(R.id.iv_header_search);
        ivReturn = (ImageView)findViewById(R.id.iv_header_return);
        rvCritic = (RecyclerView)findViewById(R.id.rv_mycritic_critic);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_mycritic_srl);

        rvCritic.setOnTouchListener(touchListener);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        rvCritic.setOnScrollListener(scrollListener);
        linearLayoutManager = new LinearLayoutManager(this);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                if(msg.arg1 == MESSAGE_MORE){
                    myCriticAdapter.setAll(isAll);
                    myCriticAdapter.add(critics);
                }else {
                    setCritics();
                }
            }
        };
        UserAccount userAccount=((MyApplication)getApplicationContext()).userAccount;
        userName = userAccount.getName();
        portraitId = Test.getPicture(10);
        tvTitle.setText("我 的 影 评");
        ivReturn.setOnClickListener(clickListener);
        ivSearch.setOnClickListener(clickListener);
        start = 0;
        isAll=false;
        userId = ((MyApplication)getApplicationContext()).userAccount.getUserId();
        loadCritics();
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
    private void loadCritics(){
        swipeRefreshLayout.setRefreshing(true);
        new Thread(){
            @Override
            public void run() {
                //critics = Test.getMyCritics(10);
                critics = Connect.getMycritic(userId,start+"");
                if(critics.size()<10)isAll=true;
                start += critics.size();
                handler.sendMessage(new Message());
            }
        }.start();
    }
    private void loadMore(){
        //       swipeRefreshLayout.setRefreshing(true);
        new Thread(){
            @Override
            public void run() {
               // critics = Test.getMyCritics(lastVisibleItem);
                if(isAll)return;
                critics = Connect.getMycritic(userId,start+"");
                if(critics.size()<10)isAll=true;
                start += critics.size();
                Message message = new Message();
                message.arg1 = MESSAGE_MORE;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void setCritics(){
        myCriticAdapter = new MyCriticAdapter(MyCritic.this,critics,
                userName,portraitId,itemClickListener);
        myCriticAdapter.setAll(isAll);
        rvCritic.setLayoutManager(linearLayoutManager);
        rvCritic.setAdapter(myCriticAdapter);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_header_search:
                    Transform.toSearch(MyCritic.this);
                    break;
                case R.id.iv_header_return:
                    finish();
                    break;
            }
        }
    };
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            start = 0;
            isAll=false;
            loadCritics();
        }
    };
    private MyCriticAdapter.OnItemClickListener itemClickListener = new MyCriticAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String id) {
            Transform.toFilmDetail(MyCritic.this,id);
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
