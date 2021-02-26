package com.example.qaunewsalerts.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {

    // General Methods
    public static void clearEntireCache(Context context) { SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }

    public static String get(Context context, String valueKey, String valueDefault) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(valueKey, valueDefault);
    }

    public static void set(Context context, String valueKey, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(valueKey, value);
        edit.commit();
    }

    public static int get(Context context, String valueKey, int valueDefault) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(valueKey, valueDefault);
    }

    public static void set(Context context, String valueKey, int value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(valueKey, value);
        edit.commit();
    }

    public static boolean get(Context context, String valueKey, boolean valueDefault) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(valueKey, valueDefault);
    }

    public static void set(Context context, String valueKey, boolean value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(valueKey, value);
        edit.commit();
    }

    public static long get(Context context, String valueKey, long valueDefault) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getLong(valueKey, valueDefault);
    }

    public static void set(Context context, String valueKey, long value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(valueKey, value);
        edit.commit();
    }

    public static float get(Context context, String valueKey, float valueDefault) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getFloat(valueKey, valueDefault);
    }

    public static void set(Context context, String valueKey, float value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putFloat(valueKey, value);
        edit.commit();
    }

}
