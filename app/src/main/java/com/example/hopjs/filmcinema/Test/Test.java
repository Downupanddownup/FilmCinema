package com.example.hopjs.filmcinema.Test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hopjs.filmcinema.Adapter.CollectionAdapter;
import com.example.hopjs.filmcinema.Adapter.CriticAdapter;
import com.example.hopjs.filmcinema.Adapter.MyCriticAdapter;
import com.example.hopjs.filmcinema.Adapter.TicketRecordAdapter;
import com.example.hopjs.filmcinema.Adapter.WorkersAdapter;
import com.example.hopjs.filmcinema.Data.Cinema;
import com.example.hopjs.filmcinema.Data.FilmList;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.UI.CinemaDetail;
import com.example.hopjs.filmcinema.UI.MyCritic;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class Test {
    public static int getPicture(int seed){
        int index = seed%24;
        int resourceId;
        switch (index){
            case 0:resourceId = R.drawable.a;
                break;
            case 1:resourceId = R.drawable.b;
                break;
            case 2:resourceId = R.drawable.c;
                break;
            case 3:resourceId = R.drawable.d;
                break;
            case 4:resourceId = R.drawable.e;
                break;
            case 5:resourceId = R.drawable.f;
                break;
            case 6:resourceId = R.drawable.g;
                break;
            case 7:resourceId = R.drawable.h;
                break;
            case 8:resourceId = R.drawable.i;
                break;
            case 9:resourceId = R.drawable.j;
                break;
            case 10:resourceId = R.drawable.k;
                break;
            case 11:resourceId = R.drawable.l;
                break;
            case 12:resourceId = R.drawable.m;
                break;
            case 13:resourceId = R.drawable.n;
                break;
            case 14:resourceId = R.drawable.o;
                break;
            case 15:resourceId = R.drawable.p;
                break;
            case 16:resourceId = R.drawable.q;
                break;
            case 17:resourceId = R.drawable.r;
                break;
            case 18:resourceId = R.drawable.s;
                break;
            case 19:resourceId = R.drawable.t;
                break;
            case 20:resourceId = R.drawable.u;
                break;
            case 21:resourceId = R.drawable.v;
                break;
            case 22:resourceId = R.drawable.w;
                break;
            case 23:resourceId = R.drawable.x;
                break;
            default:resourceId = R.color.colorAccent;
        }
        return resourceId;
    }
    public static ArrayList<Cinema> getCinemas(int start,int num){
        ArrayList<Cinema> cinemas = new ArrayList<>();
        for(int i=start; i<start+num; ++i){
            Cinema tem = new Cinema();
            tem.setId(i+"");
            tem.setName("影院"+i);
            tem.setAddress("问责路，朝阳"+i+"号");
            tem.setDistance("距离："+i+"公里");
            tem.setlPrice("最低票价：￥5"+i);
            cinemas.add(tem);
        }
        return cinemas;
    }
    public static void showToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
    public static WorkersAdapter.Director getDirector(){
        WorkersAdapter.Director director = new WorkersAdapter.Director();
        director.setId("1");
        director.setName("张杰与");
        director.setPortraitId(getPicture(9));
        return director;
    }
    public static ArrayList<WorkersAdapter.Actors> getActors(){
        ArrayList<WorkersAdapter.Actors> actorses = new ArrayList<>();
        for (int i=0; i<5;++i) {
            WorkersAdapter.Actors actors = new WorkersAdapter.Actors();
            actors.setId("5"+i);
            actors.setName("柳承浩"+i);
            actors.setRole("饰：吴三桂"+i);
            actors.setPortraitId(getPicture(i*7));
            actorses.add(actors);
        }
        return actorses;
    }
    public static String getPlot(){
        return "在这项实验中，研究人员先将一个多电极阵列传感器植入猴子大脑，让它可以在大脑相关区域直接读取手臂控制鼠标的脑电位信号，并训练猴子通过键盘输入屏幕上看到的字符。研究人员通过设计的识别算法来识别猴子打字的脑电位信号，并实现在虚拟键盘上移动光标、选择字母键。最终，经过训练的猴子可以将所看到的文字通过意念“隔空”复写出来，而且最快可以每分钟12个单词的速度输入《纽约时报》的文章或莎士比亚名著《哈姆雷特》的段落。";
    }
    public static ArrayList<CriticAdapter.Critic> getCritics(int num){
        ArrayList<CriticAdapter.Critic> critics = new ArrayList<>();
        for(int i=num;i<num*2;++i){
            CriticAdapter.Critic critic = new CriticAdapter.Critic();
            critic.setName("用户"+i);
            critic.setContent("权衡 view 和 content 来决定它的宽度和高度的整齐。它被measure(int, int) 调用 并且应该被子类所覆盖，以便提供准确高效的布局测量。\n" +
                    "规定: 当覆盖这个方法的时候，你必须调用 setMeasuredDimension(int, int)以便存储精确的视图的宽和高。如果不这样做的话将触发llegalStateException异常，" +
                    "被函数 measure(int, int)抛出。调用父类 onMeasure(int, int)是合理的。");
            critic.setDate("16年10月"+i+"日");
            critic.setId(i+"");
            critic.setPortraitId(getPicture(i*7));
            critic.setPraise(i%2==0);
            critic.setPraise(i*45%30+"");
            critic.setScord(i*8%10);
            critics.add(critic);
        }
        return critics;
    }
    public static ArrayList<CollectionAdapter.Collection> getCollections(int num){
        ArrayList<CollectionAdapter.Collection> collections = new ArrayList<>();
        for(int i=num;i<num+num;++i){
            CollectionAdapter.Collection collection = new CollectionAdapter.Collection();
            collection.setName("电影："+i);
            collection.setId(i+"");
            collection.setDirector("导演："+i);
            collection.setScord(i*7%6+5+"");
            collection.setTime(i+":20");
            collection.setType("动作");
            collection.setPosterId(getPicture(i));
            collections.add(collection);
        }
        return collections;
    }
    public static ArrayList<MyCriticAdapter.Critic> getMyCritics(int num){
        ArrayList<MyCriticAdapter.Critic> critics = new ArrayList<>();
        for(int i=num;i<num*2;++i){
            MyCriticAdapter.Critic critic = new MyCriticAdapter.Critic();
            critic.setFilmName("电影"+i);
            critic.setFilmId(i+"");
            critic.setContent("权衡 view 和 content 来决定它的宽度和高度的整齐。它被measure(int, int) 调用 并且应该被子类所覆盖，以便提供准确高效的布局测量。\n" +
                    "规定: 当覆盖这个方法的时候，你必须调用 setMeasuredDimension(int, int)以便存储精确的视图的宽和高。如果不这样做的话将触发llegalStateException异常，" +
                    "被函数 measure(int, int)抛出。调用父类 onMeasure(int, int)是合理的。");
            critic.setDate("16年10月"+i+"日");
            critic.setPraise(i%2==0);
            critic.setPraise(i*45%30+"");
            critic.setScord(i*8%10);
            critics.add(critic);
        }
        return critics;
    }
    public static ArrayList<TicketRecordAdapter.TicketRecord> getTicketRecords(int num){
        ArrayList<TicketRecordAdapter.TicketRecord> ticketRecords = new ArrayList<>();
        for(int i=num;i<num*2;++i){
            TicketRecordAdapter.TicketRecord ticketRecord = new TicketRecordAdapter.TicketRecord();
            ticketRecord.setFilmId(i+"");
            ticketRecord.setFilmName("电影"+i);
            ticketRecord.setCinemaName("影院"+i);
            ticketRecord.setDate("2016.10."+i);
            ticketRecord.setVideoHall(i+"号厅");
            ticketRecord.setTime("11:"+i);
            ArrayList<Integer> tem = new ArrayList<>();
            for(int j=0;j<i%4;++j){
                tem.add((j+1)*i*345%100);
            }
            ticketRecord.setTickets(tem);
            ticketRecord.setBuyTime("6:"+i);
            ticketRecords.add(ticketRecord);
        }
        return ticketRecords;
    }
    public static ArrayList<CinemaDetail.Session> getSessions(String filmId,String cinemaId){
        ArrayList<CinemaDetail.Session> sessions = new ArrayList<>();
        for(int i=0; i<10;++i){
            CinemaDetail.Session session = new CinemaDetail.Session();
            String date = "";
            switch (i%3){
                case 0:
                    date = "2016.10.27";
                    break;
                case 1:
                    date = "2016.10.28";
                    break;
                case 2:
                    date = "2016.10.29";
                    break;
            }
            session.setDate(date);
            session.setFilmId(filmId);
            session.setPrice(40+i+"");
            session.setSessionId(i*8+"");
            session.setTime(10+i+":20");
            session.setVideoHallNum(i%5+"");
            sessions.add(session);
        }
        return  sessions;
    }
    public static ArrayList<CinemaDetail.Film> getFilms(String cinemaId){
        ArrayList<CinemaDetail.Film> films = new ArrayList<>();
        for(int i=0; i<6; ++i){
            CinemaDetail.Film film = new CinemaDetail.Film();
            film.setId(i+"");
            film.setName("电影："+i);
            film.setPosterId(getPicture(i*89+10));
            film.setScord((float)(i/2.0+6)/2);
            films.add(film);
        }
        return films;
    }
    public static CinemaDetail.Cinema getCinema(String cinemaId){
        CinemaDetail.Cinema cinema = new CinemaDetail.Cinema();
        cinema.setId(cinemaId);
        cinema.setAddress("昌耀路3号");
        cinema.setName("1234556影院");
        cinema.setPhone("9876543212");
        return cinema;
    }

    public static ArrayList<FilmList> getFilmList(){
        ArrayList<FilmList> filmListArrayList = new ArrayList<FilmList>();
        for(int i=0; i<10;++i){
            FilmList filmList = new FilmList();
            filmList.setId(i+"");
            filmList.setName("电影"+i);
            filmList.setScord(i*8%10+5+"");
                    /*filmList.setPoster(getPoster(i));*/
            filmList.setPosterResourceId(Test.getPicture(i));
            filmList.setType("类型："+i);
            filmList.setDate("2016年10月"+i+"日");
            filmList.setCinemaNum(i*55+i*10+i+"");
            filmList.setShowingTimes(12*(66*i)+"");
            filmListArrayList.add(filmList);
        }
        return filmListArrayList;
    }

}
