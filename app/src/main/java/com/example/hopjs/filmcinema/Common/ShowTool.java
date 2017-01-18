package com.example.hopjs.filmcinema.Common;

/**
 * Created by Hopjs on 2016/12/27.
 */

public class ShowTool {
    public static String getUpcomingDate(String date){
        String content,year="",month="",day="";
        for(int i=0;i<8&&i<date.length();++i){
            if(i<4){
                year+=date.charAt(i);
            }
            if(i==4||i==5){
                month+=date.charAt(i);
            }
            if(i==6||i==7){
                day+=date.charAt(i);
            }
        }
        content=year+"."+month+"."+day;
        return content;
    }
    public static String getUpcomingListFragmentDate(String date){
        String content,year="",month="",day="";
        for(int i=0;i<8&&i<date.length();++i){
            if(i<4){
                year+=date.charAt(i);
            }
            if(i==4||i==5){
                month+=date.charAt(i);
            }
            if(i==6||i==7){
                day+=date.charAt(i);
            }
        }
        content=year+"年"+month+"月"+day+"日上映";
        return content;
    }
    public static String showDistance(String distance){
        int d = Integer.parseInt(distance);
        String content="";
        if(d<1000){
            content=d+"米";
        }
        if(d>=1000&&d<20000){
            int a=d/1000;
            int b=d%1000/100;
            content=a+"."+b+"公里";
        }
        if(d>=20000){
            content=">20公里";
        }
        return content;
    }
    public static String showCriticTime(String date){
        String content,year="",month="",day="",hour="",minute="";
        for(int i=0;i<12&&i<date.length();++i){
            if(i<4){
                year+=date.charAt(i);
            }
            if(i==4||i==5){
                month+=date.charAt(i);
            }
            if(i==6||i==7){
                day+=date.charAt(i);
            }
            if(i==8||i==9){
                hour+=date.charAt(i);
            }
            if(i==10||i==11){
                minute+=date.charAt(i);
            }
        }
        content=year+"-"+month+"-"+day+" "+hour+":"+minute;
        return content;
    }

    public static String sessionStartTime(String time){
        String content,hour="",minute="";
        for(int i=0;i<4&&i<time.length();++i){
            if(i<2){
                hour+=time.charAt(i);
            }else {
                minute+=time.charAt(i);
            }
        }
        content=hour+":"+minute;
        return content;
    }

}
