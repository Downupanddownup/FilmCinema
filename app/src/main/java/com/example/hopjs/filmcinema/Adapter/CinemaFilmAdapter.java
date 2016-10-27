package com.example.hopjs.filmcinema.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.UI.CinemaDetail;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/27.
 */

public class CinemaFilmAdapter extends RecyclerView.Adapter<CinemaFilmAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivPoster;
        private String id;
        private int index;
        private OnItemClickListener itemClickListener;

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,id,index);
        }

        public ViewHolder(View itemView,int viewType,OnItemClickListener itemClickListener) {
            super(itemView);
            ivPoster = (ImageView)itemView.findViewById(R.id.iv_cdetail_film_item);
            this.itemClickListener = itemClickListener;
            if(viewType == TYPE_NORMAL){
                itemView.setOnClickListener(this);
            }
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,String id,int index);
    }

    public static final int TYPE_NULL = 1;
    public static final int TYPE_NORMAL = 2;
    private OnItemClickListener itemClickListener;
    private ArrayList<CinemaDetail.Film> films;
    private Context context;

    public CinemaFilmAdapter(Context context, ArrayList<CinemaDetail.Film>
            films,OnItemClickListener itemClickListener) {
        this.films = films;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e("cinemafilmadapter","postion:"+position+",getItemCount:"+getItemCount());
        if(position==0 || position == getItemCount()-1){
            holder.ivPoster.setImageBitmap(null);
        }else {
            holder.index = position;
            holder.id = films.get(position-1).getId();
            Bitmap bitmap = ((MyApplication)context.getApplicationContext()).bitmapCache
                    .getBitmap(films.get(position-1).getPosterId(),context,0.1);
            holder.ivPoster.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return films.size()+2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0 || position==getItemCount()-1) {
            return TYPE_NULL;
        }else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate
                (R.layout.cdetail_film_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,viewType,itemClickListener);
        return viewHolder;
    }
}
