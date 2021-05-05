package com.example.myapplication.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalService {

    private SharedPreferences GET;
    private SharedPreferences.Editor SET;

    public LocalService(Context context){
        GET = PreferenceManager.getDefaultSharedPreferences(context);
        SET = GET.edit();
    }

    public void add(String key, String value){
        SET.putString(key,value);
        SET.commit();
    }

    public String get(String key){
        return GET.getString(key,null);
    }

    public void delete(String key){
        if(get(key)!=null){
            SET.remove(key);
            SET.commit();
        }
    }
    public String update(String key, String value){
        if(get(value)!=null){
            SET.remove(value);
            SET.commit();
        }
        SET.putString(key,value);
        SET.commit();
        return key;
    }
    public Boolean isLogin(){
        return GET.getString("userId",null)!= null;
    }
}
