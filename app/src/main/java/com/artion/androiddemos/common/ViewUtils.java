package com.artion.androiddemos.common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.artion.androiddemos.R;
import com.artion.androiddemos.common.TimerUtils.TimerListener;

/**
 * View相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-01
 */
public class ViewUtils {
	public static int DUPLICATE_DIFF_TIME = 500;
	/**
	 * 连续点击时屏蔽DUPLICATE_DIFF_TIME内的其他点击
	 * @param view
	 * @return
	 */
	public static boolean isDuplicateClicks(View view) {
		return isDuplicateClicks(view, DUPLICATE_DIFF_TIME, true);
	}
	
	/**
	 * 控件是否正在被多次点击
	 * @param view
	 * @param DIFF_TIME 点击时间差（毫秒），可选500，或 1000等
	 * @param isCountEveryTime 是否每次都计算，如果不是按DIFF_TIME时重新计算，在连点中也会到点执行
	 * @return
	 */
	public static boolean isDuplicateClicks(View view, final int DIFF_TIME, boolean isCountEveryTime) {
		if(view == null) {
			return false;
		}
		Long t1 = (Long) view.getTag(R.id.on_view_click);
		if(t1 == null) {
			t1 = Long.valueOf(0);
		}
		
		long t2 = System.currentTimeMillis();
		if(isCountEveryTime) {
			view.setTag(R.id.on_view_click, t2);
		}
		
		if(Math.abs(t2 - t1) > DIFF_TIME) {
			if(!isCountEveryTime) {
				view.setTag(R.id.on_view_click, t2);
			}
			return false;
		}
		
		return true;
	}
	
	/**
	 * 控件是否被连续点击多少次
	 * @param view 控件必须设置id
	 * @param MAX_COUNT 点击最大次数响应
	 * @param DIFF_TIME 点击时间差（毫秒），可选500，或 1000等
	 * @return
	 */
	public static boolean isOnViewClickCount(View view, final int MAX_COUNT, final int DIFF_TIME) {
		if(MAX_COUNT < 2) {
			return true;
		}
		final int TAG_ID_TIME = R.id.on_view_click + 1;
		final int TAG_ID_CLICKTIMES = R.id.on_view_click + 2;
		
		Long t1 = (Long) view.getTag(TAG_ID_TIME);
		Integer count1 = (Integer) view.getTag(TAG_ID_CLICKTIMES);
		
		if(t1 == null) {
			t1 = Long.valueOf(0);
		}
		
		if(count1 == null) {
			count1 = Integer.valueOf(0);
		}
		
		long t2 = System.currentTimeMillis();
		
		view.setTag(TAG_ID_TIME, t2);
		view.setTag(TAG_ID_CLICKTIMES, ++ count1);
		
		if(Math.abs(t2 - t1) > DIFF_TIME) {
			if(t1 != 0) {
				view.setTag(TAG_ID_CLICKTIMES, Integer.valueOf(1));
			}
			return false;
		}
		if(count1 >= MAX_COUNT) {
			view.setTag(TAG_ID_TIME, Long.valueOf(0));
			view.setTag(TAG_ID_CLICKTIMES, Integer.valueOf(0));
			return true;
		}
		
		return false;
	}
	
	/**
	 * 控件被连续点击多少次之后响应
	 * @param view 控件必须设置id
	 * @param DIFF_TIME 点击时间差（毫秒），可选300，500，或 1000等
	 * @param listener 控件点击响应包含点击次数
	 * @return
	 */
	public static int onViewClickTimes(final View view, final int DIFF_TIME, final OnViewClickListener listener) {
		final int TAG_ID_CLICKTIMES = R.id.on_view_click + 20;
		final int TAG_ID_TIMERUTILS = R.id.on_view_click + 30;
		
		Integer count1 = (Integer) view.getTag(TAG_ID_CLICKTIMES);
		TimerUtils timer = (TimerUtils) view.getTag(TAG_ID_TIMERUTILS);
		
		if(count1 == null) {
			count1 = Integer.valueOf(0);
		}
		
		if(timer == null) {
			timer = new TimerUtils();
			view.setTag(TAG_ID_TIMERUTILS, timer);
		}
		
		view.setTag(TAG_ID_CLICKTIMES, ++ count1);
		
		if(listener != null) {
			listener.onClicking(view, count1);
		}
		
		timer.startTimer(DIFF_TIME, new TimerListener() {
			
			@Override
			public void timeOnTick(long seconds) {
			}
			
			@Override
			public void timeOnFinish() {
				if(listener != null) {
					listener.onClicked(view, (Integer) view.getTag(TAG_ID_CLICKTIMES));
				}
				
				view.setTag(TAG_ID_CLICKTIMES, Integer.valueOf(0));
			}
		});
		return 0;
	}
	
	/**
	 * 控件点击响应包含点击次数响应接口
	 * @author jinsheng_cai
	 * @since 2014-10-27
	 */
	public interface OnViewClickListener {
		/**
		 * 点击结束后响应
		 * @param view
		 * @param clickTimes 被点击次数
		 */
		public void onClicked(View view, int clickTimes);
		/**
		 * 点击过程中响应
		 * @param view
		 * @param clickTimes 被点击次数
		 */
		public void onClicking(View view, int clickTimes);
	}
	
	/**
	 * 控件跳动单次
	 * @param view
	 */
	public static void shockView(View view) {
//		view.clearAnimation();
		AnimationSet set = new AnimationSet(false);
		TranslateAnimation transAnim = new TranslateAnimation(0, 2, 0, -5);
        transAnim.setDuration(250);
        transAnim.setRepeatCount(5);
        transAnim.setRepeatMode(Animation.REVERSE);
        
        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.2f, 0f, -1f);
        scaleAnim.setDuration(250);
        scaleAnim.setRepeatCount(5);
        scaleAnim.setRepeatMode(Animation.REVERSE);
        
        set.addAnimation(transAnim);
        set.addAnimation(scaleAnim);
        view.startAnimation(set);
	}

	// If the package id is 0x00 or 0x01, it's either an undefined package
	// or a framework id
    //    if ((key >>> 24) < 2) {
	//	throw new IllegalArgumentException("The key must be an application-specific "
	//			+ "resource id.");
	public static int generateApplicationSpecificViewId(int diff) {
        if(diff < 0) {
            diff = 0;
        }
		return (diff + 2) << 24;
	}

	public static void addTargetView(Context context, View targetView, View badgeView) {
		ViewGroup.LayoutParams lp = targetView.getLayoutParams();
		ViewParent parent = targetView.getParent();
		FrameLayout container = new FrameLayout(context);

		// TODO verify that parent is indeed a ViewGroup
		ViewGroup group = (ViewGroup) parent;
		badgeView.setVisibility(View.GONE);
		if(group instanceof RelativeLayout) {
			group.addView(badgeView, lp);
		} else {

			int index = group.indexOfChild(targetView);

			group.removeView(targetView);
			group.addView(container, index, lp);

			container.addView(targetView);
			container.addView(badgeView);
		}
		group.invalidate();
	}

	public static void setViewLayoutParams(View targetView, View extraView, int width, int height, int gravity) {
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		lp.width = targetView.getWidth() - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, targetView.getResources().getDisplayMetrics());
		lp.width = width;
		lp.height = height;
		lp.gravity = gravity;
//      lp.setMargins(0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, activity.getResources().getDisplayMetrics()), 0);
		extraView.setLayoutParams(lp);
	}

	public static GradientDrawable getGradientDrawable(int color, float cornerRadius) {
		GradientDrawable gd = new GradientDrawable();//创建drawable
		gd.setColor(color);
		gd.setCornerRadius(cornerRadius);
		return gd;
	}

	/**
	 * 创建selector。colorValue使用Color.parseColor,但是对view的setbackground不行，目前未知原因
	 * @param normalColorValue
	 * @param pressedColorValue
	 * @param focusColorValue
	 * @param unableColorValue
	 * @return
	 */
	public static StateListDrawable newSelector(int normalColorValue, int pressedColorValue, int focusColorValue,
												int unableColorValue) {
		StateListDrawable bg = new StateListDrawable();
		Drawable normal = normalColorValue == -1 ? null : getGradientDrawable(normalColorValue, 30);
		Drawable pressed = pressedColorValue == -1 ? null : getGradientDrawable(pressedColorValue, 30);
		Drawable focused = focusColorValue == -1 ? null : getGradientDrawable(focusColorValue, 30);
		Drawable unable = unableColorValue == -1 ? null : getGradientDrawable(unableColorValue, 30);
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, focused);
		// View.WINDOW_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, normal);
		return bg;
	}

	public static void setBackground(View view, String colorNormalStr, String colorPressedStr) {
		if(view == null ||
				TextUtils.isEmpty(colorNormalStr) || TextUtils.isEmpty(colorPressedStr)) {
			return;
		}
		try {
			int normalColor = Color.parseColor(colorNormalStr);
			int pressedColor = Color.parseColor(colorPressedStr);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				StateListDrawable drawable = newSelector(normalColor, pressedColor, pressedColor, normalColor);
				view.setBackground(drawable);
			} else {
				view.setBackgroundColor(normalColor);
			}
		} catch (Exception e) {

		}
	}

}
