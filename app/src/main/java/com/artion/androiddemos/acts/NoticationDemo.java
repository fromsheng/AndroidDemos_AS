package com.artion.androiddemos.acts;

import com.artion.androiddemos.R;
import com.artion.androiddemos.common.MiuiUtils;
import com.artion.androiddemos.common.NotificationUtils;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.view.AppFloatView;
import com.artion.androiddemos.view.BaseToastView;
import com.artion.androiddemos.view.FloatNotificationView;
import com.artion.androiddemos.view.ToastFloatView;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;


public class NoticationDemo extends CommonBtnDemo {

	private FloatNotificationView floatView;
	private ToastFloatView toastFloatView;

	int count = 0;
	boolean isShow = false;
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		btn1.setText("通知:" + count);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NotificationUtils.showNotification(mAct, "呵呵", btn1.getText().toString());
				btn1.setText("通知:" + count++);

				MiuiUtils.jumpToPermissionsEditorActivity(mAct);
			}
		});

		btn2.setText("测试通知");
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent tIntent = mAct.getPackageManager().getLaunchIntentForPackage(getPackageName());
//				Intent launchIntent = new Intent(Intent.ACTION_MAIN);
//				launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//				launchIntent.setComponent(tIntent.getComponent());

//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						Intent launchIntent = new Intent(mAct, ExtraViewDemo.class);
//
//						PendingIntent pendingIntent = PendingIntent.getActivity(mAct, 0, launchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//						NotificationUtils.showNotification(mAct, getApplicationInfo().icon, "title", "哈哈哈", pendingIntent, 0);
//
//					}
//				}).start();

//				getToastFloatView().updateView("" + System.currentTimeMillis());//仅此activity内单例
				ToastFloatView.getInstance(mAct).updateToastView("" + System.currentTimeMillis());//全局单例

				}
		});

		ToastFloatView.getInstance(mAct).setToastViewListener(new BaseToastView.ToastViewListener() {
			@Override
			public void onClick() {
				ToastUtils.showMessage(mAct, ToastFloatView.getInstance(mAct).getModel());
			}
		});

		btn3.setText("悬浮通知");
		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isShow) {
					getFloatView().hideFloatView();
				} else {
					getFloatView().showTitle("" + System.currentTimeMillis());
				}
				isShow = !isShow;

			}
		});
		
	}

//	private ToastFloatView getToastFloatView() {
//		if(toastFloatView == null) {
//			toastFloatView = new ToastFloatView(mAct.getApplicationContext());
//		}
//		return toastFloatView;
//	}

	private FloatNotificationView getFloatView() {
		if(floatView == null) {
			floatView = new FloatNotificationView(this.getApplicationContext());
			floatView.setFloatViewListener(new AppFloatView.OnFloatViewListener() {
				@Override
				public void onClick() {
					ToastUtils.showMessage(mAct, "FloatNotificationView onClick:" + floatView.getTitle());
				}
			});
			floatView.createFloatView(0);
		}
		return floatView;
	}

}
