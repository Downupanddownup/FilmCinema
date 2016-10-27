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
        setContentView(R.layout.cdetail);


    }





}