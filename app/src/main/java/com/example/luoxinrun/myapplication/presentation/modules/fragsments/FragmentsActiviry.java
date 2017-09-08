package com.example.luoxinrun.myapplication.presentation.modules.fragsments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.luoxinrun.myapplication.R;
import com.example.luoxinrun.myapplication.databinding.ActivityFragmentsBinding;
import com.example.luoxinrun.myapplication.presentation.base.BaseActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luoxinrun on 2017/7/28.
 */

public class FragmentsActiviry extends BaseActivity {
  private ActivityFragmentsBinding mBinding;
  private List<Fragment> fragmentList = new ArrayList<>();

  private static final String FRAGMENT_TAG = "FRAGMENT_TAG";
  private static final String FRAGMENT_POSITION = "FRAGMENT_POSITION";
  private List<String> mTags = new ArrayList<>();
  private int mCurrentPosition = 0;
  private MyFragmentViewPagerAdapter mAdapter;

  @Override
  protected void initComponent() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_fragments);
    mBinding.setPresenter(new Presenter());
    fragmentList.add(new OneFragment());
    fragmentList.add(new TwoFragment());
    fragmentList.add(new ThreeFragment());
    fragmentList.add(new FourFragment());
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      mTags = (List<String>) savedInstanceState.getSerializable(FRAGMENT_TAG);
      mCurrentPosition = savedInstanceState.getInt(FRAGMENT_POSITION);
    }
    mAdapter = new MyFragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
    mBinding.viewpage.setAdapter(mAdapter);
    mBinding.viewpage.setCurrentItem(mCurrentPosition);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(FRAGMENT_TAG, (Serializable) mTags);
    outState.putSerializable(FRAGMENT_POSITION, mBinding.viewpage.getCurrentItem());
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

  public class MyFragmentViewPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList;

    public MyFragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
      super(fm);
      this.mFragmentManager = fm;
      this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
      String tag = mTags.get(position);
      Fragment fragment = mFragmentManager.findFragmentByTag(tag);
      if (fragment != null) {
        return fragment;
      }
      return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
      return mFragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      final long itemId = getItemId(position);
      String name = makeFragmentName(container.getId(), itemId);
      mTags.add(position, name);
      return super.instantiateItem(container, position);
    }
  }

  private static String makeFragmentName(int viewId, long id) {
    return "android:switcher:" + viewId + ":" + id;
  }

}
