package com.example.hopjs.filmcinema.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hopjs.filmcinema.Data.FilmList;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder>{
    public static final int TYPE_NOWSHOWING = 1;
    public static final int TYPE_UPCOMING = 2;
    public static final int TYPE_SEARCH = 3;
    public final int TYPE_FOOTER = 3;
    private int type;
    private ArrayList<FilmList> filmLists;
    private Context context;
    private FilmListAdapter.myOnItemClickListener listener;
    public interface myOnItemClickListener{
        public void onItemClick(View view, String id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String id;
        private ImageView poster;
        private TextView name;
        private TextView scord;

        private TextView type;
        private TextView date;
        private TextView cinemaNum;
        private TextView showingTimes;
        private FilmListAdapter.myOnItemClickListener listener;

        private TextView footer;

        @Override
        public void onClick(View v) {
            listener.onItemClick(v,id);
        }

        public ViewHolder(View itemView, int vhType, FilmListAdapter.myOnItemClickListener listener)  {
            super(itemView);
            switch (vhType){
                case TYPE_NOWSHOWING:
                    poster = (ImageView) itemView.findViewById(R.id.iv_fragment_film0list_now0showing_poster);
                    name = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_now0showing_name);
                    scord = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_now0showing_scord);
                    cinemaNum = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_now0showing_cinema0num);
                    showingTimes = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_now0showing_showing0times);
                    break;
                case TYPE_UPCOMING:
                    poster = (ImageView) itemView.findViewById(R.id.iv_fragment_film0list_upcoming_poster);
                    name = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_upcoming_name);
                    scord = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_upcoming_scord);
                    type = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_upcoming_type);
                    date = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_upcoming_date);
                    break;
                case TYPE_FOOTER:
                    footer = (TextView) itemView.findViewById(R.id.tv_fragment_film0list_footer_tips);
                    break;
            }
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

    }

    public FilmListAdapter(Context context, ArrayList<FilmList> filmLists, FilmListAdapter.myOnItemClickListener listener, int type) {
        super();
        this.context = context;
        this.filmLists = filmLists;
        this.listener = listener;
        this.type = type;
    }

    public void add(FilmList filmList){
        filmLists.add(filmList);
        notifyItemInserted(filmLists.size());
    }
    @Override
    public void onBindViewHolder(FilmListAdapter.ViewHolder holder, int position) {
        if(position+1 != getItemCount()) {
            holder.id = filmLists.get(position).getId();
          //  holder.poster.setImageBitmap(filmLists.get(position).getPoster());
            /*holder.poster.setImageResource(filmLists.get(position).getPosterResourceId());*/
            /*Bitmap bitmap = ((MyApplication)context.getApplicationContext()).bitmapCache
                    .getBitmap(filmLists.get(position).getPosterResourceId(),context,0.05);
            holder.poster.setImageBitmap(bitmap);*/
            Connect.TemUrl temUrl = new Connect.TemUrl();
            temUrl.setConnectionType(Connect.NETWORK_FILM_PICTURE);
            temUrl.addHeader("posterName",filmLists.get(position).getPosterName());
            Glide.with(context)
                    .load(temUrl.getSurl())
                    .placeholder(R.drawable.x)
                    .error(R.drawable.w)
                    .into(holder.poster);
            holder.name.setText(filmLists.get(position).getName());
            holder.scord.setText(filmLists.get(position).getScord());
            if (type == TYPE_NOWSHOWING) {
                holder.cinemaNum.setText(filmLists.get(position).getCinemaNum());
                holder.showingTimes.setText(filmLists.get(position).getShowingTimes());
            } else {
                holder.type.setText(filmLists.get(position).getType());
                holder.date.setText(filmLists.get(position).getDate());
            }
        }
    }

    @Override
    public FilmListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType != TYPE_FOOTER) {
            if (type == TYPE_NOWSHOWING) {
                view = LayoutInflater.from(context).inflate
                        (R.layout.fragment_film0list_now0showing, parent, false);
            } else {
                view = LayoutInflater.from(context).inflate
                        (R.layout.fragment_film0list_upcoming, parent, false);
            }
        }else {
            view = LayoutInflater.from(context).inflate
                    (R.layout.fragment_film0list_footer, parent, false);
        }
        FilmListAdapter.ViewHolder viewHolder = new FilmListAdapter.ViewHolder(view,type,listener);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return filmLists.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1 == getItemCount()) {
            return TYPE_FOOTER;
        }else{
            return 0;
        }
    }
}