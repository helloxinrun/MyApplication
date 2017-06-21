package com.example.luoxinrun.myapplication.camera.util;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * Created by luoxinrun on 2017/6/20.
 */

public class ScreenshotUtil {
  private final String DCIM_PATH = Environment
      .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/";
  private static final int REQUEST_SYSTEMCAMERA = 100; // 系统拍照
  private static final int REQUEST_CUSTOMCAMERA = 101; // 自定义拍照
  private static final int REQUEST_MEDIASTOREIMAGES = 102; // 从相册选取
  private static final int REQUEST_PHOTO_CUT = 103; // 照片裁剪

  private String mTempName;
  private Context mContext;

  private static ScreenshotUtil instance;

  public ScreenshotUtil getInstance(Context context) {
    if (instance == null) {
      instance = new ScreenshotUtil();
    }
    this.mContext = context;
    return instance;
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
    case REQUEST_SYSTEMCAMERA:
      if (resultCode == Activity.RESULT_OK) {
//        File temp = new File(data.getData());
//        File currentPhoto = new File(DCIM_PATH + System.currentTimeMillis() + ".jpg");
//        if (!temp.exists())
//          return;
//        temp.renameTo(currentPhoto);
//        if (!currentPhoto.exists())
//          return;
//        startPhotoZoom(Uri.fromFile(currentPhoto), 800, 640);
      }
      break;
    case REQUEST_MEDIASTOREIMAGES:
      if (data != null)
//        startPhotoZoom(Uri.fromFile(new File(CameraUtil.getPath(this, data.getData()))), 800, 640);
      break;
    case REQUEST_PHOTO_CUT:
      if (resultCode == Activity.RESULT_OK)
//        cutSucceed = true;
      break;
    }

  }

//  private void startPhotoZoom(Uri uri, int outputX, int outputY) {
//    Intent intent = new Intent("com.android.camera.action.CROP"); // 进入系统裁剪页面
//    intent.setDataAndType(uri, "image/*"); // 设置图片类型
//    // crop为true是设置在开启的intent中设置显示的view可以剪裁
//    intent.putExtra("crop", "true");
//
//    // aspectX aspectY 是宽高的比例
//    intent.putExtra("aspectX", outputX);
//    intent.putExtra("aspectY", outputY);
//
//    // outputX,outputY 是剪裁图片的宽高
//    intent.putExtra("outputX", outputX);
//    intent.putExtra("outputY", outputY);
//    intent.putExtra("return-data", false); // 这里我们截取的是大图，而intent传递数据不能太大(一般不允许超过1兆),所以这里我们不需要获取数据了
//    intent.putExtra(MediaStore.EXTRA_OUTPUT,
//        Uri.fromFile(new File(Constants.IMAGE_FILE_FOLDER + "screenshot" + index + ".jpg"))); // 裁剪后输出的位置
//    intent.putExtra("noFaceDetection", true); // 不使用人脸识别
//    startActivityForResult(intent, REQUEST_PHOTO_CUT);
//  }

}
