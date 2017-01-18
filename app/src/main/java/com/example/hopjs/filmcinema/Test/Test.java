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
import com.example.hopjs.filmcinema.Adapter.RvCityAdapter;
import com.example.hopjs.filmcinema.Adapter.TicketRecordAdapter;
import com.example.hopjs.filmcinema.Adapter.WorkersAdapter;
import com.example.hopjs.filmcinema.Data.Cinema;
import com.example.hopjs.filmcinema.Data.FilmList;
import com.example.hopjs.filmcinema.Data.HomePageFilm;
import com.example.hopjs.filmcinema.Data.TicketInformation;
import com.example.hopjs.filmcinema.Network.Connect;
import com.example.hopjs.filmcinema.R;
import com.example.hopjs.filmcinema.UI.CinemaDetail;
import com.example.hopjs.filmcinema.UI.FilmDetail;
import com.example.hopjs.filmcinema.UI.MyCritic;
import com.example.hopjs.filmcinema.UI.SeatChoose;

import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/10/12.
 */

public class Test {

    public static String getNidemingziPlot(){
        String p = "  故事发生的地点是在每千年回归一次的彗星造访过一个月之前，" +
                " 日本飞驒市的乡下小镇糸守町。在这里女高中生三叶每天都过着忧郁的生活，而她烦恼的不光有担任镇长的父亲所举行的选举运动，还有家传神社的古老习俗。在这个小小的城镇，周围都只是些爱瞎操心的老人。为此三叶对于大都市充满了憧憬。" +
                "然而某一天，自己做了一个变成男孩子的梦。这里有着陌生的房间、陌生的朋友。而眼前出现的则是东京的街道。三叶虽然感到困惑，但是能够来到朝思暮想的都市生活，让她觉得神清气爽。另一方面在东京生活的男高中生立花泷也做了个奇怪的梦，他在一个从未去过的深山小镇中，变成了女高中生。两人就这样在梦中邂逅了彼此。";
        return p;
    }
    public static FilmDetail.FinforFilm getfilmdetail(){
        FilmDetail.FinforFilm f = new FilmDetail.FinforFilm();
        f.setDate("2016-12-02");
        f.setFilmId("1");
        f.setPosterName(R.drawable.nidemingzi+"");
        f.setScord("9.1");
        f.setType("动画/爱情/剧情");
        f.setTime("106分钟");
        f.setName("你的名字");
        return f;
    }

    public static ArrayList<Integer>getBigPosters(){
        //获取四张电影海报
        ArrayList<Integer>res = new ArrayList<>();
        res.add(R.drawable.meigonghexingdong);
        res.add(R.drawable.mingribianyuan);
        res.add(R.drawable.shiqianyiwannian);
        res.add(R.drawable.zhushenzhinu);
        return res;
    }
    public static ArrayList<HomePageFilm>getUpcoming(){
        ArrayList<HomePageFilm> upcoming = new ArrayList<>();
        HomePageFilm t = new HomePageFilm();
        t.setId("1");
        t.setPosterName(R.drawable.baiduren+"");
        t.setName("摆渡人");
        t.setScord("7.0");
        t.setDate("2016-12-23");
        upcoming.add(t);
        t=new HomePageFilm();
        t.setId("2");
        t.setPosterName(R.drawable.maopaiwodi+"");
        t.setName("冒牌卧底");
        t.setScord("7.0");
        t.setDate("2016-12-29");
        upcoming.add(t);
        t=new HomePageFilm();
        t.setId("3");
        t.setPosterName(R.drawable.shixinzhe+"");
        t.setName("失心者");
        t.setScord("7.0");
        t.setDate("2016-12-23");
        upcoming.add(t);
        t=new HomePageFilm();
        t.setId("4");
        t.setPosterName(R.drawable.lueduozhe+"");
        t.setName("掠夺者");
        t.setScord("7.0");
        t.setDate("2016-12-23");
        upcoming.add(t);
        return upcoming;
    }
    public static ArrayList<HomePageFilm>getNowShowing(){
        ArrayList<HomePageFilm> nowshowing = new ArrayList<>();
        HomePageFilm t = new HomePageFilm();
        t.setId("1");
        t.setPosterName(R.drawable.changcheng+"");
        t.setName("长城");
        t.setScord("8.1");
        nowshowing.add(t);
        t=new HomePageFilm();
        t.setId("2");
        t.setPosterName(R.drawable.tiedaofeihu+"");
        t.setName("铁道飞虎");
        t.setScord("7.0");
        nowshowing.add(t);
        t=new HomePageFilm();
        t.setId("3");
        t.setPosterName(R.drawable.nidemingzi+"");
        t.setName("你的名字");
        t.setScord("9.1");
        nowshowing.add(t);
        t=new HomePageFilm();
        t.setId("4");
        t.setPosterName(R.drawable.wozaigugongxiuwenwu+"");
        t.setName("我在故宫修文物");
        t.setScord("8.4");
        nowshowing.add(t);
        return nowshowing;
    }

    public static ArrayList<FilmList>getNowShowingFilmList(){
        ArrayList<FilmList> nowshowing = new ArrayList<>();
        FilmList t = new FilmList();
        t.setId("1");
        t.setPosterName(R.drawable.changcheng+"");
        t.setName("长城");
        t.setScord("8.1");
        nowshowing.add(t);
        t.setCinemaNum("今日84家影院上映");
        t.setShowingTimes("共放映1237场");
        t=new FilmList();
        t.setId("2");
        t.setPosterName(R.drawable.tiedaofeihu+"");
        t.setName("铁道飞虎");
        t.setScord("7.0");
        t.setCinemaNum("今日80家影院上映");
        t.setShowingTimes("共放映890场");
        nowshowing.add(t);
        t=new FilmList();
        t.setId("3");
        t.setPosterName(R.drawable.nidemingzi+"");
        t.setName("你的名字");
        t.setScord("9.1");
        t.setCinemaNum("今日55家影院上映");
        t.setShowingTimes("共放映171场");
        nowshowing.add(t);
        t=new FilmList();
        t.setId("4");
        t.setPosterName(R.drawable.wozaigugongxiuwenwu+"");
        t.setName("我在故宫修文物");
        t.setScord("8.4");
        nowshowing.add(t);
        t.setCinemaNum("今日47家影院上映");
        t.setShowingTimes("共放映91场");
        return nowshowing;
    }
    public static ArrayList<FilmList>getUpcomingFilmList(){
        ArrayList<FilmList> upcoming = new ArrayList<>();
        FilmList t = new FilmList();
        t.setId("1");
        t.setPosterName(R.drawable.baiduren+"");
        t.setName("摆渡人");
        t.setScord("7.0");
        t.setDate("2016-12-23");
        t.setType("爱情/喜剧");
        upcoming.add(t);
        t=new FilmList();
        t.setId("2");
        t.setPosterName(R.drawable.maopaiwodi+"");
        t.setName("冒牌卧底");
        t.setScord("7.0");
        t.setDate("2016-12-29");
        t.setType("喜剧");
        upcoming.add(t);
        t=new FilmList();
        t.setId("3");
        t.setPosterName(R.drawable.shixinzhe+"");
        t.setName("失心者");
        t.setScord("7.0");
        t.setDate("2016-12-23");
        t.setType("惊悚");
        upcoming.add(t);
        t=new FilmList();
        t.setId("4");
        t.setPosterName(R.drawable.lueduozhe+"");
        t.setName("掠夺者");
        t.setScord("7.0");
        t.setDate("2016-12-23");
        t.setType("犯罪");
        upcoming.add(t);
        return upcoming;
    }

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

    public static ArrayList<RvCityAdapter.City> getCitys(){
        ArrayList<RvCityAdapter.City> cityArrayList = new ArrayList<>();
        char t = 'Z';
        int a = (int)t;
        for(int i=0;i<26;++i){
            if(i%2!=0) {
                for (int j = 0; j < i / 2 + 1; j++) {
                    RvCityAdapter.City tem = new RvCityAdapter.City();
                    tem.setId(""+i+j);
                    t =(char)(a-i);
                    tem.setLettle(t+"");
                    tem.setName("城市"+t+j);
                    cityArrayList.add(tem);
                }
            }
        }
        return  cityArrayList;
    }

    public static ArrayList<Cinema> getCinemas(int start,int num){
        ArrayList<Cinema> cinemas = new ArrayList<>();
        Cinema tem = new Cinema();
        tem.setId("");
        tem.setName("新远下沙影城");
        tem.setAddress("下沙经济技术开发区文泽路99" +
                "号福雷德广场B座");
        tem.setDistance("712m");
        tem.setlPrice("最低票价：￥34");
        cinemas.add(tem);
        tem = new Cinema();
        tem.setId("");
        tem.setName("杭州比高电影城（大河店）");
        tem.setAddress("杭州市拱墅区小河路488" +
                "号运河天地4幢");
        tem.setDistance("19.7km");
        tem.setlPrice("最低票价：￥39");
        cinemas.add(tem);
        tem = new Cinema();
        tem.setId("");
        tem.setName("杭州佳映IMAX影城");
        tem.setAddress("杭州市西湖区古墩路与余杭塘路路口" +
                "五洲广场2层（印象城对面）");
        tem.setDistance(">20km");
        tem.setlPrice("最低票价：￥38");
        cinemas.add(tem);
        tem = new Cinema();
        tem.setId("");
        tem.setName("杭州UME");
        tem.setAddress("杭州市文二西路551号西城广场4楼");
        tem.setDistance(">20km");
        tem.setlPrice("最低票价：￥37");
        cinemas.add(tem);

        return cinemas;
    }
    public static void showToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
    public static WorkersAdapter.Director getDirector(){
        WorkersAdapter.Director director = new WorkersAdapter.Director();
        director.setId("1");
        director.setName("新海诚");
        director.setportraitName(R.drawable.daoyan+"");
       // director.setPortraitId(getPicture(9));
        return director;
    }
    public static ArrayList<WorkersAdapter.Actors> getActors(){
        ArrayList<WorkersAdapter.Actors> actorses = new ArrayList<>();
        WorkersAdapter.Actors actors = new WorkersAdapter.Actors();
        actors.setId("5");
        actors.setName("神木隆之介");
        actors.setRole("饰：立花泷");
        actors.setPortraitName(R.drawable.shengmulongzhijie+"");
        actorses.add(actors);
        actors = new WorkersAdapter.Actors();
        actors.setId("5");
        actors.setName("上白石萌音");
        actors.setRole("饰：宫水三叶");
        actors.setPortraitName(R.drawable.shangbaishimengying+"");
        actorses.add(actors);
        actors = new WorkersAdapter.Actors();
        actors.setId("5");
        actors.setName("悠木碧");
        actors.setRole("饰：名取早耶香");
        actors.setPortraitName(R.drawable.youmubi+"");
        actorses.add(actors);
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
          //  critic.setPortraitId(getPicture(i*7));
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
         //   collection.setPosterId(getPicture(i));
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
                    date = "2016.12.23";
                    break;
                case 1:
                    date = "2016.12.24";
                    break;
                case 2:
                    date = "2016.12.25";
                    break;
            }
            session.setDate(date);
            session.setFilmId(filmId);
            session.setPrice("39");
            session.setSessionId(i*8+"");
            session.setTime(10+i+":20");
            session.setVideoHallNum(i%4+1+"号厅");
            sessions.add(session);
        }
        return  sessions;
    }
    public static ArrayList<CinemaDetail.Film> getFilms(String cinemaId){
        ArrayList<CinemaDetail.Film> films = new ArrayList<>();
        CinemaDetail.Film film = new CinemaDetail.Film();
        film.setId("1");
        film.setName("摆渡人");
        film.setPosterName(R.drawable.baiduren+"");
        film.setScord(7.0f/2);
        films.add(film);
        film = new CinemaDetail.Film();
        film.setId("2");
        film.setName("长城");
        film.setPosterName(R.drawable.changcheng+"");
        film.setScord(6.8f/2);
        films.add(film);
        film = new CinemaDetail.Film();
        film.setId("3");
        film.setName("我在故宫修文物");
        film.setPosterName(R.drawable.wozaigugongxiuwenwu+"");
        film.setScord(7.2f/2);
        films.add(film);
        return films;
    }
    public static CinemaDetail.Cinema getCinema(String cinemaId){
        CinemaDetail.Cinema cinema = new CinemaDetail.Cinema();
        cinema.setId(cinemaId);
        cinema.setAddress("下沙经济技术开发区文泽路99号福雷德广场B座");
        cinema.setName("新远下沙影城");
        cinema.setPhone("0571-88285470");


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
          //  filmList.setPosterResourceId(Test.getPicture(i));
            filmList.setType("类型："+i);
            filmList.setDate("2016年10月"+i+"日");
            filmList.setCinemaNum(i*55+i*10+i+"");
            filmList.setShowingTimes(12*(66*i)+"");
            filmListArrayList.add(filmList);
        }
        return filmListArrayList;
    }

    public static TicketInformation getTicketInformation(){
        TicketInformation ticketInformation = new TicketInformation();
        ticketInformation.setTime("10:00");
        ticketInformation.setCinemaId("");
        ticketInformation.setCinemaName("新远下沙影城");
        ticketInformation.setDate("12月23日");
        ticketInformation.setFilmId("");
        ticketInformation.setFilmName("摆渡人");
        ticketInformation.setPrice("39");
        ArrayList<SeatChoose.seat>t=new ArrayList<>();
        SeatChoose.seat a=new SeatChoose.seat();
        a.setSeatNum(20);
        a.setSeat_type(1);
        t.add(a);
        ticketInformation.setTickets(t);
        ticketInformation.setVideoHallNum("7号厅");
        return ticketInformation;
    }

}
