package com.example.hopjs.filmcinema;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.hopjs.filmcinema.Adapter.CinemaFilmAdapter;
import com.example.hopjs.filmcinema.Adapter.CollectionAdapter;
import com.example.hopjs.filmcinema.Adapter.CriticAdapter;
import com.example.hopjs.filmcinema.Adapter.MyCriticAdapter;
import com.example.hopjs.filmcinema.Adapter.SessionAdapter;
import com.example.hopjs.filmcinema.Adapter.TicketRecordAdapter;
import com.example.hopjs.filmcinema.Adapter.WorkersAdapter;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.Cinema;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.CinemaDetail;
import com.example.hopjs.filmcinema.UI.Fragment.CinemaListFragment;
import com.example.hopjs.filmcinema.UI.Fragment.SessionFragment;
import com.example.hopjs.filmcinema.UI.Login;
import com.example.hopjs.filmcinema.UI.TicketRecord;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Hopjs on 2016/10/9.
 */

public class TestCell extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        final EditText editText=(EditText)findViewById(R.id.et_test_id);
        Button button = (Button)findViewById(R.id.bt_test_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {

                        switch (Integer.parseInt(editText.getText().toString())){
                            case 1:
                                UserAccount userAccount=new UserAccount();
                                userAccount.setName("云淡风轻");
                                userAccount.setPwd("1234567");
                                userAccount.setBphone("15988169467");
                                Connect.postRegister(userAccount);
                                break;
                            case 2:
                                Connect.postLogin("15988169467","1234568");
                                break;
                            case 3:
                                String sessionId="1201612159:00";
                                String cinemaId="1";
                                String userId="071";
                                Time time =new Time("GMT");
                                time.setToNow();
                                String Time=""+time.year+time.month+time.monthDay+((time.hour+8)%24+1)+time.minute+"";
                                ArrayList<Integer> a=new ArrayList<Integer>();
                                a.add(43);
                                a.add(14);
                                Connect.postBuyTicket(sessionId,userId
                                ,cinemaId,Time,"14",a);
                                break;
                            case 4:

                                Connect.postPsd("071","654321");
                                break;
                            case 5:

                                Connect.postPhone("071","1598");
                                break;
                            case 6:
                                CriticAdapter.Critic critic = new CriticAdapter.Critic();
                                critic.setId("071");
                                critic.setFilmId("002");
                                critic.setScord(7.5f);
                                critic.setContent("很棒");
                                time =new Time("GMT");
                                time.setToNow();
                                critic.setDate(""+time.year+time.month+time.monthDay+((time.hour+8)%24+1)+time.minute+"");

                                Connect.postCritic(critic);
                                break;
                            case 7:Connect.getSeat("001","002","2016122422000");
                                    break;

                        }
                    }
                }.start();
            }
        });



    }





}