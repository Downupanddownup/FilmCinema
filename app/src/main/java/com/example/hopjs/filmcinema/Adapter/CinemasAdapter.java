package com.example.hopjs.filmcinema.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.ShowTool;
import com.example.hopjs.filmcinema.Data.Cinema;
import com.example.hopjs.filmcinema.R;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class CinemasAdapter extends RecyclerView.Adapter<CinemasAdapter.ViewHolder> {
    public static final int TYPE_CINEMA = 1;
    public static final int TYPE_FOOTER = 2;
    private ArrayList<Cinema> cinemas;
    private Context context;
    private OnItemClickListener listener;
    private boolean isAll;

    public interface OnItemClickListener{
        public void onItemClick(View view,String id);
    }

    public CinemasAdapter(Context context,ArrayList<Cinema> cinemas,OnItemClickListener listener) {
        this.cinemas = cinemas;
        this.context = context;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name,lprice,address,distance;
        private String id;
        private OnItemClickListener listener;
        private TextView tvFooter;

        @Override
        public void onClick(View v) {
            listener.onItemClick(v,id);
        }

        public ViewHolder(View itemView, OnItemClickListener listener, int type) {
            super(itemView);
            if(type == CinemasAdapter.TYPE_CINEMA){
                this.listener = listener;
                itemView.setOnClickListener(this);
                name = (TextView)itemView.findViewById(R.id.tv_fragment_cinemas_item_name);
                lprice = (TextView)itemView.findViewById(R.id.tv_fragment_cinemas_item_lprice);
                address = (TextView)itemView.findViewById(R.id.tv_fragment_cinemas_item_address);
                distance = (TextView)itemView.findViewById(R.id.tv_fragment_cinemas_item_distance);
            }else {
                tvFooter=(TextView)itemView.findViewById(R.id.tv_fragment_film0list_footer_tips);
            }
        }
    }

    public void setIsAll(boolean isAll){
        this.isAll=isAll;
        if(isAll)notifyItemChanged(getItemCount()-1);
    }

    public void add(Cinema cinema){
        cinemas.add(cinema);
        notifyItemInserted(cinemas.size());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position+1 != getItemCount()){
            String tem;
            holder.id = cinemas.get(position).getId();
            holder.name.setText(cinemas.get(position).getName());
            tem="￥"+cinemas.get(position).getlPrice()+"起步";
            holder.lprice.setText(tem);
            String address=cinemas.get(position).getAddress();
            holder.address.setText(getAddress(address));
            tem= ShowTool.showDistance(cinemas.get(position).getDistance());
            holder.distance.setText(tem);
        }else {
            if(isAll)holder.tvFooter.setText("没有更多了");
            else holder.tvFooter.setText("正在加载...");
        }
    }

    private String getAddress(String address){
        String ad="";
        for(int i=0;i<15&&i<address.length();++i){
            ad+=address.charAt(i);
        }
        if(address.length()>15){
            ad+="...";
        }
        return ad;
    }
    @Override
    public int getItemCount() {
        return cinemas.size()+1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
       if(viewType == TYPE_CINEMA){
           view = LayoutInflater.from(context).inflate
                   (R.layout.fragment_cinemas_item,parent,false);
       }else {
           view = LayoutInflater.from(context).inflate
                   (R.layout.fragment_film0list_footer,parent,false);
       }
        ViewHolder viewHolder = new ViewHolder(view,listener,viewType);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position+1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_CINEMA;
        }
    }
}
