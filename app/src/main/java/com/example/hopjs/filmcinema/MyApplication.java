package com.example.hopjs.filmcinema;

import android.app.Application;

import com.example.hopjs.filmcinema.BitmapTool.BitmapCache;
import com.example.hopjs.filmcinema.Data.UserAccount;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class MyApplication extends Application {
    public BitmapCache bitmapCache;
    public UserAccount userAccount;
    @Override
    public void onCreate() {
        super.onCreate();
        bitmapCache = BitmapCache.getInstance();
        userAccount = new UserAccount();
        userAccount.setLogin(true);
        userAccount.setName("张泉单");
        userAccount.setBphone("13757371020");
    }
}
