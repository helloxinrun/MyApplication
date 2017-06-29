package com.example.luoxinrun.myapplication.greendao;

import com.example.luoxinrun.myapplication.BaseActivity;
import com.example.luoxinrun.myapplication.R;
import com.example.luoxinrun.myapplication.databinding.ActivityGreendaoBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by luoxinrun on 2017/6/14.
 */

public class GreenDaoActivity extends BaseActivity {

  private ActivityGreendaoBinding mBinding;

  @Override
  protected void initComponent() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_greendao);
    mBinding.setPresenter(new Presenter());
    mBinding.et.setFilters(new InputFilter[]{new InputFilterMinMax(0, 3333, 2)});
    mBinding.et.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        //s:edittext显示的内容，start:字符添加起始位置，字符减少后的起始位置。before:字符添加时始终为0，字符减少时表示减少的个数。count:字符添加时表示增加的个数，字符减少时始终为0。
      //  Log.e("TAG","=======onTextChange:"+s+"==========start:"+start+"==========before:"+before+"=========count:"+count);
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

//  InputFilter lengthfilter = new InputFilter() {
//    String s = "222.22";
//    BigDecimal last = new BigDecimal(s);
//
//    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//      Log.e("TAG","======source:"+source.toString()+"=====start:"+start+"=====end:"+end);
//      Log.e("TAG","======dest:"+dest.toString()+"=====dstart:"+dstart+"=====dend:"+dend);
//      // 删除等特殊字符，直接返回
//      if ("".equals(source.toString())) {
//        return null;
//      }
//
//     if ("".equals(dest.toString())){
//       BigDecimal now = new BigDecimal(source.toString());
//       if (now.compareTo(last)==1){
//         source.subSequence(0, s.length());
//
//       }
//     }
//
//
//
////      //首字符不能是"."
////      if (dstart == dend && dstart == 0){
////        //开头添加"."
////        if (source.toString().startsWith(".")){
////          return "0.";
////        }
////      }else if (dstart < dend && dstart == 0){
////        //开头删除后变成"."，由于删除过程source始终是"",所以上边的判断特殊字符的去掉。
////        String s = dest.toString().substring(dend,dest.length());
////        if (s.startsWith(".")){
////          return "0";
////        }
////      }
//
////      //不能是00,01,02...这种情况。
////      if (dstart == dend && dstart == 1){
////        if (dest.toString().startsWith("0") && !source.toString().startsWith(".")){
////
//////          return source.toString().substring(1, end);
////        }
////      }
////
////      //减的情况，假如从第二位减后的结果符合情况...假如从第一位减后的结果符合情况
////      else if (dstart < dend && dstart == 1){
////        String s = dest.toString().substring(dend,dest.length());
////        if (s.startsWith(".")){
////          return "0";
////        }
////      }
//
////      String dValue = dest.toString();
////      String[] splitArray = dValue.split("\\.");
////      if (splitArray.length > 1) {
////        String dotValue = splitArray[1];
////        int diff = dotValue.length() + 1 - 2;
////        if (diff > 0) {
////          CharSequence c=source.subSequence(start, end - diff);
////          System.out.println(c.toString());
////          return c;
////        }
////      }
//      return null;
//    }
//  };


  public class Presenter {

    public void onSubmit(){
      BigDecimal bigDecimal = new BigDecimal(mBinding.et.getText().toString());


      Toast.makeText(GreenDaoActivity.this, bigDecimal.toString(), Toast.LENGTH_SHORT).show();
    }
  }

}
