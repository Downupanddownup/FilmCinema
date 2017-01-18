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

import com.example.hopjs.filmcinema.Adapter.TicketRecordAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/18.
 */

public class TicketRecord extends AppCompatActivity {
    private final int MESSAGE_REFRESH = 0;
    private final int MESSAGE_MORE = 1;
    private TextView tvTitle;
    private ImageView ivReturn,ivSearch;
    private RecyclerView rvTicketRecord;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private TicketRecordAdapter ticketRecordAdapter;
    private ArrayList<TicketRecordAdapter.TicketRecord> ticketRecords;
    private int lastVisibleItem;
    private Handler handler;
    private int start;
    private boolean isAll;
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket0record);

        tvTitle = (TextView)findViewById(R.id.tv_header_title);
        ivSearch = (ImageView)findViewById(R.id.iv_header_search);
        ivReturn = (ImageView)findViewById(R.id.iv_header_return);
        rvTicketRecord = (RecyclerView)findViewById(R.id.rv_ticket0recrod_ticktet);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_ticket0record_srl);

        rvTicketRecord.setOnTouchListener(touchListener);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        rvTicketRecord.setOnScrollListener(scrollListener);
        linearLayoutManager = new LinearLayoutManager(this);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                swipeRefreshLayout.setRefreshing(false);
                if(msg.arg1 == MESSAGE_MORE){
                    ticketRecordAdapter.setAll(isAll);
                    ticketRecordAdapter.add(ticketRecords);
                }else {
                    setTicketRecords();
                }
            }
        };
        tvTitle.setText("购 票 记 录");
        ivReturn.setOnClickListener(clickListener);
        ivSearch.setOnClickListener(clickListener);
        start = 0;
        userId = ((MyApplication)getApplicationContext()).userAccount.getUserId();
        loadTicketRecords();
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
    private void loadTicketRecords(){
        swipeRefreshLayout.setRefreshing(true);
        new Thread(){
            @Override
            public void run() {
                //ticketRecords = Test.getTicketRecords(10);
                ticketRecords = Connect.getTicketRecorder(userId,start+"");
                if(ticketRecords.size()<10)isAll=true;
                start += ticketRecords.size();
                handler.sendMessage(new Message());
            }
        }.start();
    }
    private void loadMore(){
        //    swipeRefreshLayout.setRefreshing(true);
        new Thread(){
            @Override
            public void run() {
             //   ticketRecords = Test.getTicketRecords(lastVisibleItem);
                if(isAll) return;
                ticketRecords = Connect.getTicketRecorder(userId,start+"");
                if(ticketRecords.size()<10)isAll=true;
                start += ticketRecords.size();
                Message message = new Message();
                message.arg1 = MESSAGE_MORE;
                handler.sendMessage(message);
            }
        }.start();
    }
    private void setTicketRecords(){
        ticketRecordAdapter = new TicketRecordAdapter
                (TicketRecord.this,ticketRecords,itemClickListener);
        ticketRecordAdapter.setAll(isAll);
        rvTicketRecord.setLayoutManager(linearLayoutManager);
        rvTicketRecord.setAdapter(ticketRecordAdapter);
    }
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_header_search:
                    Transform.toSearch(TicketRecord.this);
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
            loadTicketRecords();
        }
    };
    private TicketRecordAdapter.OnItemClickListener itemClickListener = new TicketRecordAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String id) {
            Transform.toFilmDetail(TicketRecord.this,id);
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
