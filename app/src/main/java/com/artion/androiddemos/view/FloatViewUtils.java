package com.artion.androiddemos.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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

    public static void fixFloatSide(View view,
                                    int screenWidth, int screenHeight) {
        try {
            int halfWidth = screenWidth / 2;
            int viewCenter = FLOAT_LEFT + (view.getWidth() / 2);
            if (viewCenter > halfWidth) {
                int diffR = screenWidth - view.getRight();
                if (diffR > 0) {
                    startTranslate(view, 0, diffR, diffR);
                    FLOAT_LEFT += diffR;
                }
            } else {
                if (FLOAT_LEFT > 0) {
                    startTranslate(view, 0, -FLOAT_LEFT, FLOAT_LEFT);
                    FLOAT_LEFT = 0;
                }
            }
        } catch (Exception e) {

        }
    }

    private static void startTranslate(final View view, final int fromX, final int toX, int duration) {
        TranslateAnimation animation = new TranslateAnimation(fromX, toX, 0, 0);
        animation.setDuration(duration);
//		animation.setFillAfter(true);//只是将view移动到了目标位置，但是view绑定的点击事件还在原来位置
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                view.setEnabled(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int l = view.getLeft() + (toX - fromX);
                int width = view.getWidth();
                int height = view.getHeight();
                int t = view.getTop();
                view.clearAnimation();

                view.layout(l, t, l + width, t + height);
                view.setEnabled(true);
            }
        });
        view.startAnimation(animation);
    }
}
