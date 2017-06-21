package com.example.luoxinrun.myapplication.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by luoxinrun on 2017/6/8.
 */

public class RxBus {

  private static RxBus rxBus = null;
  /**
   * PublishSubject,只会把订阅之后的Observerable数据发给观察者;
   * SerializedSubject,串行化的subject,避免不同线程间数据接收不同步问题;
   */
  private Subject<Object, Object> mRxBusObserverable = new SerializedSubject<>(
      PublishSubject.create());

  public static synchronized RxBus getInstance() {
    if (rxBus == null) {
      rxBus = new RxBus();
    }
    return rxBus;
  }

  public void post(Object o) {
    if (hasObserverable()) {
      mRxBusObserverable.onNext(o);
    }
  }

  public Observable<Object> toObserverable() {
    return mRxBusObserverable;
  }

  public boolean hasObserverable() {
    return mRxBusObserverable.hasObservers();
  }

}
