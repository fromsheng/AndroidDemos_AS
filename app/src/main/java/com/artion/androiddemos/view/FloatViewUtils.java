package com.artion.androiddemos.view;

import android.view.View;
import android.widget.FrameLayout;

/**
 * 页面fragment仿悬浮图标工具类，主要处理位置
 * Created by caijinsheng on 17/11/9.
 */

public class FloatViewUtils {
    public static int FLOAT_LEFT;
    public static int FLOAT_TOP;
    public static int FLOAT_RIGHT;
    public static int FLOAT_BOTTOM;

    /**
     * 是否拦截
     *
     * @return true:拦截;false:不拦截.
     */
    public static boolean needIntercept(int startX, int startY,
                                        int endX, int endY) {
        if (Math.abs(startX - endX) > 30 || Math.abs(startY - endY) > 30) {
            return true;
        }
        return false;
    }

    public static void resetLayoutParams(View view,
                                         int screenWidth, int screenHeight) {
        if(view == null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();

        if(FloatViewUtils.FLOAT_RIGHT <= FloatViewUtils.FLOAT_LEFT
                || FloatViewUtils.FLOAT_BOTTOM <= FloatViewUtils.FLOAT_TOP) {
            layoutParams.leftMargin = 0;
            layoutParams.topMargin = screenHeight/2;
        } else {
            layoutParams.leftMargin = FloatViewUtils.FLOAT_LEFT;
            layoutParams.topMargin = FloatViewUtils.FLOAT_TOP;
        }
        view.setLayoutParams(layoutParams);
    }

    public static void record(View v,
                              int screenWidth, int screenHeight,
                              int startX, int startY,
                              int endX, int endY) {
        int dx = endX - startX;
        int dy = endY- startY;
        FloatViewUtils.FLOAT_LEFT = v.getLeft() + dx;
        FloatViewUtils.FLOAT_BOTTOM = v.getBottom() + dy;
        FloatViewUtils.FLOAT_RIGHT = v.getRight() + dx;
        FloatViewUtils.FLOAT_TOP = v.getTop() + dy;
        // 下面判断移动是否超出屏幕
        if (FloatViewUtils.FLOAT_LEFT < 0) {
            FloatViewUtils.FLOAT_LEFT = 0;
            FloatViewUtils.FLOAT_RIGHT = FloatViewUtils.FLOAT_LEFT + v.getWidth();
        }
        if (FloatViewUtils.FLOAT_TOP < 0) {
            FloatViewUtils.FLOAT_TOP = 0;
            FloatViewUtils.FLOAT_BOTTOM = FloatViewUtils.FLOAT_TOP + v.getHeight();
        }
        if (FloatViewUtils.FLOAT_RIGHT > screenWidth) {
            FloatViewUtils.FLOAT_RIGHT = screenWidth;
            FloatViewUtils.FLOAT_LEFT = FloatViewUtils.FLOAT_RIGHT - v.getWidth();
        }
        if (FloatViewUtils.FLOAT_BOTTOM > screenHeight) {
            FloatViewUtils.FLOAT_BOTTOM = screenHeight;
            FloatViewUtils.FLOAT_TOP = FloatViewUtils.FLOAT_BOTTOM - v.getHeight();
        }
    }
}
