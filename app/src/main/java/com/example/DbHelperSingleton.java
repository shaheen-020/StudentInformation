package com.example;

import android.content.Context;

public class DbHelperSingleton {
    private static DbHelper instance;

    private DbHelperSingleton() {} // private constructor

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }
}

