package com.example.hopjs.filmcinema.UI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Adapter.RvCityAdapter;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/12/17.
 */

public class City extends AppCompatActivity {

    private ImageView ivReturn;
    private TextView tvTitle,tvConfirm,tvCurrentCity;
    private RecyclerView rvCity;
    private RvCityAdapter rvCityAdapter;
    private ArrayList<RvCityAdapter.City> cities;
    private Handler handler;
    private LinearLayoutManager linearLayoutManager;
    private RvCityAdapter.City city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);

        ivReturn = (ImageView)findViewById(R.id.iv_city_return);
        tvTitle = (TextView)findViewById(R.id.tv_city_title);
        tvConfirm = (TextView)findViewById(R.id.tv_city_choose);
        tvCurrentCity = (TextView)findViewById(R.id.tv_city_name);
        rvCity = (RecyclerView)findViewById(R.id.rv_city_list);

        tvTitle.setText("城 市");
        String cityName = ((MyApplication)getApplicationContext()).city.getName();
        tvCurrentCity.setText(cityName);

        ivReturn.setOnClickListener(listener);
        tvConfirm.setOnClickListener(listener);

        city = new RvCityAdapter.City();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setCityData();
            }
        };
        loadCityData();

    }
    private void setCityData(){
        linearLayoutManager = new LinearLayoutManager
                (this,LinearLayoutManager.VERTICAL,false);
        rvCity.setLayoutManager(linearLayoutManager);
        rvCityAdapter = new RvCityAdapter(this,cities,onItemClickListener);
        rvCity.setAdapter(rvCityAdapter);
    }

    private void loadCityData(){
        new Thread(){
            @Override
            public void run() {
                cities = Test.getCitys();
                handler.sendMessage(new Message());
            }
        }.start();
    }

    private RvCityAdapter.OnItemClickListener onItemClickListener = new RvCityAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, String cityId) {
            tvCurrentCity.setText(((TextView)view).getText());
            city.setId(cityId);
            city.setName(((TextView)view).getText()+"");
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_city_return:
                    finish();
                    break;
                case R.id.tv_city_choose:
                    ((MyApplication)getApplicationContext()).city =city;
                    finish();
                    break;
            }
        }
    };
}
