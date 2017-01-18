package com.example.hopjs.filmcinema.Network;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.hopjs.filmcinema.Adapter.CollectionAdapter;
import com.example.hopjs.filmcinema.Adapter.CriticAdapter;
import com.example.hopjs.filmcinema.Adapter.FilmListAdapter;
import com.example.hopjs.filmcinema.Adapter.MyCriticAdapter;
import com.example.hopjs.filmcinema.Adapter.RvCityAdapter;
import com.example.hopjs.filmcinema.Adapter.TicketRecordAdapter;
import com.example.hopjs.filmcinema.Adapter.WorkersAdapter;
import com.example.hopjs.filmcinema.Data.Cinema;
import com.example.hopjs.filmcinema.Data.FilmList;
import com.example.hopjs.filmcinema.Data.HomePageFilm;
import com.example.hopjs.filmcinema.Data.Result;
import com.example.hopjs.filmcinema.Data.UserAccount;
import com.example.hopjs.filmcinema.UI.CinemaDetail;
import com.example.hopjs.filmcinema.UI.FilmDetail;
import com.example.hopjs.filmcinema.UI.Fragment.FilmListFragment;
import com.example.hopjs.filmcinema.UI.SeatChoose;
import com.example.hopjs.filmcinema.UI.TicketRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Hopjs on 2016/12/5.
 */

/*
* 目的：整理出本应用的网络通信规则
* 格式：1，网络通信的用途；2，获取的内容；3，需要的数据量；4，据此设计该通信的网络通信码
* 开始整理
* 一、首页大型海报获取
* 2，获取海报的电影Id和海报图片
* 3，4份
* 4，NETWORK_HOMEPAGE_GETBIGPOSTER 1
* 二、获取正在热映的电影信息
* 2，电影Id、海报、评分、名称
* 3，10份
* 4，NETWORK_HOMEPAGE_GETISSHOWING 2
* 三、获取即将上映的电影信息
* 2，电影Id、海报、名称、上映时间
* 3，10份
* 4，NETWORK_HOMEPAGE_GETISCOMING 3
* 四、获取正在热映的电影
* 2，电影Id、名称、评分、正在上映的影院数量、上映的场次、海报
* 3，10份
* 4，NETWORK_FILMLIST_GETISSHOWING 4
* 五、获取即将上映的电影
* 2、电影id、名称、评分、海报、类型、上映日期
* 3、10份
* 4，NETWORK_FILMLIST_GETISCOMING 5
* 六、获取全城市影院信息
* 2、发送城市id，影院id、影院名称、最低票价、地址、距离
* 3，10份
* 4，NETWORK_CINEMALIST_ALLCITY_NOSPECIAL 6
* 七、获取附近影院信息
* 2，发送城市id、经纬度，获取影院id、影院名称、最低票价、地址、距离
* 3，10份
* 4，NETWORK_CINEMALIST_NEARBY_NOSPECIAL 7
* 八、获取购票记录
* 2，发送用户id，获取电影名称、电影id、影院名称、观看日期、放映厅号、放映时间、票数、票号、购买日期
* 3，10份
* 4，NETWORK_TICKETRECORD 8
* 九、获取个人影评记录
* 2，发送用户id，获取电影名称、电影id、评分、内容、发布日期、被点赞数
* 3，10份
* 4，NETWORK_MYCRITIC 9
* 十、我的收藏
* 2，发送用户id，获取电影名称、编号、评分、海报、类型、导演、时长
* 3，10份
* 4，NETWORK_COLLECTION 10
* 十一、获取电影的基本信息
* 2，发送电影id，获取电影海报、高清背景、电影名称、评分、类型、时长、上映时间
* 3，1份
* 4，NETWORK_FILMDETAIL_FINFOR 11
* 十二、获取剧情简介和工作人员信息
* 2，发送电影id，获取简介、导演照片、导演名称、演员照片、名称、扮演角色
* 3，1份
* 4，NETWORK_FILMDETAIL_MINFOR 12
* 十三、获取用户评论
* 2，发送电影id，获取用户名称、用户id、用户头像、用户评分、内容、评论时间和受赞数
* 3，5份
* 4，NETWORK_FILMDETAIL_CRITIC 13
* 十四、获取某电影的在某城市正在或即将上映的影院信息
* 2，发送电影id、城市id，获取获取影院id、影院名称、最低票价、地址、距离
* 3，10份
* 4，NETWORK_CINEMALIST_ALLCITY_SPECIAL 14
* 十五、获取某电影在用户附近的用户信息
* 2，发送电影id、城市id、经纬度，获取获取获取影院id、影院名称、最低票价、地址、距离
* 3，10份
* 4，NETWORK_CINEMALIST_NEARBY_SPECIAL 15
*十六、获取影院基本信息
* 2，发送影院id、城市id，获取影院联系电话
* 3，1份
* 4，NETWORK_CINEMADETAIL_FINFOR 16
* 十七、获取影院电影列表
* 2，发送影院id、城市id，获取电影列表和拍片场次，包含电影id、名称、海报、评分，场次
* 包括日期、厅号、时间和票价
* 3，全部
* 4，NETWORK_CINEMADEATIL_FILM 17
* 十八、获取影院某天某放映厅某场次的座位分布
* 2，发送影院id、场次编号，获取座位分布图
* 3，1份
* 4，NETWORK_SEATCHOOSE 18
* */
public class Connect {

    public static final int NETWORK_HOMEPAGE_GETBIGPOSTER = 1;
    public static final int NETWORK_HOMEPAGE_GETISSHOWING = 2;
    public static final int NETWORK_HOMEPAGE_GETUPCOMING = 3;
    public static final int NETWORK_FILMLIST_GETNOWSHOWING = 4;
    public static final int NETWORK_FILMLIST_GETUPCOMING = 5;
    public static final int NETWORK_CINEMALIST_ALLCITY_NOSPECIAL = 6;
    public static final int NETWORK_CINEMALIST_NEARBY_NOSPECIAL = 7;
    public static final int NETWORK_TICKETRECORD = 8;
    public static final int NETWORK_MYCRITIC = 9;
    public static final int NETWORK_COLLECTION = 10;
    public static final int NETWORK_FILMDETAIL_FINFOR = 11;
    public static final int NETWORK_FILMDETAIL_ACTORS = 12;
    public static final int NETWORK_FILMDETAIL_CRITIC = 13;
    public static final int NETWORK_CINEMALIST_ALLCITY_SPECIAL = 14;
    public static final int NETWORK_CINEMALIST_NEARBY_SPECIAL = 15;
    public static final int NETWORK_CINEMADETAIL_FINFOR = 16;
    public static final int NETWORK_CINEMADEATIL_FILM = 17;
    public static final int NETWORK_SEATCHOOSE = 18;
    public static final int NETWORK_FILMDETAIL_DIRECTOR = 19;
    public static final int NETWORK_CINEMADEATIL_SESSION = 20;
    public static final int NETWORK_FILM_PICTURE = 21;
    public static final int NETWORK_FILMDETAIL_PLOT = 22;
    public static final int NETWORK_PORTRAIT = 23;
    public static final int NETWORK_SEARCH_FILM = 33;
    public static final int NETWORK_POST_LOGIN = 24;
    public static final int NETWORK_POST_REGISTER = 25;
    public static final int NETWORK_POST_PCENTER_EDIT = 26;
    public static final int NETWORK_POST_PCENTER_PSD = 27;
    public static final int NETWORK_POST_PCENTER_PHONE = 28;
    public static final int NETWORK_POST_PRAISE = 29;
    public static final int NETWORK_POST_COLLECT = 30;
    public static final int NETWORK_POST_CRITIC = 31;
    public static final int NETWORK_POST_BUYTICKET = 32;
    public static final int NETWORK_CITY = 34;
    public static final int NETWORK_SEARCH_CINEMA = 35;

    private static HttpURLConnection getHttpURLConnection(URL url)throws Exception{
        HttpURLConnection httpURLConnection = null;
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(6000);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    public static class TemUrl{
        private final String SURL = "http://192.168.253.1:8080/FilmCinemaService/servlet/Service";
        private String surl;
        public TemUrl() {
            surl = SURL;
        }
        public void setConnectionType(final int type){
            surl += ("?type="+type);
        }
        public void addHeader(String name,String value){
            surl += "&"+name+"="+value;
        }
        public URL getURL() throws Exception{
            return new URL(surl);
        }
        public String getSurl(){
            return surl;
        }
    }

    public   static   String   inputStream2String(InputStream   is)   throws IOException {
        ByteArrayOutputStream baos   =   new   ByteArrayOutputStream();
        int   i=-1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return   baos.toString();
    }


    public static Result postBuyTicket(String sessionId,String userId,
             String cinemaId,String buyTime,String filmId,ArrayList<Integer> tickets){
        //准备阶段
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;

        jsonObject.addProperty("userId",userId);
        jsonObject.addProperty("sessionId",sessionId);
        jsonObject.addProperty("buyTime",buyTime);
        jsonObject.addProperty("cinemaId",cinemaId);
        jsonObject.addProperty("filmId",filmId);
        JsonArray jsonArray = new JsonArray();
        for(int i:tickets){
            jsonArray.add(i);
        }
        jsonObject.add("tickets",jsonArray);

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_POST_BUYTICKET);///
        ///
        try {
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            result.setCode(jsonObject.get("result").getAsInt());
            Log.i("uuuuuuuuuuuuu","normal:"+result.getCode()+"");
        }catch (Exception e){
            result.setCode(Result.RESULT_NOT_NETWORK);
            Log.i("uuuuuuuuuuuuu","erro:"+result.getCode()+e.getMessage());
        }
        Log.i("uuuuuuuuuuuuu",result.getCode()+"");
        return result;

    }

    public static Result postCritic(CriticAdapter.Critic critic){
        //准备阶段
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        try {
            jsonObject.addProperty("userId", critic.getId());
            jsonObject.addProperty("filmId", critic.getFilmId());
            jsonObject.addProperty("time", critic.getDate());
            jsonObject.addProperty("content", URLEncoder.encode(critic.getContent(), "UTF-8"));
            jsonObject.addProperty("scord", critic.getScord());

            TemUrl temUrl = new TemUrl();
            temUrl.setConnectionType(NETWORK_POST_CRITIC);///
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
        }catch (Exception e){
                result.setCode(Result.RESULT_NOT_NETWORK);
        }
        result.setCode(Result.RESULT_OK);
        return result;
    }

    public static Result postCollect(String userId,String filmId){
        //准备阶段
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;

        jsonObject.addProperty("userId",userId);
        jsonObject.addProperty("filmId",filmId);

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_POST_COLLECT);///
        ///
        try {
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            int code = httpURLConnection.getResponseCode();

        }catch (Exception e){

        }
        return result;
    }

    public static Result postPraise(String userId,String criticId){
        //准备阶段
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;

        jsonObject.addProperty("userId",userId);
        jsonObject.addProperty("criticId",criticId);

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_POST_PRAISE);///
        ///
        try {
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            int code = httpURLConnection.getResponseCode();


        }catch (Exception e){

        }
        return result;
    }

    public static Result postPhone(String userId,String phone){
        //准备阶段
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;

        jsonObject.addProperty("userId",userId);
        jsonObject.addProperty("phone",phone);

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_POST_PCENTER_PHONE);///
        ///
        try {
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            result.setCode(jsonObject.get("result").getAsInt());
        }catch (Exception e){
            result.setCode(Result.RESULT_NOT_NETWORK);
        }
        result.setCode(Result.RESULT_OK);
        return result;
    }

    public static Result postPsd(String userId,String pwd){
        //准备阶段
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;

        jsonObject.addProperty("userId",userId);
        jsonObject.addProperty("password",pwd);

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_POST_PCENTER_PSD);///
        ///
        try {
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.getResponseCode();
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
        }catch (Exception e){
            result.setCode(Result.RESULT_NOT_NETWORK);
        }
        return result;
    }
///
    public static Result postEdit(String userId,String userName, String sex, Bitmap portrait){
        //准备阶段
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;

        jsonObject.addProperty("userId",userId);
        jsonObject.addProperty("userName",userName);
        jsonObject.addProperty("sex",sex);

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_POST_PCENTER_EDIT);///
        ///
        try {
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            result.setCode(jsonObject.get("result").getAsInt());
        }catch (Exception e){

        }
        result.setCode(Result.RESULT_NOT_NETWORK);
        return result;
    }
    public static UserAccount postLogin(String phone,String pwd){
        //准备阶段
        UserAccount userAccount;
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;

        jsonObject.addProperty("phone",phone);
        jsonObject.addProperty("password",pwd);

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_POST_LOGIN);///
        ///
        try {
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(),"UTF-8"));
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            if(jsonObject.get("userAccount").getAsInt()==1){
                userAccount=null;
            }else{
                userAccount = new UserAccount();
                userAccount.setUserId(jsonObject.get("id").getAsString());
                userAccount.setName(jsonObject.get("name").getAsString());
                userAccount.setPwd(pwd);
                userAccount.setPortraitName(jsonObject.get("portraitName").getAsString());
                userAccount.setSex(jsonObject.get("sex").getAsString());
                userAccount.setBphone(phone);
            }
        }catch (Exception e){
            userAccount = new UserAccount();
            userAccount.setSetportrait(true);
        }
        /*
        userAccount.setLogin(true);
        userAccount.setPwd("123456");
        userAccount.setSex("男");
        userAccount.setPortraitName("");
        userAccount.setName("风");
        userAccount.setBphone("15988169467");
        userAccount.setUserId("001");*/
        return userAccount;
    }
    public static Result postRegister(UserAccount userAccount){
        //准备阶段
        Result result = new Result();
        JsonObject jsonObject = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        try {
            jsonObject.addProperty("name", URLEncoder.encode(userAccount.getName(), "UTF-8"));
            jsonObject.addProperty("password", userAccount.getPwd());
            jsonObject.addProperty("phone", userAccount.getBphone());

            TemUrl temUrl = new TemUrl();
            temUrl.setConnectionType(NETWORK_POST_REGISTER);///
            temUrl.addHeader("postContent", URLEncoder.encode(jsonObject.toString(), "UTF-8"));
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            httpURLConnection.setRequestMethod("POST");
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            result.setCode(jsonObject.get("result").getAsInt());
        }catch (Exception e){
            result.setCode(Result.RESULT_NOT_NETWORK);
        }
        return result;
    }

    //////////
    public static ArrayList<FilmList> getSearchFilm(String filmNameLike,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<FilmList> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_SEARCH_FILM);///
        temUrl.addHeader("start",start);

        ///
        try {
            //通信阶段
            filmNameLike=URLEncoder.encode(filmNameLike,"UTF-8");
            temUrl.addHeader("filmNameLike",URLEncoder.encode(filmNameLike,"UTF-8"));
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                FilmList temContent = new FilmList();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setPosterName(j.getAsJsonObject().get("posterName").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setScord(j.getAsJsonObject().get("scord").getAsString());
                temContent.setDate(j.getAsJsonObject().get("date").getAsString());
                temContent.setType(j.getAsJsonObject().get("type").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
                Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<Cinema> getSearchCinema(String cinemaNameLike,String cityId,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<Cinema> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_SEARCH_CINEMA);///
        temUrl.addHeader("cityId",cityId);
        temUrl.addHeader("start",start);

        ///
        try {
            //通信阶段
            cinemaNameLike=URLEncoder.encode(cinemaNameLike,"UTF-8");
            temUrl.addHeader("cinemaNameLike",URLEncoder.encode(cinemaNameLike,"UTF-8"));
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                Cinema temContent = new Cinema();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setAddress(j.getAsJsonObject().get("address").getAsString());
                temContent.setDistance(j.getAsJsonObject().get("distance").getAsString());
                temContent.setlPrice(j.getAsJsonObject().get("lprice").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<RvCityAdapter.City> getCity(){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<RvCityAdapter.City> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_CITY);///
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                RvCityAdapter.City temContent = new RvCityAdapter.City();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setLettle(j.getAsJsonObject().get("lettle").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){

        }
        return Content;
    }
    public static ArrayList<HomePageFilm> getNowShowingData_HomepageFilm(){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<HomePageFilm> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_HOMEPAGE_GETISSHOWING);///
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                HomePageFilm temContent = new HomePageFilm();
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setScord(j.getAsJsonObject().get("scord").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setPosterName(j.getAsJsonObject().get("posterName").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<HomePageFilm> getUpcomingData_HomepageFilm(){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<HomePageFilm> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_HOMEPAGE_GETUPCOMING);///
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                HomePageFilm temContent = new HomePageFilm();
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setScord(j.getAsJsonObject().get("scord").getAsString());
                temContent.setDate(j.getAsJsonObject().get("date").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setPosterName(j.getAsJsonObject().get("posterName").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<FilmList> getBigPosters_HomePageFilm(){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<FilmList> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_HOMEPAGE_GETBIGPOSTER);///
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                FilmList temContent = new FilmList();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setPosterName(j.getAsJsonObject().get("posterName").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<FilmList> getNowShowingData_FilmList(String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<FilmList> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_FILMLIST_GETNOWSHOWING);///
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                FilmList temContent = new FilmList();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setPosterName(j.getAsJsonObject().get("posterName").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setScord(j.getAsJsonObject().get("scord").getAsString());
                temContent.setShowingTimes(j.getAsJsonObject().get("showingTimes").getAsString());
                temContent.setCinemaNum(j.getAsJsonObject().get("cinemaNum").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<FilmList> getUpcomingData_FilmList(String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<FilmList> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_FILMLIST_GETUPCOMING);///
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                FilmList temContent = new FilmList();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setPosterName(j.getAsJsonObject().get("posterName").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setScord(j.getAsJsonObject().get("scord").getAsString());
                temContent.setDate(j.getAsJsonObject().get("date").getAsString());
                temContent.setType(j.getAsJsonObject().get("type").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<Cinema> getAllCity_CinemaList_NoSpecial(String cityId,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<Cinema> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_CINEMALIST_ALLCITY_NOSPECIAL);///
        temUrl.addHeader("cityId",cityId);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                Cinema temContent = new Cinema();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setAddress(j.getAsJsonObject().get("address").getAsString());
                temContent.setDistance(j.getAsJsonObject().get("distance").getAsString());
                temContent.setlPrice(j.getAsJsonObject().get("lprice").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<Cinema> getNearby_CinemaList_NoSpecial(String cityId,String jing,String wei,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<Cinema> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_CINEMALIST_NEARBY_NOSPECIAL);///
        temUrl.addHeader("cityId",cityId);
        temUrl.addHeader("jing",jing);
        temUrl.addHeader("wei",wei);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                Cinema temContent = new Cinema();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setAddress(j.getAsJsonObject().get("address").getAsString());
                temContent.setDistance(j.getAsJsonObject().get("distance").getAsString());
                temContent.setlPrice(j.getAsJsonObject().get("lprice").getAsString());
                Content.add(temContent);
            }
            /*ArrayList<Cinema> tem = new ArrayList<>();
            while (Content.size()>0){
                int min=findMin(Content);
                tem.add(Content.get(min));
                Content.remove(min);
            }
            Content=tem;*/
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }

    public static ArrayList<TicketRecordAdapter.TicketRecord> getTicketRecorder(String userId,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<TicketRecordAdapter.TicketRecord> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_TICKETRECORD);///
        temUrl.addHeader("userId",userId);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                TicketRecordAdapter.TicketRecord temContent = new TicketRecordAdapter.TicketRecord();///
                temContent.setDate(j.getAsJsonObject().get("date").getAsString());
                temContent.setFilmId(j.getAsJsonObject().get("filmId").getAsString());
                temContent.setBuyTime(j.getAsJsonObject().get("buyTime").getAsString());
                temContent.setCinemaName(j.getAsJsonObject().get("cinemaName").getAsString());
                temContent.setFilmName(j.getAsJsonObject().get("filmName").getAsString());
                temContent.setTime(j.getAsJsonObject().get("time").getAsString());
                temContent.setVideoHall(j.getAsJsonObject().get("videoHall").getAsString());
                JsonArray jsonArray2 = j.getAsJsonObject().get("tickets").getAsJsonArray();
                ArrayList<Integer> tickets = new ArrayList<>();
                for (JsonElement je:jsonArray2) {
                    tickets.add(je.getAsInt());
                }
                temContent.setTickets(tickets);
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static ArrayList<MyCriticAdapter.Critic> getMycritic(String userId,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<MyCriticAdapter.Critic> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_MYCRITIC);///
        temUrl.addHeader("userId",userId);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                MyCriticAdapter.Critic temContent = new MyCriticAdapter.Critic();///
                temContent.setDate(j.getAsJsonObject().get("date").getAsString());
                temContent.setFilmId(j.getAsJsonObject().get("filmId").getAsString());
                temContent.setFilmName(j.getAsJsonObject().get("filmName").getAsString());
                temContent.setContent(j.getAsJsonObject().get("content").getAsString());
                String tem = j.getAsJsonObject().get("scord").getAsString();
                temContent.setScord(Float.parseFloat(tem));
                temContent.setPraise(j.getAsJsonObject().get("praise").getAsString());
                tem = j.getAsJsonObject().get("isPraise").getAsString();
                if(tem.equals("ture")){
                    temContent.setPraise(true);
                }else {
                    temContent.setPraise(false);
                }

                Content.add(temContent);
            }
        }catch (Exception e){

        }
        return Content;
    }
    public static ArrayList<CollectionAdapter.Collection> getCollection(String userId,String start){
//准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<CollectionAdapter.Collection> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_COLLECTION);///
        temUrl.addHeader("userId",userId);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                CollectionAdapter.Collection temContent = new CollectionAdapter.Collection();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setPosterName(j.getAsJsonObject().get("posterName").getAsString());
                temContent.setType(j.getAsJsonObject().get("type").getAsString());
                temContent.setTime(j.getAsJsonObject().get("time").getAsString());
                temContent.setScord(j.getAsJsonObject().get("scord").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setDirector(j.getAsJsonObject().get("director").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){

        }
        return Content;
    }
    public static FilmDetail.FinforFilm getFinfor_FilmDetail(String filmId){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        FilmDetail.FinforFilm Content = new FilmDetail.FinforFilm();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_FILMDETAIL_FINFOR);///
        temUrl.addHeader("filmId",filmId);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();

            Content.setFilmId(filmId);
            Content.setScord(jsonObject.getAsJsonObject().get("scord").getAsString());
            Content.setPosterName(jsonObject.getAsJsonObject().get("posterName").getAsString());
            Content.setName(jsonObject.getAsJsonObject().get("name").getAsString());
            Content.setTime(jsonObject.getAsJsonObject().get("time").getAsString());
            Content.setType(jsonObject.getAsJsonObject().get("type").getAsString());
            Content.setDate(jsonObject.getAsJsonObject().get("date").getAsString());

        }catch (Exception e){

        }
        return Content;
    }
    public static ArrayList<WorkersAdapter.Actors> getActors_FilmDeatail(String filmId,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<WorkersAdapter.Actors> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_FILMDETAIL_ACTORS);///
        temUrl.addHeader("filmId",filmId);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                WorkersAdapter.Actors temContent = new WorkersAdapter.Actors();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setPortraitName(j.getAsJsonObject().get("portraitName").getAsString());
                temContent.setRole(j.getAsJsonObject().get("role").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){

        }
        return Content;
    }
    public static WorkersAdapter.Director getDirector_FilmDeatil(String filmId){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        WorkersAdapter.Director Content = new WorkersAdapter.Director();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_FILMDETAIL_DIRECTOR);///
        temUrl.addHeader("filmId",filmId);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            Content.setId(jsonObject.getAsJsonObject().get("id").getAsString());
            Content.setportraitName(jsonObject.getAsJsonObject().get("portraitName").getAsString());
            Content.setName(jsonObject.getAsJsonObject().get("name").getAsString());

        }catch (Exception e){

        }
        return Content;
    }
    public static ArrayList<CriticAdapter.Critic> getCritic_FilmDeatil(String filmId,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<CriticAdapter.Critic> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_FILMDETAIL_CRITIC);///
        temUrl.addHeader("filmId",filmId);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                CriticAdapter.Critic temContent = new CriticAdapter.Critic();///
                temContent.setDate(j.getAsJsonObject().get("date").getAsString());
                temContent.setPortraitName(j.getAsJsonObject().get("portraitName").getAsString());
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setContent(j.getAsJsonObject().get("content").getAsString());
                String tem = j.getAsJsonObject().get("scord").getAsString();
                temContent.setScord(Float.parseFloat(tem));
                temContent.setPraise(j.getAsJsonObject().get("praise").getAsString());
                tem = j.getAsJsonObject().get("isPraise").getAsString();
                if(tem.equals("ture")){
                    temContent.setPraise(true);
                }else {
                    temContent.setPraise(false);
                }
                Content.add(temContent);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return Content;
    }
    public static ArrayList<Cinema> getAllCity_CinemaList_Special(String filmId,String cityId,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<Cinema> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_CINEMALIST_ALLCITY_SPECIAL);///
        temUrl.addHeader("cityId",cityId);
        temUrl.addHeader("filmId",filmId);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                Cinema temContent = new Cinema();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setAddress(j.getAsJsonObject().get("address").getAsString());
                temContent.setDistance(j.getAsJsonObject().get("distance").getAsString());
                temContent.setlPrice(j.getAsJsonObject().get("lprice").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){

        }
        return Content;
    }
    public static ArrayList<Cinema> getNearby_CinemaList_Special(String filmId,String cityId,String jing,String wei,String start){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<Cinema> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_CINEMALIST_NEARBY_SPECIAL);///
        temUrl.addHeader("filmId",filmId);
        temUrl.addHeader("cityId",cityId);
        temUrl.addHeader("jing",jing);
        temUrl.addHeader("wei",wei);
        temUrl.addHeader("start",start);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                Cinema temContent = new Cinema();///
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                temContent.setAddress(j.getAsJsonObject().get("address").getAsString());
                temContent.setDistance(j.getAsJsonObject().get("distance").getAsString());
                temContent.setlPrice(j.getAsJsonObject().get("lprice").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static CinemaDetail.Cinema getFinfor_CinemaDetail(String cinemaId) {
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        CinemaDetail.Cinema Content = new CinemaDetail.Cinema();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_CINEMADETAIL_FINFOR);///
        temUrl.addHeader("cinemaId", cinemaId);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();

            Content.setId(cinemaId);
            Content.setPhone(jsonObject.get("phone").getAsString());
            Content.setAddress(jsonObject.get("address").getAsString());
            Content.setName(jsonObject.get("name").getAsString());
        }
        catch (Exception e){

        }
        return Content;
    }
    public static ArrayList<CinemaDetail.Film> getFilm_CinemaDetail(String cinemaId){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<CinemaDetail.Film> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_CINEMADEATIL_FILM);///
        temUrl.addHeader("cinemaId",cinemaId);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                CinemaDetail.Film temContent = new CinemaDetail.Film();///
                String scord = j.getAsJsonObject().get("scord").getAsString();
                temContent.setScord(Float.parseFloat(scord));
                temContent.setId(j.getAsJsonObject().get("id").getAsString());
                temContent.setPosterName(j.getAsJsonObject().get("posterName").getAsString());
                temContent.setName(j.getAsJsonObject().get("name").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){

        }
        return Content;
    }
    public static ArrayList<CinemaDetail.Session> getSession_CinemaDetail(String cinemaId,String filmId){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        ArrayList<CinemaDetail.Session> Content = new ArrayList<>();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_CINEMADEATIL_SESSION);///
        temUrl.addHeader("cinemaId",cinemaId);
        temUrl.addHeader("filmId",filmId);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("List").getAsJsonArray();///
            for (JsonElement j:jsonArray) {
                CinemaDetail.Session temContent = new CinemaDetail.Session();///
                temContent.setFilmId(filmId);
                temContent.setVideoHallNum(j.getAsJsonObject().get("videoHallNum").getAsString());
                temContent.setPrice(j.getAsJsonObject().get("price").getAsString());
                temContent.setTime(j.getAsJsonObject().get("time").getAsString());
                temContent.setDate(j.getAsJsonObject().get("date").getAsString());
                temContent.setSessionId(j.getAsJsonObject().get("sessionId").getAsString());
                Content.add(temContent);
            }
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static SeatChoose.CinemaSeat getSeat(String cinemaId,String filmId,String sessionId){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        SeatChoose.CinemaSeat Content = new SeatChoose.CinemaSeat();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_SEATCHOOSE);///
        temUrl.addHeader("cinemaId",cinemaId);
        temUrl.addHeader("sessionId",sessionId);
        temUrl.addHeader("filmId",filmId);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            String tem = jsonObject.getAsJsonObject().get("coulmns").getAsString();
            Content.setCoulmns(Integer.parseInt(tem));
            tem = jsonObject.getAsJsonObject().get("row").getAsString();
            Content.setRow(Integer.parseInt(tem));
            JsonArray jsonArray2 = jsonObject.getAsJsonObject().get("seatList").getAsJsonArray();
            ArrayList<SeatChoose.seat> temSeat = new ArrayList<>();
            for (JsonElement je : jsonArray2) {
                SeatChoose.seat s = new SeatChoose.seat();
                s.setSeat_state(je.getAsJsonObject().get("state").getAsInt());
                s.setSeat_type(je.getAsJsonObject().get("type").getAsInt());
                s.setSeatNum(je.getAsJsonObject().get("seatNum").getAsInt());
                s.setLovers_locat(je.getAsJsonObject().get("lovers_locat").getAsInt());
                temSeat.add(s);
            }
            Content.setSeatList(temSeat);
        }catch (Exception e){
            Log.e("iiiiiiiiiiiii",e.getMessage());
        }
        return Content;
    }
    public static String getPlot(String filmId){
        //准备阶段
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        String Content = new String();///

        TemUrl temUrl = new TemUrl();
        temUrl.setConnectionType(NETWORK_FILMDETAIL_PLOT);///
        temUrl.addHeader("filmId",filmId);
        ///
        try {
            //通信阶段
            HttpURLConnection httpURLConnection = getHttpURLConnection(temUrl.getURL());
            int code = httpURLConnection.getResponseCode();
            if (code == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            //解析阶段
            String data = inputStream2String(inputStream);
            data = URLDecoder.decode(data, "UTF-8");
            jsonObject = jsonParser.parse(data).getAsJsonObject();
            Content = jsonObject.getAsJsonObject().get("plot").getAsString();

        }catch (Exception e){

        }
        return Content;
    }

}
