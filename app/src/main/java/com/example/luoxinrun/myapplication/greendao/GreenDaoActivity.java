package com.example.luoxinrun.myapplication.greendao;

import com.example.luoxinrun.myapplication.BaseActivity;
import com.example.luoxinrun.myapplication.R;
import com.example.luoxinrun.myapplication.databinding.ActivityGreendaoBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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
    mBinding.et.setFilters(new InputFilter[]{new NumberInputFilter(2).maxValue(999999.99f)});
    mBinding.et.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.e("TAG","=======beforeTextChanged:"+s+"==========start:"+start+"==========after:"+after+"=========count:"+count);

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.e("TAG","=======onTextChange:"+s+"==========start:"+start+"==========before:"+before+"=========count:"+count);
      }

      @Override
      public void afterTextChanged(Editable s) {
        Log.e("TAG","=======afterTextChanged:"+s);
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
