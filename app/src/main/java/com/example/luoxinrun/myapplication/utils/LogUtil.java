package com.example.luoxinrun.myapplication.utils;

import android.util.Log;

/**
 * Created by luoxinrun on 2017/8/17.
 */

public class LogUtil {
  private static final boolean DEBUG = true;
  private static final String DIVISION = "+++++++++++++++++";
  private static final String TAG = "TAG";

  public static void d(String log) {

  }

  public static void print(String type, String log) {


    if (log.length() > 4000) {
      Log.v(TAG, "length = " + log.length());
      int chunkCount = log.length() / 4000;
      for (int i = 0; i <= chunkCount; i++) {
        int max = 4000 * (i + 1);
        if (max >= log.length()) {
          Log.v(TAG, DIVISION + i + "/" + chunkCount + ":" + log.substring(4000 * i));
        } else {
          Log.v(TAG, DIVISION + i + "/" + chunkCount + ":" + log.substring(4000 * i, max));
        }
      }
    } else {
      Log.v(TAG, DIVISION + ":" + log.toString());
    }
  }

}
