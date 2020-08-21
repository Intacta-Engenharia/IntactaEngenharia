package com.intactaengenharia.intacta.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private Activity activity;
    private SharedPreferences preferences;

    public Preferences(Activity activity) {
        this.activity = activity;
        this.preferences = activity.getSharedPreferences("userpreferences", Context.MODE_PRIVATE);
    }

    public void setLogged(String cnpj){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("logged", cnpj).apply();
        //System.out.println("Has logged? " + this.Getlogin());


    }

    public String getLogin(){ return preferences.getString("logged", ""); }

    public void destroy(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("logged", null).apply();
    }
}
