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
    mBinding.et.setFilters(new InputFilter[]{new InputFilterMinMax(2).maxValue(999999.99f)});
    mBinding.et.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        //s:edittext显示的内容，start:字符添加起始位置，字符减少后的起始位置。before:字符添加时始终为0，字符减少时表示减少的个数。count:字符添加时表示增加的个数，字符减少时始终为0。
        Log.e("TAG","=======onTextChange:"+s+"==========start:"+start+"==========before:"+before+"=========count:"+count);
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  public class Presenter {

    public void onSubmit(){
      BigDecimal bigDecimal = new BigDecimal(mBinding.et.getText().toString());

      Toast.makeText(GreenDaoActivity.this, bigDecimal.toString(), Toast.LENGTH_SHORT).show();
    }
  }

}
