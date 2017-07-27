package com.example.luoxinrun.myapplication.rxbus;

import com.example.luoxinrun.myapplication.presentation.base.BaseActivity;
import com.example.luoxinrun.myapplication.R;
import com.example.luoxinrun.myapplication.databinding.ActivityRxbusBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by luoxinrun on 2017/6/13.
 */

public class RxBusActivity extends BaseActivity {

  private ActivityRxbusBinding mBinding;
  private CompositeSubscription mCompositeSubscription;

  @Override
  protected void initComponent() {
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rxbus);
    mBinding.setPresenter(new Presenter());
    rxBusObservers();
  }

  @Override
  protected void loadData(Bundle savedInstanceState) {

  }

  private void rxBusObservers() {
    Subscription subscription = RxBus.getInstance().toObserverable().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Object>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onNext(Object o) {
            Toast.makeText(RxBusActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
          }
        });
    addSubscription(subscription);
  }

  public void addSubscription(Subscription subscription) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }
    this.mCompositeSubscription.add(subscription);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (this.mCompositeSubscription != null) {
      // 取消注册，以避免内存泄露
      this.mCompositeSubscription.unsubscribe();
    }
  }

  public class Presenter {

    public void onClick(View v) {
      RxBus.getInstance().post("这是来自上游被观察者发来的数据");
    }
  }

}
