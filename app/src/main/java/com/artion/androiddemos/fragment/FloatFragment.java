package com.artion.androiddemos.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.artion.androiddemos.R;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.view.FloatViewUtils;

/**
 * Created by caijinsheng on 17/11/3.
 */

public class FloatFragment extends BaseFragment {

    private static FloatFragment instance;

    public static FloatFragment getInstance() {
        if(instance == null) {
            instance = new FloatFragment();
        }
        return instance;
    }

    public FloatFragment() {}

    private View dragView;

    private DisplayMetrics dm;
    private int startX, startY;
    private int lastX, lastY;
    private int screenWidth ;
    private int screenHeight ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fag_floatview,null);
        dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        dragView = view.findViewById(R.id.floatview);
        dragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int ea = event.getAction();
                switch(ea){
                    case MotionEvent.ACTION_DOWN:
                        lastX = startX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                        lastY = startY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        FloatViewUtils.record(v, screenWidth, screenHeight,
                                lastX, lastY, (int) event.getRawX(), (int) event.getRawY());
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        v.layout(FloatViewUtils.FLOAT_LEFT,
                                FloatViewUtils.FLOAT_TOP,
                                FloatViewUtils.FLOAT_RIGHT,
                                FloatViewUtils.FLOAT_BOTTOM);
                        v.postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (FloatViewUtils.needIntercept(startX, startY, lastX, lastY)) {
                            FloatViewUtils.fixFloatSide(v, screenWidth, screenHeight);
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        dragView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToastUtils.showMessage(getContext(), "mWindowView onClick");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        FloatViewUtils.resetLayoutParams(dragView, screenWidth, screenHeight);

    }
}
