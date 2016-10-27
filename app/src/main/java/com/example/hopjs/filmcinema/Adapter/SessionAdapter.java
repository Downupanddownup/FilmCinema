package com.example.hopjs.filmcinema.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.Transform;
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

        public ViewHodler(View itemView, final Activity activity,final String cinemaId) {
            super(itemView);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_session_price);
            tvVideoHallNum = (TextView) itemView.findViewById(R.id.tv_session_videonum);
            tvTime = (TextView) itemView.findViewById(R.id.tv_session_time);
            tvVideoHallNum.setTextColor(Color.GRAY);
            tvTime.setTextColor(Color.GRAY);
            btBuy = (Button) itemView.findViewById(R.id.tv_session_buy);
            btBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Transform.toSeatChoose(activity,filmId,cinemaId,sessionId);
                }
            });
        }

    }


    private ArrayList<CinemaDetail.Session> sessions;
    private Context context;
    private Activity activity;
    private String cinemaId;

    public SessionAdapter(Activity activity,ArrayList<CinemaDetail.Session> sessions,String cinemaId) {
        super();
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.sessions = sessions;
    }

    public void replaceSessions(ArrayList<CinemaDetail.Session> sessions){
        for(int i=this.sessions.size()-1;i>-1;--i){
            this.sessions.remove(i);
            notifyItemRemoved(i);
        }
        for(int i=0;i<sessions.size();++i){
            this.sessions.add(sessions.get(i));
            notifyItemInserted(i);


        }
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
        ViewHodler viewHodler = new ViewHodler(view,activity,cinemaId);
        return viewHodler;
    }
}
