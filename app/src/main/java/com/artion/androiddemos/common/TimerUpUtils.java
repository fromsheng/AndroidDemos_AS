package com.artion.androiddemos.common;

import android.os.Handler;
import android.os.Looper;

/**
 * 计时类
 * @author jinsheng_cai
 * @since 18/1/5
 */
public class TimerUpUtils {
	private CountUpTimer mTimer = null; // 计时

	private Handler mHandler = null;

	public TimerUpUtils() {
		mHandler = new Handler(Looper.getMainLooper());
	}

	/**
	 * @param countInterval
	 * @param mListener
	 */
	public void startTimer(final long countInterval, final TimerUpListener mListener) {
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				cancelTimer();

				mTimer = new CountUpTimer(countInterval) {
					@Override
					public void onTick(long timeSum) {
						if(mListener != null) {
							mListener.OnTickInterval(timeSum);
						}
					}
				};

				mTimer.start();
			}
		});
		
	}
	
	public void cancelTimer() {
		if(mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}
	
	/**
	 * 倒计时监听回调
	 * @author jinsheng_cai
	 * @since 2014-07-09
	 */
	public interface TimerUpListener {
		/**
		 * countInterval级回调
		 * @param timeSum
		 */
		public void OnTickInterval(long timeSum);
	}
}
