package com.example.hopjs.filmcinema;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TabHost;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Hopjs on 2016/10/9.
 */

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment,container,false);
        final TextView tv = (TextView)view.findViewById(R.id.textView);
        Button button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("你惦记了按钮");
            }
        });
        Bundle bundle = getArguments();
    //    tv.setText((String)bundle.get("text"));
        Date date = new Date();
      //  tv.setText(date.getSeconds()+"");
        Activity activity = getActivity();
        ViewPager viewPager = (ViewPager)activity.findViewById(R.id.vp_view);
       // changeViewPageScroller(viewPager);
        return view;
    }
    //反射机制   控制 viewpager滑动时间  为800
    private void changeViewPageScroller(ViewPager vp) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller scroller;
            scroller = new FixedSpeedScroller(getActivity(),new AccelerateDecelerateInterpolator());
            mField.set(vp, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class FixedSpeedScroller extends Scroller {
        private int mDuration = 600;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy,
                                int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }

    };
}
