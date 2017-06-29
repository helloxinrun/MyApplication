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

    private int count;

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
//        // 删除等特殊字符，直接返回
//        if ("".equals(source.toString())) {
//            return null;
//        }

        //只能输入数字和点
        if (!source.toString().matches("^[0-9\\.]*$")) {
            return "";
        }

        //首字符不能是"."
        if (dstart == dend && dstart == 0) {
            //开头添加"."
            if (source.toString().startsWith(".")) {
                return "0.";
            }
        } else if (dstart < dend && dstart == 0) {
            //开头删除后变成"."，由于删除过程source始终是"",所以上边的判断特殊字符的去掉。
            String s = dest.toString().substring(dend);
            if (s.startsWith(".")) {
                return "0";
            }
        }

        //最多只能有一个点和保留小数点后的固定位数
        String dValue = dest.toString();
        if (dValue.indexOf(".") != -1) {
            if (source.toString().indexOf(".") != -1) {
                return "";
            }
            String s = spliceStr(source, dest, dstart, dend);
            if (s.substring(s.indexOf(".") + 1).length() > precision) {
                return "";
            }
        } else {
            count = 0;
            int n = containStrNum(source.toString(), ".");
            Log.e("TAG","+++++++++++++++"+n);
            if (n > 1) {
                return "";
            } else if (n == 1) {
                String s = spliceStr(source, dest, dstart, dend);
                if (s.substring(s.indexOf(".") + 1).length() > precision) {
                    return "";
                }
            }
        }

        //过滤首字符是0第二个字符不是"."的情况
        String s1 = spliceStr(source, dest, dstart, dend);
        if (s1.length()>1 && s1.startsWith("0") && s1.charAt(1) != '.'){
            return "";
        }

//    String dValue = dest.toString();
//    String[] splitArray = dValue.split("\\.");
//    int dotIndex = dValue.indexOf(".");
//    Log.e("TAG","+++++++++dotIndex"+splitArray.length);
//    if (splitArray.length > 1) {
//      if (source.toString().startsWith(".")) {
//        return "";// 输入多个点号
//      }
//      if (precision >= 0 && dstart > dotIndex) {
//        String dotValue = splitArray[1];
//        int diff = dotValue.length() + 1 - precision;
//        if (diff > 0) {
//          return source.subSequence(start, end - diff);
//        }
//      }
//    } else {
//      if (precision == 0 && source.toString().equals(".")) {
//        return "";
//      }
//      if (dotIndex > 0 && source.toString().equals(".")) {
//        return "";
//      }
//    }

        //限制最大值和最小值
        try {
            String s = "";
            if (dstart == dend && dstart == 0) {
                s = source.toString() + dest.toString();
            } else if (dstart == dend && dstart > 0) {
                s = dest.subSequence(0, dstart).toString() + source.toString() + dest.subSequence(dstart, dest.length());
            }
            float input = Float.parseFloat(s);
            if (!isInRange(min, max, input)) {
                return "";
            }
        } catch (NumberFormatException nfe) {
        }

        return null;
    }

    public String spliceStr(CharSequence source, Spanned dest, int dstart, int dend) {
        if (dstart == dend && dstart == 0) {
            return source.toString() + dest.toString();
        } else if (dstart == dend && dstart > 0) {
            return dest.subSequence(0, dstart).toString() + source.toString() + dest.subSequence(dstart, dest.length());
        }
        return "";
    }

    private int containStrNum(String str, String s) {
        if (str.indexOf(s) == -1) {
            return 0;
        } else {
            count++;
            containStrNum(str.substring(str.indexOf(s) + 1), s);
            return count;
        }
    }

    private boolean isInRange(float min, float max, float input) {
        return max > min ? input >= min && input <= max : input >= max && input <= min;
    }

}
