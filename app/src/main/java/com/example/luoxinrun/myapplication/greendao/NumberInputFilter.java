package com.example.luoxinrun.myapplication.greendao;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

/**
 * Created by luoxinrun on 2017/6/29.
 */

public class NumberInputFilter implements InputFilter {

  private float min = -1f, max = -1f; // 限定最大最小值
  private int precision = 0; // 限定位数

  private int count; // 记录小数点个数

  public NumberInputFilter(int precision) {
    this.precision = precision;
  }

  public NumberInputFilter minValue(float min) {
    this.min = min;
    return this;
  }

  public NumberInputFilter maxValue(float max) {
    this.max = max;
    return this;
  }

  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    Log.e("TAG", "======source:" + source.toString() + "=====start:" + start + "=====end:" + end);
    Log.e("TAG", "======dest:" + dest.toString() + "=====dstart:" + dstart + "=====dend:" + dend);
    // // 删除等特殊字符，直接返回
    // if ("".equals(source.toString())) {
    // return null;
    // }

    // 只能输入数字和小数点
    if (!source.toString().matches("^[0-9\\.]*$")) {
      return "";
    }

    String s = spliceStr(source, dest, dstart, dend);
    Log.e("TAG", "=======================spliceStr:" + s);

    // 最多只能有一个"."和保留"."后的固定位数
    count = 0;
    int n = containStrNum(s, ".");
    if (n > 1) {
      return "";
    } else if (n == 1) {
      if (s.substring(s.indexOf(".") + 1).length() > precision) {
        return "";
      }
    }

    // 首字符是"."进行转换
    if (s.startsWith(".")) {
      if (dstart == dend && dstart == 0) {
        //首位添加
        return "0.";
      } else if (dstart < dend && dstart == 0) {
        //首位删除
        return "0";
      }
    }

    // 过滤首字符是"0"第二个字符不是"."的情况
    if (s.length() > 1 && s.startsWith("0") && s.charAt(1) != '.') {
      if (dstart == dend && dstart == 1) {
        //从第二位添加
        return "";
      } else if (dstart < dend) {
        //删除操作
        return dest.subSequence(dstart, dend);
      }
    }

    // 限制最大值和最小值(如果限定为负，则按照默认大小处理)
    if (min >= 0 || max >= 0) {
      if (min >= 0 && max < 0) {
        max = Float.MAX_VALUE;
      } else if (min < 0 && max >= 0) {
        min = 0;
      }

      try {
        float input = Float.parseFloat(s);
        if (!isInRange(min, max, input)) {
          return "";
        }
      } catch (NumberFormatException nfe) {
        return "";
      }
    }

    // // 最多只能有一个点和保留小数点后的固定位数
    // String dValue = dest.toString();
    // if (dValue.indexOf(".") != -1) {
    // if (source.toString().indexOf(".") != -1) {
    // return "";
    // }
    // String s = spliceStr(source, dest, dstart, dend);
    // if (s.substring(s.indexOf(".") + 1).length() > precision) {
    // return "";
    // }
    // } else {
    // count = 0;
    // int n = containStrNum(source.toString(), ".");
    // Log.e("TAG", "+++++++++++++++" + n);
    // if (n > 1) {
    // return "";
    // } else if (n == 1) {
    // String s = spliceStr(source, dest, dstart, dend);
    // if (s.substring(s.indexOf(".") + 1).length() > precision) {
    // return "";
    // }
    // }
    // }
    //
    // // 首字符是"."进行转换
    // if (dstart == dend && dstart == 0) {
    // // 首位直接输入"."
    // if (source.toString().startsWith(".")) {
    // return "0.";
    // }
    // } else if (dstart < dend && dstart == 0) {
    // // 删除后首位变成"."，由于删除过程source始终是"",所以上边过滤删除操作的去掉。
    // String s1 = dest.toString().substring(dend);
    // if (s1.startsWith(".")) {
    // return "0";
    // }
    // }
    //
    // // 过滤首字符是0第二个字符不是"."的情况
    // String s1 = spliceStr(source, dest, dstart, dend);
    // if (s.length() > 1 && s.startsWith("0") && s.charAt(1) != '.') {
    // return "";
    // }
    //
    // // String dValue = dest.toString();
    // // String[] splitArray = dValue.split("\\.");
    // // int dotIndex = dValue.indexOf(".");
    // // Log.e("TAG","+++++++++dotIndex"+splitArray.length);
    // // if (splitArray.length > 1) {
    // // if (source.toString().startsWith(".")) {
    // // return "";// 输入多个点号
    // // }
    // // if (precision >= 0 && dstart > dotIndex) {
    // // String dotValue = splitArray[1];
    // // int diff = dotValue.length() + 1 - precision;
    // // if (diff > 0) {
    // // return source.subSequence(start, end - diff);
    // // }
    // // }
    // // } else {
    // // if (precision == 0 && source.toString().equals(".")) {
    // // return "";
    // // }
    // // if (dotIndex > 0 && source.toString().equals(".")) {
    // // return "";
    // // }
    // // }
    //
    // // 限制最大值和最小值
    // try {
    // String s = "";
    // if (dstart == dend && dstart == 0) {
    // s = source.toString() + dest.toString();
    // } else if (dstart == dend && dstart > 0) {
    // s = dest.subSequence(0, dstart).toString() + source.toString()
    // + dest.subSequence(dstart, dest.length());
    // }
    // float input = Float.parseFloat(s);
    // if (!isInRange(min, max, input)) {
    // return "";
    // }
    // } catch (NumberFormatException nfe) {
    // }

    return null;
  }

  /**
   * 字符拼接
   * 
   * @param source
   * @param dest
   * @param dstart
   * @param dend
   * @return
   */
  public String spliceStr(CharSequence source, Spanned dest, int dstart, int dend) {
    // 输入操作
    if (dstart == dend && dstart == 0) {
      return source.toString() + dest.toString();
    } else if (dstart == dend && dstart > 0) {
      return dest.subSequence(0, dstart).toString() + source.toString()
          + dest.subSequence(dstart, dest.length());
    }
    // 删除操作
    return dest.toString().substring(0, dstart) + dest.toString().substring(dend);
  }

  /**
   * 判断母串包含子串的个数
   * 
   * @param str
   * @param s
   * @return
   */
  private int containStrNum(String str, String s) {
    if (str.indexOf(s) == -1) {
      return 0;
    } else {
      count++;
      containStrNum(str.substring(str.indexOf(s) + s.length()), s);
      return count;
    }
  }

  /**
   * 判断是否属于某个区间
   * 
   * @param min
   * @param max
   * @param input
   * @return
   */
  private boolean isInRange(float min, float max, float input) {
    return max > min ? input >= min && input <= max : input >= max && input <= min;
  }

}
