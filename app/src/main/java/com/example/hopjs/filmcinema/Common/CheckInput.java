package com.example.hopjs.filmcinema.Common;

/**
 * Created by Hopjs on 2016/12/25.
 */

public class CheckInput {
    public static final int NAME_OVERLENGTH=1;
    public static final int NAME_NOCONTENT=2;
    public static final int NAME_OK=6;
    public static final int PASSWORD_NOCONTENT=3;
    public static final int PASSWORD_NOTFITLENGTH=4;
    public static final int PASSWORD_EXISTILLEGALCHAR=5;
    public static final int PASSWORD_OK=7;
    public static int isRightName(String name){
        if(name.length()==0){
            return NAME_NOCONTENT;
        }else if(name.length()>10){
            return NAME_OVERLENGTH;
        }else {
            return NAME_OK;
        }
    }
    public static int isRightPassword(String password){
        if(password.length()==0){
            return PASSWORD_NOCONTENT;
        }else if(password.length()<6||password.length()>12){
            return PASSWORD_NOTFITLENGTH;
        }else {
            int zero = (int)'0';
            int a=(int)'a';
            int A=(int)'A';
            for(int i=0;i<password.length();++i){
                int s = (int)password.charAt(i);
                if((s>=zero&&s<zero+10)
                        ||(s>=a&&a<a+26)
                        ||(s>=A&&A<A+26)){
                }else {
                    return PASSWORD_EXISTILLEGALCHAR;
                }
            }
        }
        return PASSWORD_OK;
    }
}
