package com.example.hopjs.filmcinema.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.TicketInformation;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hopjs on 2016/10/18.
 */

public class Confirm extends AppCompatActivity {

    TextView tvTitle;
    ImageView ivReturn,ivSearch;
    TextView tvFilmName,tvDate,tvCiname,tvVideoHall,tvTime,
            tvCommonTicketSum,tvCommonTicketNum,tvLoverTicketSum,
            tvLoverTicketNum,tvPrice;
    Button btPayfor;
    TicketInformation ticketInformation;

    private NiftyDialogBuilder dialogBuilder;
    private LinearLayout llPayfor;
    private Button btZhifubao,btWeixin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        tvTitle = (TextView)findViewById(R.id.tv_header_title);
        ivReturn = (ImageView)findViewById(R.id.iv_header_return);
        ivSearch  = (ImageView)findViewById(R.id.iv_header_search);
        tvFilmName = (TextView)findViewById(R.id.tv_confirm_film);
        tvDate = (TextView)findViewById(R.id.tv_confirm_date);
        tvCiname = (TextView)findViewById(R.id.tv_confirm_cinema);
        tvVideoHall = (TextView)findViewById(R.id.tv_confirm_videohallnum);
        tvTime = (TextView)findViewById(R.id.tv_confirm_time);
        tvCommonTicketSum = (TextView)findViewById(R.id.tv_confirm_commontic);
        tvCommonTicketNum = (TextView)findViewById(R.id.tv_confirm_tics);
        tvLoverTicketSum = (TextView)findViewById(R.id.tv_confirm_loversticsum);
        tvLoverTicketNum = (TextView)findViewById(R.id.tv_confirm_lovertics);
        tvPrice = (TextView)findViewById(R.id.tv_confirm_money);
        btPayfor = (Button)findViewById(R.id.bt_confirm_payfor);

        ivReturn.setOnClickListener(listener);
        ivSearch.setOnClickListener(listener);
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        btPayfor.setOnClickListener(listener);
        ticketInformation = ((MyApplication)this.getApplicationContext()).ticketInformation;
        setData();
        llPayfor = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.payfor,null,false);
        btZhifubao = (Button)llPayfor.findViewById(R.id.bt_payfor_zhifubao);
        btWeixin = (Button)llPayfor.findViewById(R.id.bt_payfor_weixin);
        btWeixin.setOnClickListener(listener);
        btZhifubao.setOnClickListener(listener);
    }

    private void setData(){
        tvFilmName.setText(ticketInformation.getFilmName());
        tvDate.setText(ticketInformation.getDate());
        tvCiname.setText(ticketInformation.getCinemaName());
        tvVideoHall.setText(ticketInformation.getVideoHallNum());
        tvTime.setText(ticketInformation.getTime());

        List<SeatChoose.seat> seats = ticketInformation.getTickets();

        int commonTicketSum = 0;
        int loversTicketSum = 0;
        String commonTickets = "";
        String loversTickets = "";

        for(int i=0; i< seats.size(); ++i){
            if(seats.get(i).getSeat_type()== SeatChoose.seat.ORDINARY_SEAT){
                commonTicketSum++;
                commonTickets += seats.get(i).getSeatNum()+"号  ";
            }else {
                loversTicketSum++;
                loversTickets += seats.get(i).getSeatNum()+"号  ";
            }
        }

        tvCommonTicketSum.setText(commonTicketSum+"");
        tvCommonTicketNum.setText(commonTickets);
        tvLoverTicketSum.setText(loversTicketSum+"");
        tvLoverTicketNum.setText(loversTickets);

        int price = 0;
        if(ticketInformation.getPrice() != null)
        {
            price = Integer.parseInt(ticketInformation.getPrice());
        }
        tvPrice.setText(price*ticketInformation.getTicketSum()+"");
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_confirm_payfor:
                    dialogBuilder
                            .withTitle("支 付")
                            .withMessage("            请选择支付方式")
                            .withDuration(500)
                            .withEffect(Effectstype.Fall)
                            .isCancelableOnTouchOutside(true)
                            .setCustomView(llPayfor,Confirm.this)
                            .show();
                    break;
                case R.id.bt_payfor_zhifubao:
                    Transform.toHomePage(Confirm.this);
                    break;
                case R.id.bt_payfor_weixin:
                    Transform.toHomePage(Confirm.this);
                    break;
                case R.id.iv_header_return:
                    finish();
                    break;
                case R.id.iv_header_search:
                    Transform.toSearch(Confirm.this);
                    break;
            }
        }
    };


}
