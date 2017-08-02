package com.example.luoxinrun.myapplication.presentation.modules.fragsments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by luoxinrun on 2017/7/28.
 */

public class ThreeFragment extends Fragment {
  private static final String LOG_TAG = "ThreeFragment";
  private static final String LOG_LINE = "++++++++++++++++++++++++++++++++++++++";

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    Log.e(LOG_TAG, LOG_LINE + "setUserVisibleHint:" + isVisibleToUser);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.e(LOG_TAG, LOG_LINE + "onAttach");
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e(LOG_TAG, LOG_LINE + "onCreate");
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    Log.e(LOG_TAG, LOG_LINE + "onCreateView");
    LinearLayout ll = new LinearLayout(getActivity());
    ll.setGravity(Gravity.CENTER);
    TextView tv = new TextView(getActivity());
    tv.setText("第3个fragment");
    ll.addView(tv);
    return ll;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.e(LOG_TAG, LOG_LINE + "onActivityCreated");
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.e(LOG_TAG, LOG_LINE + "onStart");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.e(LOG_TAG, LOG_LINE + "onResume");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.e(LOG_TAG, LOG_LINE + "onPause");
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.e(LOG_TAG, LOG_LINE + "onStop");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.e(LOG_TAG, LOG_LINE + "onDestroyView");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.e(LOG_TAG, LOG_LINE + "onDestroy");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    Log.e(LOG_TAG, LOG_LINE + "onDetach");
  }
}
