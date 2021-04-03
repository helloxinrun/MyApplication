package com.ixinrun.app_base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.ixinrun.app_base.utils.ScreenUtils;


/**
 * 描述：通用PopupWindow
 * </p>
 *
 * @author : ixinrun
 * @date : 2019/9/2
 */
public class BasePopupWindow extends PopupWindow {

    private Window window;
    private boolean isTransparent;
    protected Context context;

    public BasePopupWindow(Context context) {
        super(context);
        this.context = context;
        if (context instanceof Activity) {
            this.window = ((Activity) context).getWindow();
        }
        popupwindowSetting();
    }

    private void popupwindowSetting() {
        setFocusable(true);         // 设置PopupWindow可获得焦点
        setTouchable(true);         // 设置PopupWindow可触摸
        setOutsideTouchable(false); // 设置非PopupWindow区域可触摸
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));        // 无背景
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 背景是否透明, 默认半透明
     *
     * @param isTransparent
     */
    public void setWindowTransparent(boolean isTransparent) {
        this.isTransparent = isTransparent;
    }

    public void show(View locationView) {
        show(locationView, 0, 0);
    }

    public void show(View anchor, int xoff, int yoff) {
        int off[] = calculatePopWindowPos(anchor, getContentView(), xoff, yoff);
        showAtLocation(anchor, Gravity.TOP | Gravity.START, off[0], off[1]);
        popOutShadow(this);
    }

    private void popOutShadow(PopupWindow popupWindow) {
        if (window == null && isTransparent) {
            return;
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.7f;
        window.setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
    }

    /**
     * 自动调整PopWindow位置
     * window水平方向显示，最左与屏幕左边对齐，最右与屏幕右边对齐；
     * window垂直方向显示，下边空间不够自动展示在上边。
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @param xoff        自定义x偏移
     * @param yoff        自定义y偏移
     * @return window显示的左上角的xOff, yOff坐标
     */
    private static int[] calculatePopWindowPos(final View anchorView, final View contentView, int xoff, int yoff) {
        final int[] windowPos = new int[2];
        final int[] anchorLoc = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);

        //获取anchorView的高宽
        final int anchorHeight = anchorView.getHeight();
        final int anchorWidth = anchorView.getWidth();

        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight();
        final int screenWidth = ScreenUtils.getScreenWidth();
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();

        //处理x坐标
        windowPos[0] = anchorLoc[0] + xoff;
        //向左只能展示到屏幕左对齐
        if (windowPos[0] < 0) {
            windowPos[0] = 0;
        }
        //向右只能展示到屏幕右对齐
        if (windowPos[0] > screenWidth - windowWidth) {
            windowPos[0] = screenWidth - windowWidth;
        }

        //处理y坐标，判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[1] = anchorLoc[1] - windowHeight - yoff;
        } else {
            windowPos[1] = anchorLoc[1] + anchorHeight + yoff;
        }
        return windowPos;
    }
}
