package com.example.luoxinrun.myapplication.camera;

import java.io.File;

import com.example.luoxinrun.myapplication.BaseActivity;
import com.example.luoxinrun.myapplication.R;
import com.example.luoxinrun.myapplication.camera.util.CameraUtil;
import com.example.luoxinrun.myapplication.databinding.ActivityPhotodisplayBinding;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

/**
 * Created by luoxinrun on 2017/6/14.
 */

public class PhotoDisplayActivity extends BaseActivity {
  private ActivityPhotodisplayBinding mBinding;


  @Override
  protected void initComponent() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_photodisplay);
    mBinding.setPresenter(new Presenter());
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);


  }



  public class Presenter {

    public void systemCameraClick(View v) {
//      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//      // 指定调用相机拍照后照片的储存路径
//      intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(DCIM_PATH + "temp.jpg")));
//      startActivityForResult(intent, REQUEST_SYSTEMCAMERA);
    }

    public void customCameraClick(View v) {

    }

    public void mediaStoreImagesClick(View v) {

    }

  }

}
