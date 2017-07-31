package com.example.luoxinrun.myapplication.presentation.modules.fragsments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.luoxinrun.myapplication.R;
import com.example.luoxinrun.myapplication.databinding.ActivityFragmentsBinding;
import com.example.luoxinrun.myapplication.presentation.base.BaseActivity;
import com.example.luoxinrun.myapplication.presentation.modules.fragsments.util.FragmentViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luoxinrun on 2017/7/28.
 */

public class FragmentsActiviry extends BaseActivity {
  private ActivityFragmentsBinding mBinding;
  private List<Fragment> list = new ArrayList<>();

  @Override
  protected void initComponent() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_fragments);
    mBinding.setPresenter(new Presenter());
    list.add(new OneFragment());
    list.add(new TwoFragment());
    list.add(new ThreeFragment());
    list.add(new FourFragment());
    new FragmentViewPagerAdapter(getSupportFragmentManager(), mBinding.viewpage, list);
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
  }

  public class Presenter {
    public void oneClick(View view) {
      mBinding.viewpage.setCurrentItem(0);
    }

    public void twoClick(View view) {
      mBinding.viewpage.setCurrentItem(1);
    }

    public void threeClick(View view) {
      mBinding.viewpage.setCurrentItem(2);
    }

    public void fourClick(View view) {
      mBinding.viewpage.setCurrentItem(3);
    }
  }
}
