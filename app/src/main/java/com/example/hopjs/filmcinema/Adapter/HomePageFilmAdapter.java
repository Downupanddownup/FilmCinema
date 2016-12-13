package com.example.hopjs.filmcinema.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hopjs.filmcinema.Data.HomePageFilm;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/9.
 */

public class HomePageFilmAdapter extends RecyclerView.Adapter<HomePageFilmAdapter.ViewHolder> {

    public static final int NOWSHOWING = 1;
    public static final int UPCOMING = 2;
    public ArrayList<HomePageFilm> mDatas;

    private Context context;
    private int type;
    private myItemClickListener listener;

    public interface  myItemClickListener{
        public void onClick(View v,String id);
    }

    public HomePageFilmAdapter(Context context, ArrayList<HomePageFilm> datas, int type,myItemClickListener listener) {
        super();
        mDatas = datas;
        this.context = context;
        this.type = type;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        String id;
        ImageView poster;
        TextView name;
        TextView scord;
        TextView date;
        myItemClickListener listener;

        @Override
        public void onClick(View v) {
            listener.onClick(v,id);
        }

        public ViewHolder(View itemView, myItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            switch (type){
                case NOWSHOWING:
                    poster = (ImageView)itemView.findViewById(R.id.iv_home0page_now0showing_poster);
                    name = (TextView)itemView.findViewById(R.id.tv_home0page_now0showing_name);
                    scord = (TextView)itemView.findViewById(R.id.tv_home0page_now0showing_scord);
                    break;
                case UPCOMING:
                    poster = (ImageView)itemView.findViewById(R.id.iv_home0page_upcoming_poster);
                    name = (TextView)itemView.findViewById(R.id.tv_home0page_upcoming_name);
                    scord = (TextView)itemView.findViewById(R.id.tv_home0page_upcoming_scord);
                    date = (TextView)itemView.findViewById(R.id.tv_home0page_upcoming_date);
                    break;
                default:
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(type == UPCOMING){
            holder.date.setText(mDatas.get(position).getDate());
        }
       // holder.poster.setImageBitmap(mDatas.get(position).getPoster());
        /*Bitmap bitmap = ((MyApplication)context.getApplicationContext()).bitmapCache.
                getBitmap(mDatas.get(position).getPosterResourceId(),context,0.05);
        holder.poster.setImageBitmap(bitmap);*/
        Connect.TemUrl temUrl = new Connect.TemUrl();
        temUrl.setConnectionType(Connect.NETWORK_FILM_PICTURE);
        temUrl.addHeader("filmId","");
        Glide.with(context)
                .load(temUrl.getSurl())
                .placeholder(R.drawable.x)
                .error(R.drawable.w)
                .into(holder.poster);
        holder.name.setText(mDatas.get(position).getName());
        holder.scord.setText(mDatas.get(position).getScord());
        holder.id = mDatas.get(position).getId();
    }

    @Override
    public int getItemCount() {
        Log.e("aaaaaa","size:"+mDatas.size());
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (type == NOWSHOWING) {
            view = LayoutInflater.from(context).inflate
                    (R.layout.home0page_now0showing_item, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate
                    (R.layout.home0page_upcoming_item, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view,listener);
        return viewHolder;
    }

}
