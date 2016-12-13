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

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    public static class Collection{
        private String name;
        private String id;
        private int posterId;
        private String scord;
        private String type;
        private String director;
        private String time;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPosterId() {
            return posterId;
        }

        public String getDirector() {
            return director;
        }

        public void setPosterId(int posterId) {
            this.posterId = posterId;
        }

        public String getScord() {
            return scord;
        }

        public String getType() {
            return type;
        }

        public void setScord(String scord) {
            this.scord = scord;
        }

        public String getTime() {
            return time;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivPoster;
        private TextView tvName,tvScord,tvType,tvDirector,tvTime;
        private String id;
        private TextView tvLastItem;
        private OnItemClickListener itemClickListener;
        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,id);
        }

        public ViewHolder(View itemView, int viewType, OnItemClickListener itemClickListener) {
            super(itemView);
            if(viewType == TYPE_COLLECTION){
                tvName = (TextView)itemView.findViewById
                        (R.id.tv_collection_name);
                tvName.setTextColor(Color.GRAY);
                ivPoster = (ImageView)itemView.findViewById
                        (R.id.iv_collection_poster);
                tvScord = (TextView) itemView.findViewById
                        (R.id.tv_collection_scord);
                tvScord.setTextColor(Color.GRAY);
                tvTime = (TextView) itemView.findViewById
                        (R.id.tv_collection_time);
                tvTime.setTextColor(Color.GRAY);
                tvType = (TextView) itemView.findViewById
                        (R.id.tv_collection_type);
                tvType.setTextColor(Color.GRAY);
                tvDirector = (TextView) itemView.findViewById
                        (R.id.tv_collection_director);
                tvDirector.setTextColor(Color.GRAY);
                this.itemClickListener = itemClickListener;
                itemView.setOnClickListener(this);
            }else {
                tvLastItem = (TextView)itemView.findViewById
                        (R.id.tv_collection_more);
                tvLastItem.setTextColor(Color.GRAY);
            }
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,String id);
    }

    private static final int TYPE_COLLECTION = 1;
    private static final int TYPE_FOOTER = 2;
    private ArrayList<Collection> collections;
    private Context context;
    private OnItemClickListener itemClickListener;

    public CollectionAdapter(Context context,ArrayList<Collection> collections,
                             OnItemClickListener itemClickListener) {
        this.context = context;
        this.collections = collections;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return collections.size()+1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position < getItemCount()-1){
            holder.id = collections.get(position).getId();
            holder.tvName.setText(collections.get(position).getName());
            holder.tvDirector.setText(collections.get(position).getDirector());
            holder.tvScord.setText(collections.get(position).getScord());
            holder.tvTime.setText(collections.get(position).getTime());
            holder.tvType.setText(collections.get(position).getType());
            /*Bitmap bitmap = ((MyApplication)context.getApplicationContext()).
                    bitmapCache.getBitmap(collections.get(position).getPosterId(),context,0.3);
            holder.ivPoster.setImageBitmap(bitmap);*/
            Connect.TemUrl temUrl = new Connect.TemUrl();
            temUrl.setConnectionType(Connect.NETWORK_FILM_PICTURE);
            temUrl.addHeader("filmId",collections.get(position).getId());
            Glide.with(context)
                    .load(temUrl.getSurl())
                    .placeholder(R.drawable.x)
                    .error(R.drawable.w)
                    .into(holder.ivPoster);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < getItemCount()-1){
            return TYPE_COLLECTION;
        }else {
            return TYPE_FOOTER;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_COLLECTION){
            view = LayoutInflater.from(context).inflate
                    (R.layout.collection_item,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate
                    (R.layout.collection_footer,parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(view,viewType,
                itemClickListener);
        return viewHolder;
    }
    public void add(ArrayList<Collection> collections){
        for(Collection collection:collections){
            this.collections.add(collection);
            notifyItemInserted(this.collections.size());
        }
    }
}
