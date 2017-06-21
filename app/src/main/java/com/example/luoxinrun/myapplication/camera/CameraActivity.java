package com.example.luoxinrun.myapplication.camera;

import com.example.luoxinrun.myapplication.BaseActivity;
import com.example.luoxinrun.myapplication.R;
import com.example.luoxinrun.myapplication.databinding.ActivityCameraBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

/**
 * Created by luoxinrun on 2017/6/14.
 */

public class CameraActivity extends BaseActivity {
  private ActivityCameraBinding mBinding;

  @Override
  protected void initComponent() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
    mBinding.setPresenter(new Presenter());
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  public class Presenter {

  }



}
