package com.example.hopjs.filmcinema;

import android.app.Application;

import com.example.hopjs.filmcinema.Adapter.RvCityAdapter;
import com.example.hopjs.filmcinema.BitmapTool.BitmapCache;
import com.example.hopjs.filmcinema.Data.TicketInformation;
import com.example.hopjs.filmcinema.Data.UserAccount;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class MyApplication extends Application {
    public BitmapCache bitmapCache;
    public UserAccount userAccount;
    public RvCityAdapter.City city;
    public TicketInformation ticketInformation;
    public int cityId;
    public String filmId;
    public boolean isRemebered;
    @Override
    public void onCreate() {
        super.onCreate();
        bitmapCache = BitmapCache.getInstance();
        userAccount = new UserAccount();
        isRemebered=false;
        /*userAccount.setLogin(true);
        userAccount.setName("张泉单");
        userAccount.setUserId("1");
        userAccount.setBphone("13757371020");
        userAccount.setSex("男");
        userAccount.setSetportrait(false);
        userAccount.setPwd("1234");*/
        ticketInformation = new TicketInformation();
        city = new RvCityAdapter.City();
        city.setId("1");
        city.setName("杭 州");
    }
}
