package com.rappasocial.routinemanager.free;

import com.rappasocial.routinemanager.free.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefs {
    public final static String PREFS_NAME = "prefs";
    public static String getSex(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString(
                context.getString(R.string.pref_key_flag_sex), "1");
    }
    public static void setSex(Context context, String newValue) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Editor prefsEditor = prefs.edit();
        prefsEditor.putString(
                context.getString(R.string.pref_key_flag_sex),
                newValue);
        prefsEditor.commit();
    }
}
