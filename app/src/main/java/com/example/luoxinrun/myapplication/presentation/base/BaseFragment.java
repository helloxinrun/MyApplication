package com.example.luoxinrun.myapplication.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luoxinrun on 2017/7/28.
 */

public abstract class BaseFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = initComponent(inflater, container);
    createEventHandlers();
    loadData(savedInstanceState);
    return view;
  }

  protected abstract View initComponent(LayoutInflater inflater, @Nullable ViewGroup container);

  protected void createEventHandlers() {
  }

  protected abstract void loadData(@Nullable Bundle savedInstanceState);

}
