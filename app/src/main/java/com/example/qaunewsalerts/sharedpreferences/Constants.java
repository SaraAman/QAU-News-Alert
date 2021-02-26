package com.example.qaunewsalerts.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Constants {
    public static String userId;
    public static String password="";
    public static String name="";
    public static String email="";
    public static String department="";

    public  static String newsCategories[];

//    public static boolean is_language_change=false;
//    public static String Language="English";



    public static void setSharedString(String key, String value, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key , value).commit();
    }
    public static String getSharedString(String key, String defaultString, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, defaultString);
    }
    public static int getSharedInteger(String key, int defaultValue, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, defaultValue);
    }
    public static void setSharedInteger(String key, int value, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(key , value).commit();
    }
    public static boolean getSharedBoolean(String key, boolean defaultValue, Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, defaultValue);
    }
    public static void setSharedBoolean(String key, boolean value, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(key , value).commit();
    }



}

