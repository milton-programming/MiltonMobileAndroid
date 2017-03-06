package edu.milton.miltonmobileandroid.util;

import android.app.Application;
import android.content.Context;

public class MiltonMobileAndroid extends Application {
    private static MiltonMobileAndroid instance;

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
