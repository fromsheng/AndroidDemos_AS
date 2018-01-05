package com.artion.androiddemos.common;

import android.os.Handler;
import android.os.Message;

/**
 * Created by caijinsheng on 18/1/5.
 */

public abstract class CountUpTimer {

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountInterval;

    /**
     * 计时和
     */
    private long mTimeSum = 0;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    /**
     * @param countInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     */
    public CountUpTimer(long countInterval) {
        mCountInterval = countInterval;
    }

    /**
     * Cancel the countup.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countup.
     */
    public synchronized final CountUpTimer start() {
        mCancelled = false;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }


    /**
     * Callback fired on regular interval.
     * @param timeSum
     */
    public abstract void onTick(long timeSum);


    private static final int MSG = 1;


    // handles counting up
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountUpTimer.this) {
                if (mCancelled) {
                    return;
                }
                onTick(mTimeSum);
                mTimeSum += mCountInterval;
                sendMessageDelayed(obtainMessage(MSG), mCountInterval);

            }
        }
    };

}
