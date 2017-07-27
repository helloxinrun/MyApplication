package com.example.luoxinrun.myapplication.presentation.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by luoxinrun on 2017/6/13.
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    initComponent();
    createEventHandlers();
    loadData(savedInstanceState);
  }

  protected abstract void initComponent();

  protected void createEventHandlers(){}

  protected abstract void loadData(Bundle savedInstanceState);

}
