package com.example.hopjs.filmcinema.Data;

/**
 * Created by Hopjs on 2016/12/18.
 */

public class Result {
    public static final int RESULT_OK = 1;
    public static final int RESULT_EXIT_USER = 2;
    public static final int RESULT_USER_NOTEXIT = 3;
    public static final int RESULT_PWD_WRONG = 4;
    public static final int RESULT_EXIT_PHONE = 5;
    public static final int RESULT_TICKET_BOUGHT = 6;
    public static final int RESULT_EXIT_NAME = 7;
    public static final int RESULT_NOT_NETWORK = 8;
    public static final int RESULT_TICKET_OUTDATE = 9;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
