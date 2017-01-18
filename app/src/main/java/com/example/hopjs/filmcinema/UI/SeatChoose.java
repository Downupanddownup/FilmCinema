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
import android.widget.Toast;

import com.example.hopjs.filmcinema.Common.Transform;
import com.example.hopjs.filmcinema.Data.TicketInformation;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.MyApplication;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.UI.Fragment.SidebarFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Hopjs on 2016/10/27.
 */

public class SeatChoose extends AppCompatActivity {
    private static final int LOAD_SEATDATE=1;
    private static final int SEAT_STATE_CHANGED=2;
    private static final int LOGIN=3;
    private static final int ADDSEAT=4;
    private static final int REMOVESEAT=5;
    TextView tvTitle;
    ImageView imReturn,imSearch;
    ListView lvRow;
    GridView gvSeat;
    Button btPart,btAll;
    TextView tvPicSum,tvPrice;
    Button btNext;
    private SidebarFragment sidebarFragment;
    private int picSum=0;

    CinemaSeat seats;
    private ArrayList<seat> readyBuyList;
    GridViewSeatAdapter gvsAdapter=null;
    ListViewSeatRowAdapter lvsrAdapter;
    Handler handler;
    private String cinemaId;
    private String sessionId;
    private String filmId;
    private float price;

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
        tvPicSum = (TextView)findViewById(R.id.tv_seat0choose_picnum);
        btNext = (Button)findViewById(R.id.bt_seat0choose_next);
        sidebarFragment = (SidebarFragment)getSupportFragmentManager().
                findFragmentById(R.id.f_seat0choose_sidebar);
        tvPrice = (TextView)findViewById(R.id.tv_seat0choose_price);

        imReturn.setOnClickListener(listener);
        imSearch.setOnClickListener(listener);
        btPart.setOnClickListener(listener);
        btAll.setOnClickListener(listener);
        btNext.setOnClickListener(listener);

        tvTitle.setText("影 院 选 座");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.arg1){
                    case LOAD_SEATDATE:
                        setPartSeatData();
                        break;
                    case SEAT_STATE_CHANGED:
                        tvPicSum.setText(msg.arg2+"");
                        picSum=Integer.parseInt(tvPicSum.getText().toString());
                        String tem=picSum*price+"";
                        tvPrice.setText("总票价："+tem);
                        break;
                    case LOGIN:
                        sidebarFragment.loadUserInfor();
                        break;
                }
            }
        };
        readyBuyList=new ArrayList<>();
        cinemaId = getIntent().getStringExtra("cinemaId");
        sessionId = getIntent().getStringExtra("sessionId");
        filmId = getIntent().getStringExtra("filmId");
        price = Float.parseFloat(getIntent().getStringExtra("price"));
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
                    if(picSum==0) {
                        Toast.makeText(getApplicationContext(),
                                "请选择座位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    UserAccount userAccount = ((MyApplication)getApplicationContext()).userAccount;
                    if(userAccount.isLogin()) {
                        TicketInformation ticketInformation=((MyApplication)getApplicationContext()).ticketInformation;
                      //  ticketInformation.setTickets();
                        ticketInformation.setTickets(getTickets());
                        ticketInformation.setPrice(price*picSum+"");
                        Transform.toConfirm(SeatChoose.this,sessionId);
                    }else {
                        Login login = new Login(SeatChoose.this,handler,LOGIN);
                        login.show();
                    }
                    break;
            }
        }
    };
    private ArrayList<seat> getTickets(){
        if(gvsAdapter==null)return new ArrayList<>();
        ArrayList<seat> data=new ArrayList<>();
        for(seat tem:gvsAdapter.getData()){
            if(tem.getSeat_state()==seat.STATE_CHOOSING_SEAT){
                data.add(tem);
            }
        }
        return data;
    }
    private void loadSeatData(){
        new Thread(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.arg1 = LOAD_SEATDATE;
                seats = new CinemaSeat();

                seats = Connect.getSeat(cinemaId,filmId,sessionId);
                setListViewOnTouchAndScrollListener(lvRow,gvSeat,seats.getCoulmns());
                handler.sendMessage(msg);
            }
        }.start();
    }

    public  CinemaSeat getCinemaSeatOne(){
        CinemaSeat cinemaSeat = new CinemaSeat();
        ArrayList<seat> content = new ArrayList<>();
        cinemaSeat.setCoulmns(12);
        cinemaSeat.setRow(10);
        for(int i=0;i<120;++i){
            if(i%12==0&&i>=40){
                content.add(getNoneSeat());
            }else if(i%12==11&&i<50){
                content.add(getNoneSeat());
            }else if(i>108&&i<119){
                content.add(getLoversSeat().get(0));
                i++;
                content.add(getLoversSeat().get(1));
            }else {
                if(i==119){
                    content.add(getNoneSeat());
                }else {
                    content.add(getNormalSeat());
                }
            }
        }
        cinemaSeat.setSeatList(content);
        return cinemaSeat;
    }
    public  CinemaSeat getCinemaSeatTwo(){
        CinemaSeat cinemaSeat = new CinemaSeat();
        ArrayList<seat> content = new ArrayList<>();
        cinemaSeat.setCoulmns(19);
        cinemaSeat.setRow(11);
        for(int i=0;i<209;++i){
            if(i>=5*19&&i<6*19){
                content.add(getNoneSeat());
            } else if(i>=10*19){
                if(i%2==0){
                    content.add(getLoversSeat().get(0));
                }else {
                    content.add(getLoversSeat().get(1));
                }
            }else {
                content.add(getNormalSeat());
            }
        }
        cinemaSeat.setSeatList(content);
        return cinemaSeat;
    }
    private seat getNormalSeat(){
        seat s = new seat();
        Random random = new Random();
        //  s.setSeat_type(random.nextInt()%3);
        //  s.setSeat_state(random.nextInt()%3+3);
        s.setSeat_type(1);
        s.setSeat_state(4);
        return s;
    }
    private seat getNoneSeat(){
        seat s = new seat();
        Random random = new Random();
        //  s.setSeat_type(random.nextInt()%3);
        //  s.setSeat_state(random.nextInt()%3+3);
        s.setSeat_type(3);
        s.setSeat_state(4);
        return s;
    }
    private ArrayList<seat> getLoversSeat(){
        seat right = new seat();
        seat left = new seat();
        right.setSeat_type(seat.LOVERS_SEAT);
        right.setSeat_state(seat.STATE_EMPTY_SEAT);
        right.setLovers_locat(seat.LOVERS_RIGHT);
        left.setSeat_type(seat.LOVERS_SEAT);
        left.setSeat_state(seat.STATE_EMPTY_SEAT);
        left.setLovers_locat(seat.LOVERS_LEFT);
        ArrayList<seat> seats = new ArrayList<>();
        seats.add(left);
        seats.add(right);
        return seats;
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
        if(gvsAdapter!=null)
            seats.setSeatList(gvsAdapter.getData());
        gvsAdapter = new GridViewSeatAdapter(this,seats.getSeatList(),width,height,handler);
        gvsAdapter.setPicNum(picSum);
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
        seats.setSeatList(gvsAdapter.getData());
        gvsAdapter = new GridViewSeatAdapter(this,seats.getSeatList(),width,height,handler);
        gvsAdapter.setPicNum(picSum);
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

        public final static int LOVERS_RIGHT = 1;
        public final static int LOVERS_LEFT = 2;

        private int seat_type;
        private int seat_state;
        private int seatNum;
        private int lovers_locat;

        public seat() {
            super();
            seat_type = NONE_SEAT;
            seat_state = STATE_EMPTY_SEAT;
        }

        public int getLovers_locat() {
            return lovers_locat;
        }

        public void setLovers_locat(int lovers_locat) {
            this.lovers_locat = lovers_locat;
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
                        if(data.get(position).getLovers_locat()==seat.LOVERS_LEFT){
                            im.setImageResource(R.drawable.loverseat0left);
                        }else {
                            im.setImageResource(R.drawable.loverseat0right);
                        }
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
                        im.setImageResource(R.drawable.seat_choosing);

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
                                    if(picNum<5) {
                                        data.get(position).setSeat_state(seat.STATE_CHOOSING_SEAT);
                                        imm.setImageResource(R.drawable.seat_choosing);
                                        picNum++;
                                    }else {
                                        Toast.makeText(getApplicationContext(),
                                                "单次购票不能超过5张",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    data.get(position).setSeat_state(seat.STATE_EMPTY_SEAT);
                                    imm.setImageResource(R.drawable.seat_empty);
                                    picNum--;
                                }
                            }else {
                                if (data.get(position).getSeat_state() == seat.STATE_EMPTY_SEAT) {
                                    if(picNum<4) {
                                        data.get(position).setSeat_state(seat.STATE_CHOOSING_SEAT);
                                        if (data.get(position).getLovers_locat() == seat.LOVERS_LEFT) {
                                            data.get(position + 1).setSeat_state(seat.STATE_CHOOSING_SEAT);
                                        } else {
                                            data.get(position - 1).setSeat_state(seat.STATE_CHOOSING_SEAT);
                                        }
                                        notifyDataSetChanged();
                                        imm.setImageResource(R.drawable.loverseat_choosing);
                                        picNum += 2;
                                    }else {
                                        Toast.makeText(getApplicationContext(),
                                                "单次购票不能超过5张",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    data.get(position).setSeat_state(seat.STATE_EMPTY_SEAT);
                                    if(data.get(position).getLovers_locat()==seat.LOVERS_LEFT){
                                        data.get(position+1).setSeat_state(seat.STATE_EMPTY_SEAT);
                                    }else{
                                        data.get(position-1).setSeat_state(seat.STATE_EMPTY_SEAT);
                                    }
                                    notifyDataSetChanged();
                                    imm.setImageResource(R.drawable.loverseat_empty);
                                    picNum -= 2;
                                }
                            }
                            message = new Message();
                            message.arg1 = SEAT_STATE_CHANGED;
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

        public void setPicNum(int sum){
            picNum=sum;
        }
        public ArrayList<seat> getData(){
            return (ArrayList<seat>) data;
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
