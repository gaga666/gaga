package com.example.graduates.fragment;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static MyApplication instance;
    public static Context getInstance(){
        if(instance == null){
            instance = new MyApplication();
        }
        return instance;
    }

}
