package com.project.athlo_app.UtilsService;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceclass {

    private static  final String USER_PREF="athlo_user";
    private SharedPreferences appShared;
     private  SharedPreferences.Editor prefEditor;

     public  SharedPreferenceclass(Context context){
         appShared=context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE);
         this.prefEditor=appShared.edit();


     }

    public int getValue_int(String key) {
        return appShared.getInt(key, 0);
    }

    public void setValue_int(String key, int value) {
        prefEditor.putInt(key, value).commit();
    }

    // string
    public String getValue_string(String key) {
        return appShared.getString(key, "");
    }

    public void setValue_string(String key, String value) {
        prefEditor.putString(key, value).commit();
    }


    // boolean
    public boolean getValue_boolean(String key) {
        return appShared.getBoolean(key, false);
    }

    public void setValue_boolean(String key, boolean value) {
        prefEditor.putBoolean(key, value).commit();
    }

    public void clear() {
        prefEditor.clear().commit();
    }
}
