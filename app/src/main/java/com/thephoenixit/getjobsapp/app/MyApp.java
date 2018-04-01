package com.thephoenixit.getjobsapp.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.onesignal.OneSignal;
import com.thephoenixit.getjobsapp.utils.TypefaceUtil;

/**
 * Created by root on 18/03/29.
 */

public class MyApp extends Application {
    public MyApp() {
        prefName = "getjobsapp";
        mInstance = this;
    }

    public static MyApp getInstance()
    {
        MyApp myapplication = mInstance;
        return myapplication;
    }

    public boolean getIsJobProvider()
    {
        preferences = getSharedPreferences(prefName, 0);
        return preferences.getBoolean("IsJobProvider", false);
    }

    public boolean getIsLogin()
    {
        preferences = getSharedPreferences(prefName, 0);
        return preferences.getBoolean("IsLoggedIn", false);
    }

    public String getUserEmail()
    {
        preferences = getSharedPreferences(prefName, 0);
        return preferences.getString("email", "");
    }

    public String getUserId()
    {
        preferences = getSharedPreferences(prefName, 0);
        return preferences.getString("user_id", "");
    }

    public String getUserName()
    {
        preferences = getSharedPreferences(prefName, 0);
        return preferences.getString("user_name", "");
    }

    public void onCreate()
    {
        super.onCreate();
        OneSignal.startInit(this).inFocusDisplaying(com.onesignal.OneSignal.OSInFocusDisplayOption.Notification).init();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/custom.ttf");
    }

    public void saveIsJobProvider(boolean flag)
    {
        preferences = getSharedPreferences(prefName, 0);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IsJobProvider", flag);
        editor.apply();
    }

    public void saveIsLogin(boolean flag)
    {
        preferences = getSharedPreferences(prefName, 0);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IsLoggedIn", flag);
        editor.apply();
    }

    public void saveLogin(String s, String s1, String s2)
    {
        preferences = getSharedPreferences(prefName, 0);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", s);
        editor.putString("user_name", s1);
        editor.putString("email", s2);
        editor.apply();
    }

    private static MyApp mInstance;
    public String prefName;
    public SharedPreferences preferences;
}
