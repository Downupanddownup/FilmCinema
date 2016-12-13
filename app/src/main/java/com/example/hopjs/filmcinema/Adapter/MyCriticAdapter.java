package com.example.hopjs.filmcinema.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/14.
 */

public class MyCriticAdapter extends RecyclerView.Adapter<MyCriticAdapter.ViewHolder> {
    public static class Critic{
        private String filmId;
        private String filmName;
        private float scord;
        private String date;
        private String praise;
        private String content;
        private boolean isPraise;

        public void setPraise(boolean praise) {
            isPraise = praise;
        }

        public boolean isPraise() {
            return isPraise;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public float getScord() {
            return scord;
        }

        public void setScord(float scord) {
            this.scord = scord;
        }

        public String getDate() {
            return date;
        }

        public String getPraise() {
            return praise;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public String getFilmId() {
            return filmId;
        }

        public String getFilmName() {
            return filmName;
        }

        public void setFilmId(String filmId) {
            this.filmId = filmId;
        }

        public void setFilmName(String filmName) {
            this.filmName = filmName;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivPortrait,ivPraise;
        private boolean isPraise;
        private TextView tvName,tvDate,tvPraise,tvContent,tvFilmName;
        private RatingBar rbRating;
        private String filmId;
        private TextView tvLastItem;
        private OnItemClickListener itemClickListener;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_mycritic_item_content:
                    itemClickListener.onItemClick(v,filmId);
                    break;
                case R.id.tv_mycritic_item_film:
                    itemClickListener.onItemClick(v,filmId);
                    break;
                case R.id.iv_mycritic_item_praise:
                    if(isPraise){
                        isPraise = false;
                        //sendtoServer
                        String num = tvPraise.getText().toString();
                        tvPraise.setText(Integer.parseInt(num)-1+"");
                        ivPraise.setImageResource(R.drawable.notpraise);
                    }else {
                        isPraise = true;
                        //sendtoServer
                        String num = tvPraise.getText().toString();
                        tvPraise.setText(Integer.parseInt(num)+1+"");
                        ivPraise.setImageResource(R.drawable.haspraise);
                    }
                    break;
            }

        }

        public ViewHolder(View itemView, int viewType, OnItemClickListener
                itemClickListener) {
            super(itemView);
            if(viewType == TYPE_CRITIC){
                tvFilmName = (TextView)itemView.findViewById
                        (R.id.tv_mycritic_item_film);
                tvName = (TextView)itemView.findViewById
                        (R.id.tv_mycritic_item_name);
                tvName.setTextColor(Color.GRAY);
                ivPortrait = (ImageView)itemView.findViewById
                        (R.id.iv_mycritic_item_portrait);
                rbRating = (RatingBar)itemView.findViewById
                        (R.id.rb_mycritic_item_rating);
                tvContent = (TextView) itemView.findViewById
                        (R.id.tv_mycritic_item_content);
                tvContent.setTextColor(Color.GRAY);
                tvDate = (TextView) itemView.findViewById
                        (R.id.tv_mycritic_item_date);
                tvDate.setTextColor(Color.GRAY);
                tvPraise = (TextView) itemView.findViewById
                        (R.id.tv_mycritic_item_praise);
                tvPraise.setTextColor(Color.GRAY);
                ivPraise = (ImageView) itemView.findViewById
                        (R.id.iv_mycritic_item_praise);

                this.itemClickListener = itemClickListener;
                tvFilmName.setOnClickListener(this);
                tvContent.setOnClickListener(this);
                ivPraise.setOnClickListener(this);
            }else {
                tvLastItem = (TextView)itemView.findViewById
                        (R.id.tv_mycritic_footer_more);
                tvLastItem.setTextColor(Color.GRAY);
            }
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,String filmId);
    }

    private static final int TYPE_CRITIC = 1;
    private static final int TYPE_FOOTER = 2;
    private ArrayList<Critic> critics;
    private String userName;
    private int portraitId;
    private Context context;
    private OnItemClickListener itemClickListener;

    public MyCriticAdapter(Context context,ArrayList<Critic> critics, String userName,
                           int portraitId,OnItemClickListener itemClickListener) {
        this.context = context;
        this.critics = critics;
        this.userName = userName;
        this.portraitId = portraitId;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return critics.size()+1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position < getItemCount()-1){
            holder.filmId = critics.get(position).getFilmId();
            holder.tvFilmName.setText(critics.get(position).getFilmName());
            holder.tvName.setText(userName);
            holder.tvPraise.setText(critics.get(position).getPraise());
            holder.tvDate.setText(critics.get(position).getDate());
            holder.tvContent.setText(critics.get(position).getContent());
            holder.rbRating.setRating(critics.get(position).getScord());
            holder.isPraise = critics.get(position).isPraise;
            if(critics.get(position).isPraise){
                holder.ivPraise.setImageResource(R.drawable.haspraise);
            }else {
                holder.ivPraise.setImageResource(R.drawable.notpraise);
            }
            Bitmap bitmap = ((MyApplication)context.getApplicationContext()).
                    bitmapCache.getBitmap(portraitId,context,0.01);
            holder.ivPortrait.setImageBitmap(bitmap);
            /*Connect.TemUrl temUrl = new Connect.TemUrl();
            temUrl.setConnectionType(Connect.NETWORK_FILM_PICTURE);
            temUrl.addHeader("filmId",critics.get(position).getId());
            Glide.with(context)
                    .load(temUrl.getSurl())
                    .placeholder(R.drawable.x)
                    .error(R.drawable.w)
                    .into(holder.ivPortrait);*/
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < getItemCount()-1){
            return TYPE_CRITIC;
        }else {
            return TYPE_FOOTER;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_CRITIC){
            view = LayoutInflater.from(context).inflate
                    (R.layout.mycritic_item,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate
                    (R.layout.mycritic_footer,parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(view,viewType,
                itemClickListener);
        return viewHolder;
    }
    public void add(ArrayList<Critic> critics){
        for(Critic critic:critics){
            this.critics.add(critic);
            notifyItemInserted(this.critics.size());
        }
    }
}
