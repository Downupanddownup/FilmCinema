package com.example.hopjs.filmcinema.UI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hopjs.filmcinema.Common.ShowTool;
import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.Result;
import com.example.hopjs.filmcinema.Data.TicketInformation;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.Test.Test;
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
    private String sessionId;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg2){
                case Result.RESULT_OK:
                    Toast.makeText(getApplication(),
                            "购买成功",Toast.LENGTH_SHORT).show();
                    Transform.toPcenter(Confirm.this);
                    break;
                case Result.RESULT_NOT_NETWORK:
                    Toast.makeText(getApplication(),
                            "购买失败，请检查您的网络",Toast.LENGTH_SHORT).show();
                    break;
                case Result.RESULT_TICKET_BOUGHT:
                    Toast.makeText(getApplication(),
                            "购买失败，部分票已被购买",Toast.LENGTH_SHORT).show();
                    break;
                case Result.RESULT_TICKET_OUTDATE:
                    Toast.makeText(getApplication(),
                            "购买失败，该场次的票已停止销售",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
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
        //ticketInformation= Test.getTicketInformation();
        sessionId=getIntent().getStringExtra("sessionId");
        setData();
        llPayfor = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.payfor,null,false);
        btZhifubao = (Button)llPayfor.findViewById(R.id.bt_payfor_zhifubao);
        btWeixin = (Button)llPayfor.findViewById(R.id.bt_payfor_weixin);
        btWeixin.setOnClickListener(listener);
        btZhifubao.setOnClickListener(listener);

        tvTitle.setText("确认与支付");
    }

    private void setData(){
        tvFilmName.setText(ticketInformation.getFilmName());
        String tem= ShowTool.getUpcomingListFragmentDate(ticketInformation.getDate());
        tvDate.setText(tem);
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

        float price = 0;
        if(ticketInformation.getPrice() != null)
        {
            price = Float.parseFloat(ticketInformation.getPrice());
        }
        tvPrice.setText(price+"");
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_confirm_payfor:
                    dialogBuilder
                            .withTitle("支 付")
                            .withMessage("            请选择支付方式")
                            .withDialogColor(getResources().getColor(R.color.ButtomGuidBar))
                            .withDuration(500)
                            .withEffect(Effectstype.Fall)
                            .isCancelableOnTouchOutside(true)
                            .setCustomView(llPayfor,Confirm.this)
                            .show();
                    break;
                case R.id.bt_payfor_zhifubao:
                    sendBuyTicketPost();
                    dialogBuilder.dismiss();
                    break;
                case R.id.bt_payfor_weixin:
                    sendBuyTicketPost();
                    dialogBuilder.dismiss();
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
    private void sendBuyTicketPost(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                UserAccount userAccount=((MyApplication)getApplicationContext()).userAccount;
                ArrayList<Integer> list=new ArrayList<Integer>();
                for(SeatChoose.seat tem:ticketInformation.getTickets()){
                    list.add(tem.getSeatNum());
                }
                Time time=new Time("GTM");
                time.setToNow();
                String buyTime=""+time.year
                        +isOneOrTwo(time.month+1)
                        +isOneOrTwo(time.monthDay)
                        +isOneOrTwo((time.hour+8)%24+1)
                        +isOneOrTwo(time.minute);
                Result r= Connect.postBuyTicket(sessionId,
                        userAccount.getUserId(),
                        ticketInformation.getCinemaId(),
                        buyTime,
                        ticketInformation.getFilmId(),
                        list);
                Message message=Message.obtain();
                message.arg2=r.getCode();
                handler.sendMessage(message);
            }
        }.start();
    }
    private String isOneOrTwo(int d){
        String date=d+"";
        if(date.length()==1)return "0"+date;
        else return date;
    }

}
