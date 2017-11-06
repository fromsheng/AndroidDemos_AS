package com.artion.androiddemos.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.artion.androiddemos.common.MyViewUtils;

/**
 * Created by caijinsheng on 17/10/11.
 */

public class ToastFloatView extends BaseToastView<String> implements View.OnTouchListener {

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    private static ToastFloatView instance = null;

    public static ToastFloatView getInstance(Context context) {
        if(instance == null) {
            instance = new ToastFloatView(context.getApplicationContext());//防止context泄漏
        }
        return instance;
    }

    private ToastFloatView(final Context context){
        super(context);
        if (instance != null) { //防止反射获取多个对象的漏洞
            throw new RuntimeException();
        }

//        mView.setOnTouchListener(this);//MIUI无法移动view，会抛异常，暂无法解决
    }

    @Override
    public View initView(Context context) {
        return MyViewUtils.getToastFloatView(context);
    }

    @Override
    protected void updateView(String title) {
        MyViewUtils.getMyViewHolder(mView).tvTitle.setText("悬浮窗:" + title);
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
