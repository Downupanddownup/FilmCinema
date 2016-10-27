package com.example.hopjs.filmcinema.Adapter;

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

public class WorkersAdapter extends RecyclerView.Adapter<WorkersAdapter.ViewHolder> {
    public static class Director{
        private String id;
        private String name;
        private int portraitId;

        public void setPortraitId(int portraitId) {
            this.portraitId = portraitId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPortraitId() {
            return portraitId;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
    public static class Actors{
        private String id;
        private String name;
        private int portraitId;
        private String role;

        public String getId() {
            return id;
        }

        public int getPortraitId() {
            return portraitId;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPortraitId(int portraitId) {
            this.portraitId = portraitId;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivPortrait;
        private TextView tvName,tvRoleorD;
        private String id;
        private OnItemClickListener listener;
        @Override
        public void onClick(View v) {
            listener.onItemClick(v,id);
        }

        public ViewHolder(View itemView, int viewType, OnItemClickListener listener) {
            super(itemView);

            if(viewType == TYPE_DIRECTOR){
                tvName = (TextView)itemView.findViewById
                        (R.id.tv_film0detail_minfor_director_name);
                ivPortrait = (ImageView)itemView.findViewById
                        (R.id.iv_film0detail_minfor_director_portrait);
                this.listener = listener;
                itemView.setOnClickListener(this);
            }else if(viewType == TYPE_ACTOR){
                tvName = (TextView)itemView.findViewById
                        (R.id.tv_film0detail_minfor_actor_name);
                tvRoleorD = (TextView)itemView.findViewById
                        (R.id.tv_film0detail_minfor_actor_role);
                ivPortrait = (ImageView)itemView.findViewById
                        (R.id.iv_film0detail_minfor_actor_portrait);
                this.listener = listener;
                itemView.setOnClickListener(this);
            }
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,String id);
    }

    private static final int TYPE_DIRECTOR = 1;
    private static final int TYPE_ACTOR = 2;
    private static final int TYPE_FOOTER = 3;
    private Director director;
    private ArrayList<Actors> actors;
    private Context context;
    private OnItemClickListener listener;

    public WorkersAdapter(Context context,Director director,
                          ArrayList<Actors> actors,OnItemClickListener listener) {
        this.context = context;
        this.director = director;
        this.actors = actors;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return actors.size()+2;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position == 0){
            holder.id = director.getId();
            holder.tvName.setText(director.getName());
            Bitmap bitmap = ((MyApplication)context.getApplicationContext()).
                    bitmapCache.getBitmap(director.getPortraitId(),context,0.2);
            holder.ivPortrait.setImageBitmap(bitmap);
        }else if(position < getItemCount()-1){
            holder.id = actors.get(position-1).getId();
            holder.tvName.setText(actors.get(position-1).getName());
            holder.tvRoleorD.setText(actors.get(position-1).getRole());
            Bitmap bitmap = ((MyApplication)context.getApplicationContext()).
                    bitmapCache.getBitmap(actors.get(position-1).getPortraitId(),context,0.05);
            holder.ivPortrait.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_DIRECTOR;
        }else if(position == getItemCount()-1){
            return TYPE_FOOTER;
        }else {
            return TYPE_ACTOR;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_DIRECTOR){
            view = LayoutInflater.from(context).inflate
                    (R.layout.film0detail_minfor_director,parent,false);
        }else if(viewType == TYPE_FOOTER){
            view = LayoutInflater.from(context).inflate
                    (R.layout.film0detail_minfor_footer,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate
                    (R.layout.film0detail_minfor_actor,parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(view,viewType,listener);
        return viewHolder;
    }
    public void add(ArrayList<Actors> actors){
        for(Actors actors1:actors){
            this.actors.add(actors1);
            notifyItemInserted(this.actors.size());
        }
    }
}
