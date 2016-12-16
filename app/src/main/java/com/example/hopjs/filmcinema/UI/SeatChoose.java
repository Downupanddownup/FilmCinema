package com.example.hopjs.filmcinema.UI;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Hopjs on 2016/10/27.
 */

public class SeatChoose extends AppCompatActivity {
    TextView tvTitle;
    ImageView imReturn,imSearch;
    ListView lvRow;
    GridView gvSeat;
    Button btPart,btAll;
    TextView tvPicNum;
    Button btNext;

    CinemaSeat seats;
    GridViewSeatAdapter gvsAdapter;
    ListViewSeatRowAdapter lvsrAdapter;
    Handler handler;
    private String cinemaId;
    private String sessionId;
    private String filmId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat0choose);

        tvTitle = (TextView)findViewById(R.id.tv_header_title);
        imReturn = (ImageView)findViewById(R.id.iv_header_return);
        imSearch = (ImageView)findViewById(R.id.iv_header_search);
        lvRow = (ListView)findViewById(R.id.lv_seat0choose_row);
        gvSeat = (GridView)findViewById(R.id.gv_seat0choose_seat);
        btPart = (Button)findViewById(R.id.bt_seat0choose_part);
        btAll = (Button)findViewById(R.id.bt_seat0choose_all);
        tvPicNum = (TextView)findViewById(R.id.tv_seat0choose_picnum);
        btNext = (Button)findViewById(R.id.bt_seat0choose_next);

        imReturn.setOnClickListener(listener);
        imSearch.setOnClickListener(listener);
        btPart.setOnClickListener(listener);
        btAll.setOnClickListener(listener);
        btNext.setOnClickListener(listener);

        tvTitle.setText("影 院 选 座");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 1) {
                    setPartSeatData();
                }else {
                    tvPicNum.setText(msg.arg2+"");
                }
            }
        };

        cinemaId = getIntent().getStringExtra("cinemaId");
        sessionId = getIntent().getStringExtra("sessionId");
        filmId = getIntent().getStringExtra("filmId");
        loadSeatData();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_header_return:
                    finish();
                    break;
                case R.id.iv_header_search:
                    Transform.toSearch(SeatChoose.this);
                    break;
                case R.id.bt_seat0choose_part:
                    setPartSeatData();
                    break;
                case R.id.bt_seat0choose_all:
                    setAllSeatData();
                    break;
                case R.id.bt_seat0choose_next:
                    Transform.toConfirm(SeatChoose.this);
                    break;
            }
        }
    };
    private void loadSeatData(){
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.arg1 = 1;
                /*seats = new CinemaSeat();
                seats.setCoulmns(40);
                seats.setRow(10);
                List<seat> tem = new ArrayList<seat>();
                for(int i =0;i<400;++i)tem.add(getSeat());
                seats.setSeatList(tem);*/
                seats = Connect.getSeat(cinemaId,filmId,sessionId);
                setListViewOnTouchAndScrollListener(lvRow,gvSeat,seats.getCoulmns());
                handler.sendMessage(msg);
            }
        }.start();
    }

    private seat getSeat(){
        seat s = new seat();
        Random random = new Random();
        //  s.setSeat_type(random.nextInt()%3);
        //  s.setSeat_state(random.nextInt()%3+3);
        s.setSeat_type(1);
        s.setSeat_state(4);
        return s;
    }

    private void setPartSeatData(){
        gvSeat.setNumColumns(seats.getCoulmns());
        int width = dip2px(20);
        int space = 4;//列间距
        int height = dip2px(20);

        ViewGroup.LayoutParams params = gvSeat.getLayoutParams();
        int allwith = width*seats.getCoulmns()+space*(seats.getCoulmns()-1);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        if(allwith > metric.widthPixels*0.9){
            params.width = allwith;
        }else {
            params.width = (int)(metric.widthPixels*0.9);
            width = ((int)(metric.widthPixels*0.9)-space*(seats.getCoulmns()-1))/seats.getCoulmns();
        }

        gvSeat.setColumnWidth(width);

        gvSeat.setLayoutParams(params);
        gvsAdapter = new GridViewSeatAdapter(this,seats.getSeatList(),width,height,handler);
        gvSeat.setAdapter(gvsAdapter);
        loadSeatRow(height+dip2px(1));
    }
    private void loadSeatRow(int height){
        List<String> rowList = new ArrayList<String>();
        for(int i=1;i<seats.getRow()+1;++i)rowList.add(i+"");
        lvsrAdapter = new ListViewSeatRowAdapter(this,rowList,height);
        lvRow.setAdapter(lvsrAdapter);
    }
    private void setAllSeatData(){
        gvSeat.setNumColumns(seats.getCoulmns());
        int width = 0;
        int space = 4;
        int height = 0;

        ViewGroup.LayoutParams params = gvSeat.getLayoutParams();

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        params.width = (int)(metric.widthPixels*0.9);
        width = ((int)(metric.widthPixels*0.9)-space*(seats.getCoulmns()-1))/seats.getCoulmns();
        height = (dip2px(200)-dip2px(1)*(seats.getRow()-1))/seats.getRow();

        gvSeat.setColumnWidth(width);

        gvSeat.setLayoutParams(params);
        gvsAdapter = new GridViewSeatAdapter(this,seats.getSeatList(),width,height,handler);
        gvSeat.setAdapter(gvsAdapter);
        loadSeatRow(height+dip2px(1));
    }

    private void setListViewOnTouchAndScrollListener(
            final ListView listView, final GridView gridview,final int coulmns) {


        //设置listview2列表的scroll监听，用于滑动过程中左右不同步时校正
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //如果停止滑动
                if (scrollState == 0 || scrollState == 1) {
                    //获得第一个子view
                    View subView = view.getChildAt(0);
                    Log.i("hhh","gridviewheight:"+subView.getHeight());

                    if (subView != null) {
                        final int top = subView.getTop();
                        final int top1 = listView.getChildAt(0).getTop();
                        final int position = view.getFirstVisiblePosition();

                        //如果两个首个显示的子view高度不等
                        if (top != top1) {
                            listView.setSelectionFromTop(position/coulmns, top);
                            // listView2.setSelectionFromTop(position,top);
                            // listView2.smoothScrollToPosition(position);
                        }
                    }
                }

            }
            @Override
            public void onScroll(AbsListView view, final int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                View subView = view.getChildAt(0);
                if (subView != null) {
                    final int top = subView.getTop();
                    Log.i("buyticket","first"+firstVisibleItem);
//      //如果两个首个显示的子view高度不等
                    int top1 = listView.getChildAt(0).getTop();
                    if (top1 != top) {
                        listView.setSelectionFromTop(firstVisibleItem/coulmns, top);
                        //listView2.setSelectionFromTop(firstVisibleItem, top);
                    }

                }
            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public  int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static class seat {
        public final static int ORDINARY_SEAT = 1;
        public final static int LOVERS_SEAT = 2;
        public final static int NONE_SEAT = 3;

        public final static int STATE_EMPTY_SEAT = 4;
        public final static int STATE_CHOSED_SEAT = 5;
        public final static int STATE_CHOOSING_SEAT =6;

        private int seat_type;
        private int seat_state;
        private int seatNum;

        public seat() {
            super();
            seat_type = NONE_SEAT;
            seat_state = STATE_EMPTY_SEAT;
        }

        public void setSeat_type(int type){seat_type = type;}
        public int getSeat_type(){return seat_type;}
        public void setSeat_state(int state){seat_state = state;}
        public int getSeat_state(){return seat_state;}
        public void setSeatNum(int seatNum) {
            this.seatNum = seatNum;
        }
        public int getSeatNum() {
            return seatNum;
        }

    }

    public static class CinemaSeat {
        private List<seat> seatList;
        private int coulmns;
        private int row;

        public CinemaSeat() {
            super();
            coulmns = 0;
            row = 0;
        }

        public List<seat> getSeatList(){return seatList;}
        public void setSeatList(List<seat> list){seatList = list;}
        public int getCoulmns(){return coulmns;}
        public void setCoulmns(int coulmns){this.coulmns = coulmns;}
        public int getRow(){return row;}
        public void setRow(int row){this.row = row;}
    }

    public class GridViewSeatAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<seat> data;
        private int width;
        private int height;
        private int picNum;
        private Handler handler;
        private Message message;
        private Context context;
        public GridViewSeatAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }
        public GridViewSeatAdapter(Context context,List<seat> seats,int width,int height,Handler handler) {
            mInflater = LayoutInflater.from(context);
            data = seats;
            this.context = context;
            this.width = width;
            this.height = height;
            this.handler = handler;

        }

        // TODO getView
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //创建一个listView的item元素
            ImageView im = null;
            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.seat0choose_griditem,
                        parent,false);
                im = (ImageView)convertView.findViewById(R.id.tv_seat0choose_griditem);

                ViewGroup.LayoutParams params = im.getLayoutParams();
                params.width = width;
                params.height = height;
                im.setLayoutParams(params);

                im.setScaleType(ImageView.ScaleType.FIT_CENTER);
                convertView.setTag(im);
            } else {
                im = (ImageView) convertView.getTag();
            }

            switch (data.get(position).getSeat_state()){
                case seat.STATE_EMPTY_SEAT:
                    if(data.get(position).getSeat_type() == seat.ORDINARY_SEAT){
                        // im.setImageResource(R.drawable.empty_ordinary_seat);
                        im.setImageResource(R.drawable.seat_empty);
                    }
                    else if(data.get(position).getSeat_type() == seat.LOVERS_SEAT){
                        //im.setImageResource(R.drawable.empty_lovers_seat);
                        im.setImageResource(R.drawable.loverseat_empty);
                    }else {
                        //  im.setImageResource(R.drawable.pic2);
                    }
                    break;
                case seat.STATE_CHOOSING_SEAT:
                    if(data.get(position).getSeat_type() == seat.ORDINARY_SEAT){
                        // im.setImageResource(R.drawable.choosing_ordinary_seat);
                        im.setImageResource(R.drawable.seat_choosing);
                    }
                    else if(data.get(position).getSeat_type() == seat.LOVERS_SEAT){
                        // im.setImageResource(R.drawable.choosing_lovers_seat);
                        im.setImageResource(R.drawable.loverseat_choosing);
                    }else {
                        //  im.setImageResource(R.drawable.film);
                    }
                    break;
                case seat.STATE_CHOSED_SEAT:
                    if(data.get(position).getSeat_type() == seat.ORDINARY_SEAT){
                        // im.setImageResource(R.drawable.chosed_ordinary_seat);
                        im.setImageResource(R.drawable.seat_choosed);
                    }
                    else if(data.get(position).getSeat_type() == seat.LOVERS_SEAT){
                        //im.setImageResource(R.drawable.chosed_lovers_seat);
                        im.setImageResource(R.drawable.loverseat_choosed);
                    }else {
                        //  im.setImageResource(R.drawable.filmstart);
                    }
                    break;
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imm = (ImageView)v.findViewById(R.id.tv_seat0choose_griditem);
                    if(data.get(position).getSeat_type() != seat.NONE_SEAT){
                        if(data.get(position).getSeat_state() != seat.STATE_CHOSED_SEAT){
                            if(data.get(position).getSeat_type() == seat.ORDINARY_SEAT) {
                                if (data.get(position).getSeat_state() == seat.STATE_EMPTY_SEAT) {
                                    data.get(position).setSeat_state(seat.STATE_CHOOSING_SEAT);
                                    imm.setImageResource(R.drawable.seat_choosing);
                                    picNum++;
                                } else {
                                    data.get(position).setSeat_state(seat.STATE_EMPTY_SEAT);
                                    imm.setImageResource(R.drawable.seat_empty);
                                    picNum--;
                                }
                            }else {
                                if (data.get(position).getSeat_state() == seat.STATE_EMPTY_SEAT) {
                                    data.get(position).setSeat_state(seat.STATE_CHOOSING_SEAT);
                                    imm.setImageResource(R.drawable.loverseat_choosing);
                                    picNum += 2;
                                } else {
                                    data.get(position).setSeat_state(seat.STATE_EMPTY_SEAT);
                                    imm.setImageResource(R.drawable.loverseat_empty);
                                    picNum -= 2;
                                }
                            }
                            message = new Message();
                            message.arg1 = 2;
                            message.arg2 = picNum;
                            handler.sendMessage(message);
                        }
                    }
                }
            });


            return convertView;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        //获取对应位置的item
        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    public class ListViewSeatRowAdapter extends BaseAdapter {
        private List<String> data;
        private Context context;
        private int height;
        private LayoutInflater layoutInflater;
        public ListViewSeatRowAdapter(Context context,List<String> list,int height){
            layoutInflater = LayoutInflater.from(context);
            this.context = context;
            this.height = height;
            data = list;
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = null;
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.seat0choose_listitem
                                ,parent,false);
                tv = (TextView) convertView.findViewById(R.id.tv_seat0choose_listitem);
                ViewGroup.LayoutParams parsms = tv.getLayoutParams();
                parsms.height = height;
                tv.setLayoutParams(parsms);
                tv.setGravity(Gravity.CENTER);

                convertView.setTag(tv);
            }else {
                tv = (TextView)convertView.getTag();
            }
            tv.setText(data.get(position));
            return convertView;
        }
    }
}
