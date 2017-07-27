package com.example.luoxinrun.myapplication.presentation.main;

import com.example.luoxinrun.myapplication.R;
import com.example.luoxinrun.myapplication.bubbleview.BubbleActivity;
import com.example.luoxinrun.myapplication.camera.PhotoDisplayActivity;
import com.example.luoxinrun.myapplication.databinding.ActivityMainBinding;
import com.example.luoxinrun.myapplication.greendao.GreenDaoActivity;
import com.example.luoxinrun.myapplication.presentation.base.BaseActivity;
import com.example.luoxinrun.myapplication.replugin.RePluginActivity;
import com.example.luoxinrun.myapplication.rxbus.RxBusActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {
  private ActivityMainBinding mBinding;

  @Override
  protected void initComponent() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    mBinding.setPresenter(new Presenter());
    mBinding.gradientTv.setColors(new int[]{Color.parseColor("#FF4040"), Color.parseColor("#FFC125")}).updateView();
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

    public void rePluginClick(View view) {
      startActivity(new Intent(MainActivity.this, RePluginActivity.class));
    }
  }
}
