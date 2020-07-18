package com.weshowedup.mjbi.Module;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.weshowedup.mjbi.Response.LoginResponse.Data;

public class SharedPrefManager {
    private static final String PREF_KEY_USER = "";
    SharedPreferences pref;


    private static final String PREF_NAME = "Android";
    int PRIVATE_MODE = 0;
    SharedPreferences.Editor editor;


    Context _context;

    public SharedPrefManager(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void putString(Context context, String key, String subscriptioncode){
        editor.putString(key,subscriptioncode);
        editor.commit();
    }

    public String getString(String key){
        return pref.getString(key,"");
    }

//////////////  save Token >>>>>>>>>>>>>>>>>

    public void setToken(String token){
        editor.putString("tkn",token);
        editor.commit();
    }

    public String getToken(){
        return pref.getString("tkn","");
    }


//////////////  save class of custom type and get that

    public void saveData(String key, Data data) {

        Gson gson = new Gson();
        String data1 = gson.toJson(data);
        editor.putString(key, data1);
        editor.commit();
    }

    public Data getData(String key) {
        Gson gson = new Gson();
        String json = pref.getString(key, "");
        Data data = gson.fromJson(json, Data.class);
        return data;

    }
}