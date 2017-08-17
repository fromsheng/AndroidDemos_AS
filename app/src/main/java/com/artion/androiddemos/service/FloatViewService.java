package com.artion.androiddemos.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.view.AppFloatView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class FloatViewService extends Service {

    private AppFloatView mAppFloatView = null;

    private long startTime = 0;

    private boolean isRunning = true;

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//                startTime ++;
            //TODO
            if(mAppFloatView != null) {
                mAppFloatView.updateText(getDiffTime(startTime));
            }
            handler.postDelayed(runnable, 1000);
        }
    };

    public FloatViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO
        startTime = System.currentTimeMillis();

        mAppFloatView = new AppFloatView(this);
        mAppFloatView.createFloatView(100);
        mAppFloatView.updateText(getDiffTime(startTime));
        mAppFloatView.setFloatViewListener(new AppFloatView.OnFloatViewListener() {
            @Override
            public void onClick() {
                ToastUtils.showMessage(FloatViewService.this, "mAppFloatView click");
            }
        });

        handler.postDelayed(runnable, 1000);

    }

    public String getDiffTime(long seconds) {
        long diff = Math.abs(System.currentTimeMillis() - startTime);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
//        return formatter.format(seconds * 1000);
        return formatter.format(diff);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(handler != null) {
            handler.removeMessages(101);
            handler.removeCallbacksAndMessages(null);
        }
        isRunning = false;
        mAppFloatView.removeFloatView();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
