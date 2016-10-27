package com.example.hopjs.filmcinema;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.hopjs.filmcinema.Adapter.CollectionAdapter;
import com.example.hopjs.filmcinema.Adapter.CriticAdapter;
import com.example.hopjs.filmcinema.Adapter.MyCriticAdapter;
import com.example.hopjs.filmcinema.Adapter.TicketRecordAdapter;
import com.example.hopjs.filmcinema.Adapter.WorkersAdapter;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.Fragment.CinemaListFragment;
import com.example.hopjs.filmcinema.UI.Login;
import com.example.hopjs.filmcinema.UI.TicketRecord;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Hopjs on 2016/10/9.
 */

public class TestCell extends AppCompatActivity {
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cdetail_session);

        linearLayout = (LinearLayout)findViewById(R.id.ll_cdetail_session_tab);
        for(int i=1;i<5;++i) {
            final TextView textView = new TextView(this);
            WindowManager manager = this.getWindowManager();
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    manager.getDefaultDisplay().getWidth()/3,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(params);
            textView.setText("tab:"+i);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i<linearLayout.getChildCount();++i){
                        TextView textView = (TextView)linearLayout.getChildAt(i);
                        if(textView!= v){
                            textView.setBackgroundColor(Color.TRANSPARENT);
                        }else {
                            textView.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                        }
                    }
                }
            });
            linearLayout.addView(textView);
        }
    }



}