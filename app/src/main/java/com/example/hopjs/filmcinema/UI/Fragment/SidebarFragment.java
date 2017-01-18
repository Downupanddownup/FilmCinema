package com.example.hopjs.filmcinema.UI.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.Login;

/**
 * Created by Hopjs on 2016/10/14.
 */

public class SidebarFragment extends Fragment {
    private LinearLayout llHpager,llFilm,llCinema,llQuestion,llHelp,llAbout,llExit;
    private CardView cvPortrait;
    private ImageView ivPortrait;
    private TextView tvNorT;
    private UserAccount userAccount;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadUserInfor();
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sidebar,container,true);
        tvNorT = (TextView)view.findViewById(R.id.tv_sidebar_nameortips);
        ivPortrait = (ImageView)view.findViewById(R.id.iv_sidebar_portrait);
        cvPortrait = (CardView)view.findViewById(R.id.cv_sidebar_card);
        llAbout = (LinearLayout)view.findViewById(R.id.ll_sidebar_about);
        llCinema = (LinearLayout)view.findViewById(R.id.ll_sidebar_cinema);
        llFilm = (LinearLayout)view.findViewById(R.id.ll_sidebar_film);
        llHelp = (LinearLayout)view.findViewById(R.id.ll_sidebar_help);
        llHpager = (LinearLayout)view.findViewById(R.id.ll_sidebar_hpager);
        llQuestion = (LinearLayout)view.findViewById(R.id.ll_sidebar_question);
        llExit = (LinearLayout)view.findViewById(R.id.ll_sidebar_exit);

        cvPortrait.setOnClickListener(listener);
        llQuestion.setOnClickListener(listener);
        llHpager.setOnClickListener(listener);
        llHelp.setOnClickListener(listener);
        llFilm.setOnClickListener(listener);
        llAbout.setOnClickListener(listener);
        llCinema.setOnClickListener(listener);
        llExit.setOnClickListener(listener);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        loadUserInfor();

        return view;
    }

    public void loadUserInfor(){
        userAccount = ((MyApplication)getActivity().getApplicationContext()).userAccount;
        if(userAccount.isLogin()){
            Connect.TemUrl temUrl = new Connect.TemUrl();
            temUrl.setConnectionType(Connect.NETWORK_PORTRAIT);
            temUrl.addHeader("portraitName","Portraits/"+userAccount.getPortraitName());
            Glide.with(getContext())
                    .load(temUrl.getSurl())
                    .error(R.drawable.userportrait)
                    .into(ivPortrait);
            tvNorT.setText(userAccount.getName());
            llExit.setVisibility(View.VISIBLE);
        }else {
            Glide.with(getContext())
                    .load(R.drawable.userportrait)
                    .into(ivPortrait);
            tvNorT.setText("登 录");
            llExit.setVisibility(View.INVISIBLE);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_sidebar_cinema:
                    Transform.toCinema(getActivity());
                    break;
                case R.id.ll_sidebar_about:
                    Transform.toAbout(getActivity());
                    break;
                case R.id.ll_sidebar_film:
                    Transform.toFilm(getActivity());
                    break;
                case R.id.ll_sidebar_help:
                    Transform.toHelp(getActivity());
                    break;
                case R.id.ll_sidebar_hpager:
                    Transform.toHomePage(getActivity());
                    break;
                case R.id.ll_sidebar_question:
                    Transform.toQuestion(getActivity());
                    break;
                case R.id.cv_sidebar_card:
                    if(userAccount.isLogin()){
                        Transform.toPcenter(getActivity());
                    }else {
                        Login login = new Login(getActivity(),handler,0);//getContext(),
                        login.show();
                    }
                    break;
                case R.id.ll_sidebar_exit:
                    ((MyApplication)getActivity().getApplicationContext()).
                            userAccount.setLogin(false);
                    loadUserInfor();
                    llExit.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };


}
