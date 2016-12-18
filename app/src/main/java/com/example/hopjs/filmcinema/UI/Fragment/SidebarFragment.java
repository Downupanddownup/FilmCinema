package com.example.hopjs.filmcinema.UI.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.Login;

/**
 * Created by Hopjs on 2016/10/14.
 */

public class SidebarFragment extends Fragment {
    private LinearLayout llHpager,llFilm,llCinema,llQuestion,llHelp,llAbout;
    private CardView cvPortrait;
    private ImageView ivPortrait;
    private TextView tvNorT;
    private UserAccount userAccount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sidebar,container,false);
        tvNorT = (TextView)view.findViewById(R.id.tv_sidebar_nameortips);
        ivPortrait = (ImageView)view.findViewById(R.id.iv_sidebar_portrait);
        cvPortrait = (CardView)view.findViewById(R.id.cv_sidebar_card);
        llAbout = (LinearLayout)view.findViewById(R.id.ll_sidebar_about);
        llCinema = (LinearLayout)view.findViewById(R.id.ll_sidebar_cinema);
        llFilm = (LinearLayout)view.findViewById(R.id.ll_sidebar_film);
        llHelp = (LinearLayout)view.findViewById(R.id.ll_sidebar_help);
        llHpager = (LinearLayout)view.findViewById(R.id.ll_sidebar_hpager);
        llQuestion = (LinearLayout)view.findViewById(R.id.ll_sidebar_question);

        cvPortrait.setOnClickListener(listener);
        llQuestion.setOnClickListener(listener);
        llHpager.setOnClickListener(listener);
        llHelp.setOnClickListener(listener);
        llFilm.setOnClickListener(listener);
        llAbout.setOnClickListener(listener);
        llCinema.setOnClickListener(listener);

        loadUserInfor();
        return view;
    }

    private void loadUserInfor(){
        Bitmap bitmap;
        userAccount = ((MyApplication)getActivity().getApplicationContext()).userAccount;
        if(userAccount.isLogin()){
            if(userAccount.isSetportrait()){
                /*bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                        getBitmap(userAccount.getPortraitId(),getActivity().getApplicationContext(),0.1);
                ivPortrait.setImageBitmap(bitmap);*/
            }else {
                bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                        getBitmap(R.drawable.defaultportrait,getActivity().getApplicationContext(),0.1);
                ivPortrait.setImageBitmap(bitmap);
            }
            tvNorT.setText(userAccount.getName());
        }else {
            bitmap = ((MyApplication)getActivity().getApplicationContext()).bitmapCache.
                    getBitmap(R.drawable.portrait,getActivity().getApplicationContext(),0.1);
            ivPortrait.setImageBitmap(bitmap);
            tvNorT.setText("登 录");
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
                    Login login = new Login(getContext(),getActivity());
                    login.show();
                    break;
            }
        }
    };

}
