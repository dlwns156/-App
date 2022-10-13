package com.example.mapapap;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

public class LoginRequest {
    static String username;
    static final String user = "userID";
    TextView textView;

    static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    };
    // 계정 정보 저장
    public static void setUserId(Context context, String userId){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(user,userId);
        username = userId;
        Log.d("user",userId+"");
        Log.d("로그인 저장 된?",username);

        editor.commit();
    }
    public static String getUser(Context context){

        return getSharedPreferences(context).getString(user,"");

    }
    public static void clearUser(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }

}
