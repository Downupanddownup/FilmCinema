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

import com.example.hopjs.filmcinema.MyApplication;
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

public class CriticAdapter extends RecyclerView.Adapter<CriticAdapter.ViewHolder> {
    public static class Critic{
        private String id;
        private String name;
        private int portraitId;
        private int scord;
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

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPortraitId() {
            return portraitId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScord() {
            return scord;
        }

        public void setPortraitId(int portraitId) {
            this.portraitId = portraitId;
        }

        public void setScord(int scord) {
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
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivPortrait,ivPraise;
        private boolean isPraise;
        private TextView tvName,tvDate,tvPraise,tvContent;
        private RatingBar rbRating;
        private String id;
        private TextView tvLastItem;
        private OnItemClickListener itemClickListener;
        private OnLastItemClickListener lastItemClickListener;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_film0detail_critic_body_portrait:
                itemClickListener.onItemClick(v,id);
                break;
                case R.id.iv_film0detail_critic_body_praise:
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
                case R.id.tv_film0detail_critic_footer_more:
                lastItemClickListener.onLastItemClick(v);
                break;
            }

        }

        public ViewHolder(View itemView, int viewType, OnItemClickListener
                itemClickListener,OnLastItemClickListener lastItemClickListener) {
            super(itemView);
            if(viewType == TYPE_CRITIC){
                tvName = (TextView)itemView.findViewById
                        (R.id.tv_film0detail_critic_body_name);
                tvName.setTextColor(Color.GRAY);
                ivPortrait = (ImageView)itemView.findViewById
                        (R.id.iv_film0detail_critic_body_portrait);
                rbRating = (RatingBar)itemView.findViewById
                        (R.id.rb_film0detail_critic_body_rating);
                tvContent = (TextView) itemView.findViewById
                        (R.id.tv_film0detail_critic_body_content);
                tvContent.setTextColor(Color.GRAY);
                tvDate = (TextView) itemView.findViewById
                        (R.id.tv_film0detail_critic_body_date);
                tvDate.setTextColor(Color.GRAY);
                tvPraise = (TextView) itemView.findViewById
                        (R.id.tv_film0detail_critic_body_praise);
                tvPraise.setTextColor(Color.GRAY);
                ivPraise = (ImageView) itemView.findViewById
                        (R.id.iv_film0detail_critic_body_praise);

                this.itemClickListener = itemClickListener;
                ivPortrait.setOnClickListener(this);
                ivPraise.setOnClickListener(this);
            }else {
                tvLastItem = (TextView)itemView.findViewById
                        (R.id.tv_film0detail_critic_footer_more);
                tvLastItem.setTextColor(Color.GRAY);
                this.lastItemClickListener = lastItemClickListener;
                tvLastItem.setOnClickListener(this);
            }
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,String id);
    }
    public interface OnLastItemClickListener{
        public void onLastItemClick(View view);
    }

    private static final int TYPE_CRITIC = 1;
    private static final int TYPE_FOOTER = 2;
    private ArrayList<Critic> critics;
    private Context context;
    private OnItemClickListener itemClickListener;
    private OnLastItemClickListener lastItemClickListener;

    public CriticAdapter(Context context,ArrayList<Critic> critics, OnItemClickListener
            itemClickListener,OnLastItemClickListener lastItemClickListener) {
        this.context = context;
        this.critics = critics;
        this.itemClickListener = itemClickListener;
        this.lastItemClickListener = lastItemClickListener;
    }

    @Override
    public int getItemCount() {
        return critics.size()+1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position < getItemCount()-1){
            holder.id = critics.get(position).getId();
            holder.tvName.setText(critics.get(position).getName());
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
                    bitmapCache.getBitmap(critics.get(position).getPortraitId(),context,0.01);
            holder.ivPortrait.setImageBitmap(bitmap);
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
                    (R.layout.film0detail_critic_body,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate
                    (R.layout.film0detail_critic_footer,parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(view,viewType,
                itemClickListener,lastItemClickListener);
        return viewHolder;
    }
    public void add(ArrayList<Critic> critics){
        for(Critic critic:critics){
            this.critics.add(critic);
            notifyItemInserted(this.critics.size());
        }
    }
}
