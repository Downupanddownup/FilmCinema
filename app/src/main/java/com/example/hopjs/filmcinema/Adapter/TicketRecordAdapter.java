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

import com.example.hopjs.filmcinema.Common.ShowTool;
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
import com.example.hopjs.filmcinema.UI.TicketRecord;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/14.
 */

public class TicketRecordAdapter extends RecyclerView.Adapter<TicketRecordAdapter.ViewHolder> {
    public static class TicketRecord{
        private String filmName;
        private String filmId;
        private String cinemaName;
        private String date;
        private String videoHall;
        private String time;
        private ArrayList<Integer> tickets;
        private String buyTime;

        public void setFilmName(String filmName) {
            this.filmName = filmName;
        }

        public void setFilmId(String filmId) {
            this.filmId = filmId;
        }

        public String getFilmName() {
            return filmName;
        }

        public String getFilmId() {
            return filmId;
        }

        public ArrayList<Integer> getTickets() {
            return tickets;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public String getDate() {
            return date;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public String getVideoHall() {
            return videoHall;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBuyTime() {
            return buyTime;
        }

        public String getTime() {
            return time;
        }

        public void setBuyTime(String buyTime) {
            this.buyTime = buyTime;
        }

        public void setTickets(ArrayList<Integer> tickets) {
            this.tickets = tickets;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setVideoHall(String videoHall) {
            this.videoHall = videoHall;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvFilmName,tvDate,tvCinemName,tvVideoHall,tvTime
                ,tvTicketNum,tvTicket,tvBuyDate;
        private String filmId;
        private TextView tvLastItem;
        private OnItemClickListener itemClickListener;

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,filmId);
            }

        public ViewHolder(View itemView, int viewType, OnItemClickListener
                itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            if(viewType == TYPE_TICKET_RECORD){
                tvCinemName = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_item_cinema0name);
                tvFilmName = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_item_film0name);
                tvDate = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_item_date);
                tvVideoHall = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_item_video0hall);
                tvTime = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_item_time);
                tvTicketNum = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_item_ticket0num);
                tvTicket = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_item_ticket);
                tvBuyDate = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_item_buy0date);
                itemView.setOnClickListener(this);
            }else {
                tvLastItem = (TextView)itemView.findViewById
                        (R.id.tv_ticket0record_footer_more);
                tvLastItem.setTextColor(Color.GRAY);
            }
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,String filmId);
    }

    private static final int TYPE_TICKET_RECORD = 1;
    private static final int TYPE_FOOTER = 2;
    private ArrayList<TicketRecord> ticketRecords;
    private Context context;
    private OnItemClickListener itemClickListener;
    private boolean isAll;

    public TicketRecordAdapter(Context context,ArrayList<TicketRecord> ticketRecords, OnItemClickListener
            itemClickListener) {
        this.context = context;
        this.ticketRecords = ticketRecords;
        this.itemClickListener = itemClickListener;
    }

    public void setAll(boolean all) {
        isAll = all;
        if(isAll)notifyItemChanged(getItemCount()-1);
    }

    @Override
    public int getItemCount() {
        return ticketRecords.size()+1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position < getItemCount()-1){
            holder.filmId = ticketRecords.get(position).getFilmId();
            holder.tvFilmName.setText(ticketRecords.get(position).getFilmName());
            holder.tvCinemName.setText(ticketRecords.get(position).getCinemaName());
            String tem= ShowTool.getUpcomingDate(ticketRecords.get(position).getDate());
            holder.tvDate.setText(tem+"放映");
            tem=ticketRecords.get(position).getVideoHall()+"号放映厅";
            holder.tvVideoHall.setText(tem);
            tem=ShowTool.sessionStartTime(ticketRecords.get(position).getTime());
            holder.tvTime.setText(tem+"开始");
            holder.tvTicketNum.setText("共"+ticketRecords.get(position).getTickets().size()+"张票：");
            String tickets = "";
            for(int i:ticketRecords.get(position).getTickets()) {
                tickets += i+"号 ";
            }
            holder.tvTicket.setText(tickets);
            tem=ShowTool.showCriticTime(ticketRecords.get(position).getBuyTime());
            holder.tvBuyDate.setText("于"+tem+"购买");
        }else {
            if(isAll)holder.tvLastItem.setText("没有更多了");
            else holder.tvLastItem.setText("正在加载");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < getItemCount()-1){
            return TYPE_TICKET_RECORD;
        }else {
            return TYPE_FOOTER;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_TICKET_RECORD){
            view = LayoutInflater.from(context).inflate
                    (R.layout.ticket0record_item,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate
                    (R.layout.ticket0record_footer,parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(view,viewType,
                itemClickListener);
        return viewHolder;
    }
    public void add(ArrayList<TicketRecord> ticketRecords){
        for(TicketRecord ticketRecord:ticketRecords){
            this.ticketRecords.add(ticketRecord);
            notifyItemInserted(this.ticketRecords.size());
        }
    }
}
