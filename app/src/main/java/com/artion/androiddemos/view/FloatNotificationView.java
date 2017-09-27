package com.artion.androiddemos.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.artion.androiddemos.common.MyViewUtils;

/**
 * Created by caijinsheng on 17/9/27.
 */

public class FloatNotificationView extends AppFloatView {

    public FloatNotificationView(Context c) {
        super(c);
    }

    private Runnable hiddenRunnable = new Runnable() {
        @Override
        public void run() {
            hideFloatView();
        }
    };

    @Override
    public void createFloatView(int paddingBottom) {
//        super.createFloatView(paddingBottom);

        wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        view = MyViewUtils.getFloatNotificationView(c);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;// 所有程序窗口的“基地”窗口，其他应用程序窗口都显示在它上面。
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;// 不设置这个弹出框的透明遮罩显示为黑色
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = android.R.style.Animation_Translucent;// set the animation for the window
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
//        view.setBackgroundColor(Color.TRANSPARENT);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFloatView();

                if(mListener != null) {
                    mListener.onClick();
                }
            }
        });

        view.setVisibility(View.VISIBLE);
        wm.addView(view, params);
    }

    @Override
    public void showFloatView() {
        super.showFloatView();
        if(view != null) {
            view.postDelayed(hiddenRunnable, 3000);
        }
    }

    @Override
    public void hideFloatView() {
        super.hideFloatView();
        if(view != null) {
            view.removeCallbacks(hiddenRunnable);
        }
    }
}
