package com.artion.androiddemos.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.artion.androiddemos.common.MyViewUtils;
import com.artion.androiddemos.common.ToastUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by caijinsheng on 17/10/11.
 */

public class ToastFloatView  implements View.OnTouchListener {

    public static final int LENGTH_ALWAYS = 0;
    public static final int LENGTH_SHORT = 2;
    public static final int LENGTH_LONG = 4;

    private Toast toast;
    private Context mContext;
    private int mDuration = LENGTH_LONG;
    private boolean isShow = false;

    private Object mTN;
    private Method show;
    private Method hide;
    private WindowManager mWM;
    private WindowManager.LayoutParams params;
    private View mView;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    private Handler handler = new Handler();

    public ToastFloatView(final Context context){
        this.mContext = context;
        if (toast == null) {
            toast = new Toast(mContext);
        }
        mView = MyViewUtils.getToastFloatView(context);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                ToastUtils.showMessage(context, "ToastFloatView onClick");
            }
        });

//        mView.setOnTouchListener(this);//MIUI无法移动view，会抛异常，暂无法解决
    }

    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    public void updateView(String title) {
        MyViewUtils.getMyViewHolder(mView).tvTitle.setText("悬浮窗:" + title);
        show();
    }

    /**
     * Show the view for the specified duration.
     */
    private void show(){
//        if (isShow) return;
//        MyViewUtils.getMyViewHolder(mView).tvTitle.setText("悬浮窗:" + System.currentTimeMillis());
        toast.setView(mView);
        initTN();
        try {
            show.invoke(mTN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isShow = true;
        //判断duration，如果大于#LENGTH_ALWAYS 则设置消失时间
        if (mDuration > LENGTH_ALWAYS) {
            handler.removeCallbacks(hideRunnable);
            handler.postDelayed(hideRunnable, mDuration * 1000);
        }
    }

    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this.  Normally view will disappear on its own
     * after the appropriate duration.
     */
    public void hide(){
        if(!isShow) return;
        try {
            hide.invoke(mTN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isShow = false;
        handler.removeCallbacks(hideRunnable);
    }

    public void setView(View view) {
        toast.setView(view);
    }

    public View getView() {
        return toast.getView();
    }

    /**
     * Set how long to show the view for.
     * @see #LENGTH_SHORT
     * @see #LENGTH_LONG
     * @see #LENGTH_ALWAYS
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        toast.setGravity(gravity,xOffset,yOffset);
    }

    public int getGravity() {
        return toast.getGravity();
    }

    public static ToastFloatView makeText(Context context, CharSequence text, int duration) {
        Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        ToastFloatView exToast = new ToastFloatView(context);
        exToast.toast = toast;
        exToast.mDuration = duration;

        return exToast;
    }

    private void initTN() {
        try {
            Field tnField = toast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTN = tnField.get(toast);
            show = mTN.getClass().getMethod("show");
            hide = mTN.getClass().getMethod("hide");

            Field tnParamsField = mTN.getClass().getDeclaredField("mParams");
            tnParamsField.setAccessible(true);
            params = (WindowManager.LayoutParams) tnParamsField.get(mTN);
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.format = PixelFormat.TRANSLUCENT;// 不设置这个弹出框的透明遮罩显示为黑色
            params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            params.windowAnimations = Resources.getSystem().getIdentifier("Animation.SearchBar", "style", "android");// set the animation for the window

            /**调用tn.show()之前一定要先设置mNextView*/
            Field tnNextViewField = mTN.getClass().getDeclaredField("mNextView");
            tnNextViewField.setAccessible(true);
            tnNextViewField.set(mTN, toast.getView());
            mWM = (WindowManager)mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        } catch (Exception e) {
            e.printStackTrace();
        }
        setGravity(Gravity.TOP,0 ,0);
    }

    private void updateViewPosition(){
        //更新浮动窗口位置参数
        params.x=(int) (x-mTouchStartX);
        params.y=(int) (y-mTouchStartY);
        mWM.updateViewLayout(toast.getView(), params);  //刷新显示
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                //获取相对View的坐标，即以此View左上角为原点
                mTouchStartX =  event.getX();
                mTouchStartY =  event.getY();
                break;
            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                updateViewPosition();
                break;
        }
        return true;
    }

}
