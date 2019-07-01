package com.matt.android.mynews.models.utils;

import android.util.Log;

/**
 * Class who manage logs
 */
public class Logger {

   //Blocking problem
    public static void e(String message) {
        Log.e("TAG", message);
    }

    //Non-Blocking problem
    public static void w(String message) {
        Log.w("TAG", message);
    }
    
    public static void i(String message) {
        Log.i("TAG", message);
    }
}
