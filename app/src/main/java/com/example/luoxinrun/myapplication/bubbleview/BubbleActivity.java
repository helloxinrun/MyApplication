package com.example.luoxinrun.myapplication.bubbleview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.luoxinrun.myapplication.presentation.base.BaseActivity;
import com.example.luoxinrun.myapplication.R;

/**
 * Created by luoxinrun on 2017/6/21.
 */

public class BubbleActivity extends BaseActivity {
    @Override
    protected void initComponent() {
        DataBindingUtil.setContentView(this, R.layout.activity_bubble);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }
}
