package com.example.luoxinrun.myapplication.greendao;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

/**
 * Created by luoxinrun on 2017/6/29.
 */

public class InputFilterMinMax implements InputFilter {

  private float min, max;
  private int precision;

  public InputFilterMinMax(float min, float max, int precision) {
    this.min = min;
    this.max = max;
    this.precision = precision;
  }

  public InputFilterMinMax(String min, String max, int precision) {
    this.min = Float.parseFloat(min);
    this.max = Float.parseFloat(max);
    this.precision = precision;
  }

  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    Log.e("TAG", "======source:" + source.toString() + "=====start:" + start + "=====end:" + end);
    Log.e("TAG", "======dest:" + dest.toString() + "=====dstart:" + dstart + "=====dend:" + dend);
    // 删除等特殊字符，直接返回
    if ("".equals(source.toString())) {
      return null;
    }

    if (!source.toString().matches("^[0-9\\.]*$")) {
      return "";
    }

    String dValue = dest.toString();
    String[] splitArray = dValue.split("\\.");
    int dotIndex = dValue.indexOf(".");
    if (splitArray.length > 1) {
      if (source.toString().startsWith(".")) {
        return "";// 输入多个点号
      }
      if (precision >= 0 && dstart > dotIndex) {
        String dotValue = splitArray[1];
        int diff = dotValue.length() + 1 - precision;
        if (diff > 0) {
          return source.subSequence(start, end - diff);
        }
      }
    } else {
      if (precision == 0 && source.toString().equals(".")) {
        return "";
      }
      if (dotIndex > 0 && source.toString().equals(".")) {
        return "";
      }
    }

    try {
      String s = "";
      if (dstart == dend && dstart == 0){
        s = source.toString() + dest.toString();
      }else if(dstart == dend && dstart > 0){
        s = dest.subSequence(0,dstart).toString() + source.toString() + dest.subSequence(dstart, dest.length());
      }
      float input = Float.parseFloat(s);
      if (!isInRange(min, max, input)) {
        return "";
      }
    } catch (NumberFormatException nfe) {
    }


    return null;
  }

  private boolean isInRange(float min, float max, float input) {
    return max > min ? input >= min && input <= max : input >= max && input <= min;
  }

}
