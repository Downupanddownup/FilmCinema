package com.example.hopjs.filmcinema.Adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hopjs.filmcinema.Common.Transform;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/9.
 */

public class BigPosterAdatper extends PagerAdapter {
    public static class PagerHolder{
        public ImageView poster;
        public String id;
    }
    private Activity activity;
    private ArrayList<PagerHolder> posters;
    public BigPosterAdatper(Activity activity,ArrayList<PagerHolder> posters) {
        super();
        this.posters = posters;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return posters.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        container.addView(posters.get(position).poster);
        posters.get(position).poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transform.toFilmDetail(activity,posters.get(position).id);
            }
        });
        return posters.get(position).poster;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(posters.get(position).poster);
    }

}
