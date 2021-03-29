package com.intactaengenharia.intacta.service.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.intactaengenharia.intacta.service.constants.UserConstants;

public class Preferences {

    private final SharedPreferences mPreferences;

    public Preferences(Context context) {
        this.mPreferences = context.getSharedPreferences(UserConstants.PREFERENCES.USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void store(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public String get(String key) {
        return mPreferences.getString(key, "");
    }

    public void remove(String key) {
        mPreferences.edit().remove(key).apply();
    }
}
