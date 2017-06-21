package com.example.luoxinrun.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.luoxinrun.myapplication.bubbleview.BubbleActivity;
import com.example.luoxinrun.myapplication.camera.PhotoDisplayActivity;
import com.example.luoxinrun.myapplication.databinding.ActivityMainBinding;
import com.example.luoxinrun.myapplication.greendao.GreenDaoActivity;
import com.example.luoxinrun.myapplication.rxbus.RxBusActivity;

public class MainActivity extends BaseActivity {
  private ActivityMainBinding mBinding;

  @Override
  protected void initComponent() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    mBinding.setPresenter(new Presenter());
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  public class Presenter {

    public void rxBusClick(View view) {
      startActivity(new Intent(MainActivity.this, RxBusActivity.class));
    }

    public void greenDaoClick(View view) {
      startActivity(new Intent(MainActivity.this, GreenDaoActivity.class));
    }

    public void cameraClick(View view) {
      startActivity(new Intent(MainActivity.this, PhotoDisplayActivity.class));
    }

    public void bubbleClick(View view) {
      startActivity(new Intent(MainActivity.this, BubbleActivity.class));
    }

  }
}
