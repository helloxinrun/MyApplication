package com.ixinrun.app_base.activity;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getName();
    protected Activity mContext;

    private boolean mKeyboardAutoHide = true;
    private boolean mFocusAutoLose = false;
    private boolean mActivityDestroyed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initBeforeContent();
        setContentView(getContentView());
        initAfterContent();

        initComponent();
        initEventHandlers();
        loadData(savedInstanceState);
    }

    /**
     * setContentView之前设定
     */
    protected void initBeforeContent() {
        // 如果使用沉浸式状态栏
        if (isTranslucentStatusBar()) {
            makeStatusBarTransparent(this);
        }
    }

    /**
     * 状态栏透明
     *
     * @param activity
     */
    private void makeStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int option = window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        window.getDecorView().setSystemUiVisibility(option);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 是否横屏
     */
    protected boolean isLandscape() {
        return false;
    }

    /**
     * setContentView之后设定
     */
    protected void initAfterContent() {
        //去除window默认背景，防止过度绘制
        getWindow().setBackgroundDrawable(null);
    }

    /**
     * 初始化布局
     */
    protected abstract int getContentView();

    /**
     * 初始化布局控件
     */
    protected abstract void initComponent();

    /**
     * 初始化事件响应
     */
    protected void initEventHandlers() {
    }

    /**
     * 初次加载数据
     */
    protected abstract void loadData(Bundle savedInstanceState);

    /**
     * 沉浸式状态栏
     */
    protected boolean isTranslucentStatusBar() {
        return false;
    }

    /**
     * 设置点击空白处是否隐藏软键盘
     */
    protected void setKeyboardAutoHide(boolean b) {
        this.mKeyboardAutoHide = b;
    }

    /**
     * 设置点击空白处是否失去焦点
     */
    protected void setFocusAutoLose(boolean b) {
        this.mFocusAutoLose = b;
    }

    /**
     * 点击空白处控制软键盘和焦点隐藏或显示
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isEdt(v, ev) && mKeyboardAutoHide) {
                hideSoftInput(v);
                if (mFocusAutoLose) {
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断当前焦点是否是输入框
     */
    public boolean isEdt(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            return !(event.getX() > left)
                    || !(event.getX() < right)
                    || !(event.getY() > top)
                    || !(event.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null) {
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 提前进行资源回收释放
     * 例：
     * 1.A->B->A->B...反复快速执行，导致B页面执行onCreate()或onResume()后接着却执行上一次销毁时的onDestroy(),产生异常。
     * 2.Activity被引用，销毁时无法走到onDestroy(),产生内存泄漏。
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            destroy();
        }
    }

    /**
     * 生命尽头资源回收释放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    private void destroy() {
        if (mActivityDestroyed) {
            return;
        }
        onRecycle();
        mActivityDestroyed = true;
    }

    /**
     * 资源回收释放
     */
    protected void onRecycle() {
    }
}
