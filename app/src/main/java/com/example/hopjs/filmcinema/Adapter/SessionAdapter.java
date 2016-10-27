package com.example.hopjs.filmcinema.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
import com.example.hopjs.filmcinema.UI.CinemaDetail;

import junit.framework.TestSuite;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/20.
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHodler> {
    public static class ViewHodler extends  RecyclerView.ViewHolder{
        public String filmId;
        public String sessionId;
        public TextView tvTime;
        public TextView tvPrice;
        public TextView tvVideoHallNum;
        public Button btBuy;

        public ViewHodler(View itemView,Activity activity) {
            super(itemView);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_session_price);
            tvVideoHallNum = (TextView) itemView.findViewById(R.id.tv_session_videonum);
            tvTime = (TextView) itemView.findViewById(R.id.tv_session_time);
            btBuy = (Button) itemView.findViewById(R.id.tv_session_buy);
            btBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }


    private ArrayList<CinemaDetail.Session> sessions;
    private Context context;
    private Activity activity;

    public SessionAdapter(Context context,Activity activity) {
        super();
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {
        holder.sessionId = sessions.get(position).getSessionId();
        holder.filmId = sessions.get(position).getFilmId();
        holder.tvPrice.setText(sessions.get(position).getPrice());
        holder.tvTime.setText(sessions.get(position).getTime());
        holder.tvVideoHallNum.setText(sessions.get(position).getVideoHallNum());
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.session,parent,false);
        ViewHodler viewHodler = new ViewHodler(view,activity);
        return viewHodler;
    }
}
