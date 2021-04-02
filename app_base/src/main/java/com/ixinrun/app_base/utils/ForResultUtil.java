package com.ixinrun.app_base.utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：activity for result
 * </p>
 *
 * @author : ixinrun
 * @date : 2020/6/9
 */
public final class ForResultUtil {
    private final String TAG = this.getClass().getName();
    private final ForResultFrag mFragment;

    private ForResultUtil(FragmentActivity activity) {
        mFragment = getResultBackFragment(activity);
    }

    private ForResultFrag getResultBackFragment(FragmentActivity activity) {
        if (activity == null || activity.isFinishing()) {
            return null;
        }

        ForResultFrag frag = findResultBackFragment(activity);
        if (frag == null) {
            frag = ForResultFrag.newInstance();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(frag, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        return frag;
    }

    private ForResultFrag findResultBackFragment(FragmentActivity activity) {
        return (ForResultFrag) activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }

    private static class ForResultFrag extends Fragment {
        private final Map<Integer, Callback> mCallbacks = new HashMap<>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            ForResultUtil.Callback callback = mCallbacks.remove(requestCode);
            if (callback != null) {
                callback.onActivityResult(new ForResultUtil.Result(resultCode, data));
            }
        }

        public void startForResult(Intent intent, ForResultUtil.Callback callback) {
            mCallbacks.put(callback.hashCode(), callback);
            startActivityForResult(intent, callback.hashCode());
        }

        public static ForResultFrag newInstance() {
            return new ForResultFrag();
        }
    }

    /**
     * 创建ForResultUtil实例
     *
     * @param activity 上下文
     * @return 返回ForResultUtil实例
     */
    public static ForResultUtil with(@NonNull FragmentActivity activity) {
        return new ForResultUtil(activity);
    }

    /**
     * 结果回调
     *
     * @param intent   跳转intent
     * @param callback 回调
     */
    public void startForResult(Intent intent, Callback callback) {
        if (mFragment == null || mFragment.getActivity() == null) {
            return;
        }
        mFragment.startForResult(intent, callback);
    }

    public interface Callback {

        /**
         * 返回时回调结果
         *
         * @param result 回到结果
         */
        void onActivityResult(Result result);
    }

    public static class Result {

        private int resultCode;
        private Intent data;

        Result(int resultCode, Intent data) {
            this.resultCode = resultCode;
            this.data = data;
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public Intent getData() {
            return data;
        }

        public void setData(Intent data) {
            this.data = data;
        }
    }
}
