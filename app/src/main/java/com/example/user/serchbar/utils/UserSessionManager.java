package com.example.user.serchbar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSessionManager {
    private static final String TAG = UserSessionManager.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = ApplicationConstant.APPLICATION_PACKAGE_NAME;

    public static final String COUNT="count";

    // Constructor

    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public HashMap<String, String> getCountDetails() {

        HashMap<String, String> imei = new HashMap<String, String>();
        imei.put(COUNT, pref.getString(COUNT, null));
    return imei;
    }

    public void createCountSession(String count) {
        // Storing login value as TRUE
        // Storing name in pref
        editor.putString(COUNT, count);
        editor.commit();
    }

}
